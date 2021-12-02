package net.diaowen.dwsurvey.controller;

import com.baidu.ueditor.ActionEnter;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by ldb on 2017/4/9.
 */
@Controller
@RequestMapping("/api/dwsurvey/anon/ueditor")
public class UEditorController {

    // 第二种方式
    @Autowired // 注入到容器中
    private Environment environment;

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
//        String rootPath = request.getSession().getServletContext().getRealPath("/");
//        String rootPath = "/Users/xiajunlanna/IdeaProjects/dwsurvey/target/";
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
