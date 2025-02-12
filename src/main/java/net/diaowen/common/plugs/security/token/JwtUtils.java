package net.diaowen.common.plugs.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {

    //缓存前缀
    public final static String REDIS_STORE_PREFIX="dw-auth-token:";
    //token参数名称
    public final static String TOKEN="Authorization";
    // 生成签名的秘钥，使用base64encode后的秘钥
    private final String base64EncodedSecretKey;

    // 生成签名的加密算法
    private final SignatureAlgorithm signatureAlgorithm;

    //定义一个默认的
    public JwtUtils() {
        this.base64EncodedSecretKey = Base64.encodeBase64String("dw123".getBytes());
        this.signatureAlgorithm = SignatureAlgorithm.HS256;
    }

    /**
     * 初始化方法
     * @param secretKey 未进行base64encode转码的密钥
     * @param signatureAlgorithm 加密算法
     */
    public JwtUtils(String secretKey, SignatureAlgorithm signatureAlgorithm) {
        this.base64EncodedSecretKey = Base64.encodeBase64String(secretKey.getBytes());
        this.signatureAlgorithm = signatureAlgorithm;
    }

    /**
     * 生成token
     * @param iss 签发人
     * @param ttlMillis 过期时间
     * @param claims 额外的载荷信息,如用户ID,名称
     * @return
     */
    public String createToken(String iss, long ttlMillis, Map<String, Object> claims) {
        if (claims == null) claims = new HashMap<>();
        // 签发时间（iat）：荷载部分的标准字段之一
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                // 荷载部分的非标准字段/附加字段，一般写在标准的字段之前。
                .setClaims(claims)
                // JWT ID（jti）：荷载部分的标准字段之一，JWT 的唯一性标识，虽不强求，但尽量确保其唯一性。
                .setId(UUID.randomUUID().toString())
                // 签发时间（iat）：荷载部分的标准字段之一，代表这个 JWT 的生成时间。
                .setIssuedAt(now)
                // 签发人（iss）：荷载部分的标准字段之一，代表这个 JWT 的所有者。通常是 username、userid 这样具有用户代表性的内容。
                .setSubject(iss)
                // 设置生成签名的算法和秘钥
                .signWith(signatureAlgorithm, base64EncodedSecretKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 过期时间（exp）：荷载部分的标准字段之一，代表这个 JWT 的有效期。
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    /**
     * 解析token
     * 说明：JWTToken 由（头部、荷载部、签名部）三部分组成。
     *      头部、荷载部是由Base64编码算法生成，可以反向反编码出来。
     *      签名部是由加密算法生成，无法反向解密。
     *      所以不要在jwtToken中放敏感信息
     * @param jwtToken
     * @return 返回载荷键值对
     */
    public Claims parseToken(String jwtToken) {

        // 得到 DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(base64EncodedSecretKey)
                // 设置需要解析的 jwt
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * token签名校验
     * @param jwtToken
     * @return
     */
    public boolean isVerify(String jwtToken) {
        Algorithm algorithm = null;
        switch (signatureAlgorithm) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64.decodeBase64(base64EncodedSecretKey));
                break;
            default:
                throw new RuntimeException("不支持该算法");
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(jwtToken);  // 校验不通过会抛出异常
        return true;
    }

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            return httpRequest.getParameter(TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            // 从 cookie 获取 token
            Cookie[] cookies = httpRequest.getCookies();
            if (null == cookies || cookies.length == 0) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}
