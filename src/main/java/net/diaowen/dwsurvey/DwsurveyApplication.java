package net.diaowen.dwsurvey;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"net.diaowen.common","net.diaowen.dwsurvey"})
//@EnableTransactionManagement
public class DwsurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DwsurveyApplication.class, args);
    }

    @Bean
    public TomcatServletWebServerFactory tomcatFactory(){
        return new TomcatServletWebServerFactory(){

            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }
}
