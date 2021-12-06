package net.diaowen.dwsurvey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

// @Configuration
public class UrlRewriteConf extends UrlRewriteFilter {
    private static final String URL_REWRITE = "classpath:conf/urlrewrite.xml";

    //注入urlrewrite配置文件
    @Value(URL_REWRITE)
    private Resource resource;

    //重写配置文件加载方式
    protected void loadUrlRewriter(FilterConfig filterConfig) throws ServletException {
        try {
            //将Resource对象转换成Conf对象
            Conf conf = new Conf(filterConfig.getServletContext(), resource.getInputStream(), resource.getFilename(), "@@traceability@@");
            checkConf(conf);
        } catch (IOException ex) {
            throw new ServletException("Unable to load URL rewrite configuration file from " + URL_REWRITE, ex);
        }
    }
}
