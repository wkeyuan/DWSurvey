package net.diaowen.dwsurvey.config;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

public class MyCommonsMultipartResolver extends CommonsMultipartResolver {
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if(uri!=null && uri.contains("/config")){
            return false;
        }else{
            return super.isMultipart(request);
        }
    }
}
