package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/ic/user")
public class UserController{

	@Autowired
	private AccountManager accountManager;

	@RequestMapping("/myaccount.do")
	public ModelAndView myaccount() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		User user=accountManager.getCurUser();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("/diaowen-center/my-account");
		return modelAndView;
	}

	@RequestMapping("/pwd.do")
	public String pwd() throws Exception {
		return "/diaowen-center/reset-pwd";
	}

	@RequestMapping("/updatePwd.do")
	public String updatePwd(String curpwd,String pwd) throws Exception {
		accountManager.updatePwd(curpwd,pwd);
		return "redirect:myaccount.do";
	}

	@RequestMapping("/save.do")
	public String save(HttpServletRequest request) throws Exception {
		User user=accountManager.getCurUser();
		String email = request.getParameter("email");
		String cellphone = request.getParameter("cellphone");
		String name = request.getParameter("name");
		user.setEmail(email);
		user.setCellphone(cellphone);
		user.setName(name);
		accountManager.saveUp(user);
		return "redirect:myaccount.do";
	}

}
