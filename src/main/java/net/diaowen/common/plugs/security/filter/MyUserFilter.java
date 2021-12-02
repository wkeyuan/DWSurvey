package net.diaowen.common.plugs.security.filter;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import net.sf.json.JSONObject;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyUserFilter extends UserFilter {

 @Override
 protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object mappedValue) {
    return super.isAccessAllowed(request,response,mappedValue);
 }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        super.redirectToLogin(request, response);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        try {
            HttpResult httpResult = HttpResult.buildResult(HttpStatus.NOLOGIN);
            JSONObject responseJSONObject = JSONObject.fromObject(httpResult);
            writer.write(responseJSONObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }



    }

}
