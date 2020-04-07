package com.edu.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.auth.client.UserClient;
import com.edu.auth.dao.McUserRepository;
import com.edu.auth.util.SmsUtil;
import com.edu.auth.util.ValidateUtil;
import com.edu.framework.client.ServiceList;
import com.edu.framework.domain.ucenter.McUser;
import com.edu.framework.domain.ucenter.ext.AuthToken;
import com.edu.framework.domain.ucenter.ext.McUserExt;
import com.edu.framework.domain.ucenter.request.LoginRequest;
import com.edu.framework.domain.ucenter.response.*;
import com.edu.framework.exception.ExceptionCast;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.CommonResponseResult;
import com.edu.framework.model.response.ResponseResult;
import com.edu.framework.utils.BCryptUtil;
import com.edu.framework.utils.CommonUtil;
import com.edu.framework.utils.CookieUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
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
    @Autowired
    UserClient userClient;
    @Autowired
    SmsUtil smsUtil;
    @Autowired
    DefaultKaptcha captchaProducer;
    @Autowired
    McUserRepository mcUserRepository;

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
                if (StringUtils.equals(error_description,"坏的凭证")) {
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
        stringRedisTemplate.boundValueOps(name).set(content, ttl, TimeUnit.MINUTES);
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
     * 登录并授权
     *
     * @param username
     * @param password
     * @return
     */
    private AuthToken loginAuthorization(String username, String password) {
        AuthToken authToken = this.applyToken(username, password);
        //将token存储到redis
        String jti = authToken.getJtl();
        String content = JSON.toJSONString(authToken);
        boolean saveTokenResult = saveToken(jti, content, tokenValiditySeconds);
        if (!saveTokenResult) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        //将访问令牌存储到cookie
        this.addCookie(jti);
        return authToken;
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
        if (StringUtils.isBlank(loginRequest.getVerifyEncode()) || StringUtils.isBlank(loginRequest.getVerifycode())) {
            ExceptionCast.cast(AuthCode.AUTH_VERIFYCODE_NONE);
        }
        //校验验证码是否输入正确
        String verifycode = loginRequest.getVerifycode();
        String verifyEncode = loginRequest.getVerifyEncode();
        if (!BCryptUtil.matches(verifycode, verifyEncode)) {
            ExceptionCast.cast(AuthCode.AUTH_VERIFYCODE_ERROR);
        }
        //检查账号是否被禁用
        McUser mcUser = userClient.get(loginRequest.getUsername());
        if (StringUtils.equals(mcUser.getStatus(), "0")) {
            ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }
        //进行登录操作
        AuthToken authToken = this.loginAuthorization(loginRequest.getUsername(), loginRequest.getPassword());
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
     *
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

    /**
     * 用户注册
     *
     * @param mcUserExt
     * @return
     */
    @Transactional
    public LoginResult registered(McUserExt mcUserExt) {
        String verifycode = mcUserExt.getVerifycode();
        String redisCode = stringRedisTemplate.opsForValue().get(mcUserExt.getPhone());
        if (!StringUtils.equals(verifycode, redisCode)) {
            ExceptionCast.cast(UcenterCode.VERIFICATION_CODE_ERROR);
        }

        McUser mcUser = new McUser();
        BeanUtils.copyProperties(mcUserExt, mcUser);
        if (mcUser == null) {
            ExceptionCast.cast(AuthCode.AUTH_USER_IS_NULL);
        }
        if (StringUtils.isBlank(mcUser.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        //初始密码
        String initPassword = mcUser.getPassword();
        //密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePassword = bCryptPasswordEncoder.encode(initPassword);
        mcUser.setPassword(encodePassword);
        mcUser.setCreateTime(new Date());
        mcUser.setSalt(initPassword);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(Long.parseLong(mcUser.getBirthday())));
        mcUser.setBirthday(date);
        //101001-学生
        mcUser.setUtype("101001");
        //1-正常
        mcUser.setStatus("1");
        McUserResult mcUserResult = userClient.registered(mcUser);
        if (!mcUserResult.isSuccess()) {
            ExceptionCast.cast(AuthCode.AUTH_REGISTERED_USER_FAILURE);
        }
        McUser one = mcUserResult.getMcUser();
        if (one == null) {
            ExceptionCast.cast(AuthCode.AUTH_REGISTERED_USER_FAILURE);
        }
        //进行登录操作
        AuthToken authToken = this.loginAuthorization(one.getUsername(), initPassword);
        return new LoginResult(CommonCode.SUCCESS, authToken.getJtl());
    }

    /**
     * 生成手机验证码
     * @param phone
     * @return
     */
    public ResponseResult generateMobilevalidateCode(String phone) {
        if (StringUtils.isBlank(phone)) {
            ExceptionCast.cast(CommonCode.MISS_PARAM);
        }
        Random random = new Random();
        String code = String.valueOf(random.nextInt(9999));
        if (smsUtil.smsLoginValidateCode(phone, code)) {
            stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return ResponseResult.SUCCESS();
        }
        return new ResponseResult(UcenterCode.SEND_VERIFICATION_ERROR);
    }


    public CommonResponseResult accountValidateCode() throws IOException {
        String capText = captchaProducer.createText();
        String encodeCapText = BCryptUtil.encode(capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        String fileRoot = "D:/JavaProject/projectForSchool/mcEduUI/mc-ui/img/tempfile/";
        File folder = new File(fileRoot);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        String path = fileRoot + fileName;
        File out = new File(path);
        ImageIO.write(bi, "jpg", out);
        JSONObject result = new JSONObject();
        result.put("url", fileName);
        result.put("encodeCapText", encodeCapText);
        return new CommonResponseResult(CommonCode.SUCCESS, result);
    }

    public LoginResult mobileLogin(LoginRequest loginRequest) {
        //校验账号是否输入
        if (loginRequest == null || StringUtils.isEmpty(loginRequest.getPhone())) {
            ExceptionCast.cast(AuthCode.AUTH_PHONE_NONE);
        }
        if (StringUtils.isBlank(loginRequest.getVerifycode())) {
            ExceptionCast.cast(AuthCode.AUTH_VERIFYCODE_NONE);
        }
        String verifycode = loginRequest.getVerifycode();
        String redisCode = stringRedisTemplate.opsForValue().get(loginRequest.getPhone());
        if (!StringUtils.equals(verifycode, redisCode)) {
            ExceptionCast.cast(UcenterCode.VERIFICATION_CODE_ERROR);
        }
        //检查账号是否被禁用
        McUser mcUser = mcUserRepository.findByPhone(loginRequest.getPhone());
        if (StringUtils.equals(mcUser.getStatus(), "0")) {
            ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }
        //进行登录操作
        AuthToken authToken = this.loginAuthorization(mcUser.getUsername(), mcUser.getSalt());
        return new LoginResult(CommonCode.SUCCESS, authToken.getJtl());
    }
}
