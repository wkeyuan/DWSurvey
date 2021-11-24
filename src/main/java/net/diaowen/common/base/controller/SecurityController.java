package net.diaowen.common.base.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

    @RequestMapping("/login.do")
    public String login(HttpServletRequest request,HttpServletResponse response) throws Exception {
        return "/login";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (SecurityUtils.getSubject() != null) {
            SecurityUtils.getSubject().logout();
        }
        request.getSession().invalidate();
        return "/login";
    }

}
