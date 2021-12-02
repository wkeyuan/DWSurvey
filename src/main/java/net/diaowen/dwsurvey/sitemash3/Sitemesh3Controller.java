package net.diaowen.dwsurvey.sitemash3;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sitemesh")
public class Sitemesh3Controller {

    @RequestMapping("/main")
    public String defaultDecorator() {

//        return "/../form-views/main";
        return "admin-default";
    }
}
