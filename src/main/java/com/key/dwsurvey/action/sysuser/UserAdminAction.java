package com.key.dwsurvey.action.sysuser;


import com.key.common.base.action.CrudActionSupport;
import com.key.common.base.entity.User;
import com.key.common.utils.RandomUtils;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.service.UserManager;
import org.apache.struts2.convention.annotation.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Namespaces({@Namespace("/sy/user"),@Namespace("/sy/user/nosm")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
	@Result(name= CrudActionSupport.SUCCESS,location="/WEB-INF/page/content/diaowen-useradmin/list.jsp",type= Struts2Utils.DISPATCHER),
	@Result(name= CrudActionSupport.INPUT,location="/WEB-INF/page/content/diaowen-useradmin/input.jsp",type= Struts2Utils.DISPATCHER),
	@Result(name= CrudActionSupport.RELOAD,location="/sy/user/user-admin.action",type= Struts2Utils.REDIRECT)
})
@AllowedMethods({"checkLoginNamelUn","checkEmailUn"})
public class UserAdminAction extends CrudActionSupport<User, String> {
	protected final static String USER_ROLE="userRole";
	@Autowired
	private UserManager userManager;

	@Override
	public String list() throws Exception {
		try{
			HttpServletRequest request=Struts2Utils.getRequest();
			String surveyState = request.getParameter("status");
			if(surveyState==null||"".equals(surveyState)){
				entity.setStatus(null);
			}
			page=userManager.findPage(page,entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		HttpServletRequest request= Struts2Utils.getRequest();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		HttpServletRequest request= Struts2Utils.getRequest();
		userManager.adminSave(entity,null);
		return RELOAD;
	}

	/**
	 * 账号禁用
	 */
	@Override
	public String delete() throws Exception {
		HttpServletResponse response= Struts2Utils.getResponse();
		String result="false";
		try{
			userManager.disUser(id);
			result="true";
		}catch (Exception e) {
			// TODO: handle exception
		}
		response.getWriter().write(result);
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		entity=userManager.getModel(id);
	}
	
	public void prepareExecute() throws Exception {
		prepareModel();
	}
	

	public void checkLoginNamelUn() throws Exception{
		HttpServletRequest request= Struts2Utils.getRequest();
		HttpServletResponse response= Struts2Utils.getResponse();
		String loginName=request.getParameter("loginName");
		User user=userManager.findNameUn(id,loginName);
		String result="true";
		if(user!=null){
			result="false";
		}
		response.getWriter().write(result);
	}
	
	public void checkEmailUn() throws Exception{
		HttpServletRequest request= Struts2Utils.getRequest();
		HttpServletResponse response= Struts2Utils.getResponse();
		String email=request.getParameter("email");
		User user=userManager.findEmailUn(id,email);
		String result="true";
		if(user!=null){
			result="false";
		}
		response.getWriter().write(result);
	}
	
}
