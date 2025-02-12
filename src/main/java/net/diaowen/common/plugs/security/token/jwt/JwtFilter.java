package net.diaowen.common.plugs.security.token.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.cache.redis.RedisManager;
import net.diaowen.common.plugs.security.token.JwtToken;
import net.diaowen.common.plugs.security.token.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/*
 * JWT TOKEN 方案认证过滤器，进行token的有效性验证
 * 用来拦截所有需要TOKEN的请求
 * */
public class JwtFilter extends AccessControlFilter {
    @Autowired
    private RedisManager redisManager;

    /*
     * true，通过
     * false，不通过，继续执行onAccessDenied
     * */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        return super.isAccessAllowed(request,response,mappedValue);
        return false;
    }

    /**
     * isAccessAllowed没有通过的请求
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        try{
            HttpServletRequest request  = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader("Content-Type","text/html;charset=UTF-8");
            //允许跨域请求
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", response.getHeader("Origin"));
            //清空当前ThreadLocalToken中的token，因为我们后面可能要新生成token
            //从请求中获取令牌
            String tokenStr = getRequestToken(request);
            //如果令牌为空，在响应中返回前端相关信息
            if(StringUtils.isBlank(tokenStr)) {
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().print("no useful token");
                return false;
            }
            try{
                JwtUtils jwtUtil = new JwtUtils();
                if (jwtUtil.isVerify(tokenStr)) {
                    //校验令牌
                    JwtToken jwtToken = new JwtToken(tokenStr);
                    Subject subject = this.getSubject(request, response);
                    subject.login(jwtToken);
                    return true;
                }
            }catch (TokenExpiredException e) { //发现令牌过期
//                response.setHeader("refresh-token", jwtTokenStr);
//                response.setHeader("Access-Control-Expose-Headers", "refresh-token");
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().print("令牌已经过期");
                return false;
            }catch (JWTDecodeException e){
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().print("无效的令牌");
                return false;
            }catch (AuthenticationException e) {
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().print(e.getMessage());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(JwtUtils.TOKEN);
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            return httpRequest.getParameter(JwtUtils.TOKEN);
        }
        if (StringUtils.isBlank(token)) {
            // 从 cookie 获取 token
            Cookie[] cookies = httpRequest.getCookies();
            if (null == cookies || cookies.length == 0) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtUtils.TOKEN)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    private String buildToken(String userName, User user) {
        //生成token
        JwtUtils jwtUtil = new JwtUtils();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", userName);
        chaim.put("name", user.getName());
        String jwtToken = jwtUtil.createToken(userName, 5 * 60 * 1000, chaim);
        return jwtToken;
    }
}
