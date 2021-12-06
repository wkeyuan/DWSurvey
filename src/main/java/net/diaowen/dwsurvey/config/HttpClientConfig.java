package net.diaowen.dwsurvey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:conf/httpclient/applicationContext-httpclient.xml"})
public class HttpClientConfig {

    public static void main(String[] args) {

    }


}
