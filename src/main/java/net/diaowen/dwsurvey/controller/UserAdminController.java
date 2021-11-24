package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sy/user/user-admin")
public class UserAdminController{
	protected final static String USER_ROLE="userRole";
	@Autowired
	private UserManager userManager;

	@RequestMapping("/list.do")
	public ModelAndView list(HttpServletRequest request,Page<User> page,Integer status,String loginName) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		try{
			User entity = new User();
			entity.setStatus(status);
			entity.setLoginName(loginName);
			page=userManager.findPage(page,entity);
			modelAndView.addObject("page",page);
			modelAndView.addObject("status",status);
			modelAndView.addObject("loginName",loginName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("/diaowen-useradmin/list");
		return modelAndView;
	}

	@RequestMapping("/input.do")
	public ModelAndView input(String id) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		if(id!=null){
			User user = userManager.getModel(id);
			modelAndView.addObject("entity",user);
			modelAndView.addObject("id",id);
		}
		modelAndView.setViewName("/diaowen-useradmin/input");
		return modelAndView;
	}

	@RequestMapping("/save.do")
	public String save(User entity) throws Exception {
		try{
			String id = entity.getId();
			if(id==null || id.equals("")){
				entity.setId(null);
				userManager.adminSave(entity,null);
			}else{
				User user = userManager.get(id);
				user.setEmail(entity.getEmail());
				user.setName(entity.getName());
				user.setStatus(entity.getStatus());
				user.setPwd(entity.getPwd());
				userManager.adminSave(user,null);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "redirect:/sy/user/user-admin/list.do";
	}

	/**
	 * 账号禁用
	 */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String id,String event) throws Exception {
		String result="false";
		try{
			if(event!=null && "delete".equals(event)){
				userManager.delete(id);
			}else{
				userManager.disUser(id);
			}
			result="true";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/checkLoginNamelUn.do")
	@ResponseBody
	public boolean checkLoginNamelUn(String id,String loginName) throws Exception{
		User user=userManager.findNameUn(id,loginName);
		boolean result=true;
		if(user!=null){
			result=false;
		}
		return result;
	}


	@RequestMapping("/checkEmailUn.do")
	@ResponseBody
	public boolean checkEmailUn(String id,String email) throws Exception{
		User user=userManager.findEmailUn(id,email);
		boolean result=true;
		if(user!=null){
			result=false;
		}
		return result;
	}

}
