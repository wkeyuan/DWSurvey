package net.diaowen.dwsurvey.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Api(tags = "用户中心")
public class UserController {

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "/currentUser.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取当前登录用户")
    public HttpResult currentUser() throws Exception {
        User user=accountManager.getCurUser();
        return HttpResult.SUCCESS(user);
    }

    @RequestMapping(value =  "/up-info.do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新当前登录用户信息")
    public HttpResult save(HttpServletRequest request,String name,String avatar) throws Exception {
        User user=accountManager.getCurUser();
//		user.setEmail(email);
//		user.setCellphone(cellphone);
        user.setName(name);
        user.setAvatar(avatar);
        accountManager.saveUp(user);
        return HttpResult.SUCCESS();
    }


    @RequestMapping(value = "/up-pwd.do", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改密码")
    public HttpResult updatePwd(String curpwd,String pwd) throws Exception {
        boolean isOk = accountManager.updatePwd(curpwd,pwd);
        if(isOk){
            return HttpResult.SUCCESS();
        }
        return HttpResult.FAILURE();
    }

}
