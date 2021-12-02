package net.diaowen.dwsurvey.controller.user;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户中心 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/api/dwsurvey/app/user")
public class UserController {

    @Autowired
    private AccountManager accountManager;

    @RequestMapping("/currentUser.do")
    @ResponseBody
    public HttpResult currentUser() throws Exception {
        User user=accountManager.getCurUser();
        return HttpResult.SUCCESS(user);
    }

    @RequestMapping("/up-info.do")
    @ResponseBody
    public HttpResult save(HttpServletRequest request,String name,String avatar) throws Exception {
        User user=accountManager.getCurUser();
//		user.setEmail(email);
//		user.setCellphone(cellphone);
        user.setName(name);
        user.setAvatar(avatar);
        accountManager.saveUp(user);
        return HttpResult.SUCCESS();
    }


    @RequestMapping("/up-pwd.do")
    @ResponseBody
    public HttpResult updatePwd(String curpwd,String pwd) throws Exception {
        System.out.println("curpwd:"+curpwd);
        boolean isOk = accountManager.updatePwd(curpwd,pwd);
        if(isOk){
            return HttpResult.SUCCESS();
        }
        return HttpResult.FAILURE();
    }

}
