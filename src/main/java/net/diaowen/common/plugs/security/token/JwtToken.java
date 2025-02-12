package net.diaowen.common.plugs.security.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtTOKEN
 * 类似 UsernamePasswordToken
 */
public class JwtToken implements AuthenticationToken {

    //新一个token字段
    private String token;

    //初始化方法
    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 类似是用户名，直接返回token
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 类似密码，直接返回token
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
