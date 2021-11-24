package net.diaowen.dwsurvey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations= {"classpath:conf/jcaptcha/applicationContext-jcaptcha.xml"})
public class CaptchaConfig {



}
