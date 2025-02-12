package net.diaowen.common.plugs.security;

import com.alibaba.fastjson.JSONObject;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RolesOrAuthorizationFilter extends RolesAuthorizationFilter {

 @Override
 public boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for(int i=0;i<rolesArray.length;i++) {
            if(subject.hasRole(rolesArray[i])) {
                return true;
            }
            if(subject.isPermitted(rolesArray[i])){
                return true;
            }
        }
     return false;
 }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
//            this.saveRequestAndRedirectToLogin(request, response);
            this.redirectToLogin(request,response);
        } else {
            String unauthorizedUrl = this.getUnauthorizedUrl();
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(403);
            }
        }
        return false;
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        try {
            HttpResult httpResult = HttpResult.buildResult(HttpStatus.NOLOGIN);
            String responseJSONObject = JSONObject.toJSONString(httpResult);
            writer.write(responseJSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

