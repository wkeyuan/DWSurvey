package net.diaowen.dwsurvey.config;

import net.diaowen.common.plugs.jsp2html.DiaowenBuild;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServletConfigure {

    /**
     * 代码注册servlet(不需要@ServletComponentScan注解)
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new DiaowenBuild(), "/toHtml");// ServletName默认值为首字母小写，即myServlet1
    }


}
