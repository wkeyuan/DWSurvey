package net.diaowen.common.plugs.security.token.jwt;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.cache.redis.RedisManager;
import net.diaowen.common.plugs.security.token.JwtToken;
import net.diaowen.common.plugs.security.token.JwtUtils;
import net.diaowen.dwsurvey.common.RoleCode;
import net.diaowen.dwsurvey.entity.DwRole;
import net.diaowen.dwsurvey.entity.DwRolePerm;
import net.diaowen.dwsurvey.entity.DwUserRole;
import net.diaowen.dwsurvey.service.DwUserRoleManager;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * JWT TOKEN 方案认证类
 */
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    protected AccountManager accountManager;
    @Autowired
    private DwUserRoleManager userRoleManager;
    @Autowired
    private RedisManager redisManager;

    /*
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
     * 认证：token就是从过滤器中传入的jwtToken
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String tokenStr = (String) jwtToken.getPrincipal();
        JwtUtils jwtUtil = new JwtUtils();
        //验证这个user是否存在
        String username = (String) jwtUtil.parseToken(tokenStr).get("username");
        User user = accountManager.findUserByLoginNameOrEmail(username);
        if (user != null && user.getStatus().intValue()!=0 && (user.getVisibility()==null || user.getVisibility()==1 )) {
            return new SimpleAuthenticationInfo(user, tokenStr , getName());
        }
        return null;
    }

    /**
     * 授权操作
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        String username = (String) principals.fromRealm(getName()).iterator().next();
//        与 doGetAuthenticationInfo SimpleAuthenticationInfo(username, tokenStr , getName()); username对应
//        String username = (String) principals.getPrimaryPrincipal();
//        User user = accountManager.findUserByLoginNameOrEmail(username);
        User user = (User) principals.getPrimaryPrincipal();
        if(user!=null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            if ("1".equals(user.getId())) {
                info.addRole(RoleCode.SUPER_ADMIN);
            }
            //初始化用户角色及权限
            List<DwUserRole> dwUserRoleList = userRoleManager.findByUserId(user.getId());
            for (DwUserRole dwUserRole:dwUserRoleList) {
                DwRole dwRole = dwUserRole.getDwRole();
                List<DwRolePerm> dwRolePerms = dwRole.getDwRolePerms();
                info.addRole(dwRole.getRoleCode());
                for (DwRolePerm dwRolePerm:dwRolePerms) {
                    info.addStringPermission(dwRolePerm.getPermCode());
                }
            }
            return info;
        }
        return null;
    }


}
