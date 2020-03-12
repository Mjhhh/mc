package com.edu.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTest {

    /**
     * 生成一个jwt令牌
     */
    @Test
    public void testCreateJwt() {
        //证书文件
        String key_location = "mock.keystore";
        //密钥库密码
        String keystore_password = "majunhong";
        //访问证书路径
        ClassPathResource resource = new ClassPathResource(key_location);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, keystore_password.toCharArray());
        //密钥的密码，此密码和别名要匹配
        String keypassword = "majunhong";
        //密钥别名
        String alias = "mockkey";
        //密钥对（密钥和公钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypassword.toCharArray());
        //私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        //定义payload信息
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("id", "123");
        tokenMap.put("name", "mrt");
        tokenMap.put("roles", "r01,r02");
        tokenMap.put("ext", "1");
        //生成jwt令牌
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(tokenMap), new RsaSigner(aPrivate));
        //取出jwt令牌
        String token = jwt.getEncoded();
        System.out.println("token=" + token);
    }

    /**
     * 资源服务使用公钥验证jwt的合法性，并对jwt解码
     */
    @Test
    public void testVerify() {
        //jwt令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjoidGVzdDAyIiwidXR5cGUiOiIxMDEwMDIiLCJpZCI6IjQ5IiwiZXhwIjoxNTgzOTU5ODk4LCJhdXRob3JpdGllcyI6WyJjb3Vyc2VfY291cnNlYmFzZV9nZXQiLCJjb3Vyc2VfY291cnNlYmFzZV9saXN0Il0sImp0aSI6IjhjNjM5MDg4LTk1MTQtNGEwMC04ZDgyLWNkNjAzYTlkNjVmOSIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.VAuygphy0brS8W-EcH41L0ZsOr-ESnsa-7ZpJXQQAiUkAHNeNm32l5Kw2wcRwryluelk2vl77NIWt9dKMBRKnnIBChUK7VyGLvy-wgCtA1R_XFO__sbjJKPRaCa_brvpY0WVB8TJQXHcZJ75SoYFqHhDELNQBos-31-G0RBbEiZX_OtTyZOB6uD8MN-VYqxclsdyIRTrhCh0Pc5fflh0L6VxcOmJGfJX2r0CGYLpDc4cNcQCjLqm3bqCb5jNZoLmt8bERZn5_hUtiF0dAOYOwpy83LMhDsZ0KW61-KisGbeeRYhtM4HRjW7KahQmNgHxOlP0ERlEsqcFwtNacDK_GA";
        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnf+ccYBv8Pq4cf25LoQEuyXmnV8yqJdWHA9W7ze8issmMO6h326DML6sVVFpZCK8847tUWWMz4kYxjxpoX77kZZANZanc8yzDZZiVvzedVSfBgL/69LQV30VGfi31kj9usKF08tFrM/0pApuYTnXyqSJtb9eVNDIZneNGwywaJs37UClsY4HLBndvPLiR0LXu/YQm2lWPLQWeGUDDpqTMgaIS9UBUnrSdqEXOhQixF0QE92RkQmeAU+xw+eRGzqlRKIHNw5nW1srxg1KTdHlqSTy0Civ94Eb25vhSEUIN+z8RINgMRMjfV4s7I4hmcxeUDLT2uicu7+fsdnxI6wCGwIDAQAB-----END PUBLIC KEY-----";
        //校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
        //获取jwt原始内容
        String claims = jwt.getClaims();
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
