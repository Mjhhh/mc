package com.edu.govern_gateway.filter;

import com.alibaba.fastjson.JSON;
import com.edu.framework.model.response.CommonCode;
import com.edu.framework.model.response.ResponseResult;
import com.edu.govern_gateway.service.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    AuthService authService;

    @Override
    public String filterType() {
        //四种类型：pre、routing、post、error
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器
     * @return
     */
    @Override
    public Object run() {
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //请求对象
        HttpServletRequest request = requestContext.getRequest();
        //查询身份令牌
        String jti = authService.getTokenFromCookie(request);
        if (jti == null) {
            //拒绝访问
            access_denied();
        }
        //从redis中校验身份令牌是否过期
        long expire = authService.getExpire(jti);
        if (expire <= 0) {
            //拒绝访问
            access_denied();
        }
        //查询jwt令牌
        String jwt = authService.getJwtFromHeader(request);
        if (jwt == null) {
            //拒绝访问
            access_denied();
        }
        return null;
    }

    /**
     * 拒绝访问
     */
    public void access_denied() {
        //上下文对象
        RequestContext requestContext = RequestContext.getCurrentContext();
        //拒绝访问
        requestContext.setSendZuulResponse(false);
        //设置响应内容
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        String responseResultString = JSON.toJSONString(responseResult);
        requestContext.setResponseBody(responseResultString);
        //设置状态码
        requestContext.setResponseStatusCode(200);
        HttpServletResponse response = requestContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
    }

}