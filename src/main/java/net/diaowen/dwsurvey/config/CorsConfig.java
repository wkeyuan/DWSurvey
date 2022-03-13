package net.diaowen.dwsurvey.config;

import org.apache.catalina.LifecycleState;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 跨域配置类
 */
@Configuration
public class CorsConfig {

//    private static String AllowOrigin = "*";

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);//sessionid 多次访问一致
        corsConfiguration.setAllowedOriginPatterns(getAllowOrigin());
//        corsConfiguration.addAllowedOrigin("*");// 允许任何域名使用
        corsConfiguration.addAllowedHeader("*");// 允许任何头
        corsConfiguration.addAllowedMethod("*");// 允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/file/**", buildConfig());
        source.registerCorsConfiguration("/**", buildConfig());
        CorsFilter corsFilter = new CorsFilter(source);
        FilterRegistrationBean bean = new FilterRegistrationBean(corsFilter);
        bean.setOrder(0);
        return bean;
    }

    /**
     * 可以根据需求调整 AllowOrigin ，默认是所有
     * @return
     */
    public static List<String> getAllowOrigin() {
        List<String> list = new ArrayList<>();
        list.add("*");//所有域名
        return list;
    }
}
