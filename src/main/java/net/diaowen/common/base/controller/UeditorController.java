package net.diaowen.common.base.controller;

import com.baidu.ueditor.ActionEnter;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/ueditor")
public class UeditorController {


    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value="/config.do")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String webFilePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
        String rootPath = webFilePath;
        try {
            User user = accountManager.getCurUser();
            if(user!=null){
                String exec = new ActionEnter(request, rootPath, user.getId()).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
