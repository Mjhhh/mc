package com.edu.auth.service;

import com.alibaba.fastjson.JSON;
import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.ext.AuthToken;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.AuthCode;
import com.edu.framework.domain.ucenter.response.JwtResult;
import com.edu.framework.domain.ucenter.response.LoginResult;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.tokenValiditySeconds}")
    private Long tokenValiditySeconds;

    @Value("${auth.cookieDomain}")
    String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    /**
     * 生成Authorization参数
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String httpbasic(String clientId, String clientSecret) {
        //将客户端id和客户端密码拼接，按“客户端id:客户端密码”
        String code = clientId + ":" + clientSecret;
        //进行base64编码
        byte[] encode = Base64.encode(code.getBytes());
        return "Basic " + new String(encode);
    }

    /**
     * 认证并生成令牌
     *
     * @param username 用户名
     * @param password 密码
     * @return 认证信息
     */
    private AuthToken applyToken(String username, String password) {
        //采用客户端负载均衡，从eureka获取认证服务的ip和端口
        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceList.SERVICE_UCENTER_AUTH);
        if (serviceInstance == null) {
            LOGGER.error("Failed to select authentication server");
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_AUTHSERVER_NOTFOUND);
        }
        //获取令牌的url
        String authUri = serviceInstance.getUri() + "/auth/oauth/token";
        //请求的内容分两部分
        //1、header信息，包括了http basic认证信息
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", httpbasic(clientId, clientSecret));
        //2、body信息
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(body, headers);

        //指定 restTemplate当遇到400或401响应时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        Map response = null;
        try {
            //http请求spring security的申请令牌接口
            ResponseEntity<Map> exchange = restTemplate.exchange(authUri, HttpMethod.POST, multiValueMapHttpEntity, Map.class);
            response = exchange.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            LOGGER.error("request oauth_token_password error: {}", e.getMessage());
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        //jti是jwt令牌的唯一标识作为用户身份令牌
        if (response == null || response.get("access_token") == null || response.get("refresh_token") == null || response.get("jti") == null) {
            //获取spring security返回的错误信息
            String error_description = (String) response.get("error_description");
            if (StringUtils.isNotEmpty(error_description)) {
                if (error_description.equals("坏的凭证")) {
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                } else if (error_description.contains("UserDetailsService returned null")) {
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                }
            }
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        AuthToken authToken = new AuthToken();
        //访问令牌(jwt)
        String access_token = (String) response.get("access_token");
        //刷新令牌(jwt)
        String refresh_token = (String) response.get("refresh_token");
        //jti，作为用户的身份标识
        String jti = (String) response.get("jti");

        authToken.setAccess_token(access_token);
        authToken.setRefresh_token(refresh_token);
        authToken.setJtl(jti);
        return authToken;
    }

    /**
     * 存储令牌到redis
     *
     * @param jti
     * @param content
     * @param ttl
     * @return
     */
    private boolean saveToken(String jti, String content, long ttl) {
        //令牌名称
        String name = "user_token:" + jti;
        //保存到令牌到redis
        stringRedisTemplate.boundValueOps(name).set(content, ttl, TimeUnit.SECONDS);
        //获取过期时间
        Long expire = stringRedisTemplate.getExpire(name);
        return expire > 0;
    }

    /**
     * 从redis查询令牌
     *
     * @param jti
     * @return
     */
    private AuthToken getUserToken(String jti) {
        String userToken = "user_token:" + jti;
        String userTokenString = stringRedisTemplate.opsForValue().get(userToken);
        AuthToken authToken = null;
        try {
            authToken = JSON.parseObject(userTokenString, AuthToken.class);
        } catch (Exception e) {
            LOGGER.error("getUserToken from redis and execute JSON.parseObject error {}", e.getMessage());
            e.printStackTrace();
        }
        return authToken;
    }

    /**
     * 从redis中删除令牌
     *
     * @param jti
     * @return
     */
    private boolean delToken(String jti) {
        String name = "user_token:" + jti;
        stringRedisTemplate.delete(name);
        return true;
    }

    /**
     * 添加cookie
     *
     * @param jti
     */
    private void addCookie(String jti) {
        HttpServletResponse response = this.getResponse();
        //添加cookie 认证令牌，最后一个参数设置为false，表示允许浏览器获取
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", jti, cookieMaxAge, false);
    }

    /**
     * 从cookie中读取访问令牌
     *
     * @return
     */
    private String getTokenFormCookie() {
        HttpServletRequest request = this.getRequest();
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "uid");
        return cookieMap.get("uid");
    }

    /**
     * 清除cookie
     *
     * @param jti
     */
    private void clearCookie(String jti) {
        HttpServletResponse response = this.getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", jti, 0, false);
    }

    /**
     * 获取request
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取request
     *
     * @return
     */
    private HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 登录认证并提供令牌
     *
     * @param loginRequest
     * @return
     */
    public LoginResult login(LoginRequest loginRequest) {
        //校验账号是否输入
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getUsername())) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        //校验密码是否输入
        if (StringUtils.isEmpty(loginRequest.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        AuthToken authToken = this.applyToken(loginRequest.getUsername(), loginRequest.getPassword());
        if (authToken == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        //将token存储到redis
        String jti = authToken.getJtl();
        String content = JSON.toJSONString(authToken);
        boolean saveTokenResult = saveToken(jti, content, tokenValiditySeconds);
        if (!saveTokenResult) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        //将访问令牌存储到cookie
        this.addCookie(jti);
        return new LoginResult(CommonCode.SUCCESS, authToken.getJtl());
    }

    /**
     * 获取用户的jwt令牌
     *
     * @return
     */
    public JwtResult userjwt() {
        //从cookie读取令牌
        String jti = this.getTokenFormCookie();
        //根据令牌从redis获取jwt
        AuthToken authToken = this.getUserToken(jti);
        if (authToken == null) {
            return new JwtResult(CommonCode.FAIL, null);
        }
        return new JwtResult(CommonCode.SUCCESS, authToken.getAccess_token());
    }

    /**
     * 用户退出登录
     * @return
     */
    public ResponseResult logout() {
        //从cookie读取令牌
        String jti = this.getTokenFormCookie();
        //从redis删除令牌
        this.delToken(jti);
        //清除cookie
        this.clearCookie(jti);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
