package net.diaowen.common.plugs.filter;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DwWebResourceFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String reqURI = request.getRequestURI();
        logger.info(" doFilter {}",reqURI);
        filterChain.doFilter(servletRequest,servletResponse);
       /* String surveyId = null;
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                if(reqURI.indexOf("zip")>0){
                    surveyId = getSurveyId(reqURI,"\\/webin\\/zip\\/(.*)\\.zip");
                }else if(reqURI.indexOf("expfile")>0){
                    surveyId = getSurveyId(reqURI,"\\/webin\\/expfile\\/(.*)\\/");
                }
                if(surveyId!=null){
                    //如果不为空，则验证
                    if(surveyDirectoryManager.getSurveyByUser(surveyId,user.getId())!=null){
                        filterChain.doFilter(servletRequest,servletResponse);
                    }else{
                        logger.error("no resource auth {}",surveyId);
                    }
                }else{
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            }else{
                logger.error("no resource auth");
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }*/
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String getSurveyId(String reqURI,String patternStr){
        String surveyId = null;
        try{
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(reqURI);
            if(matcher.find()) surveyId=matcher.group(1);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return surveyId;
    }
}
