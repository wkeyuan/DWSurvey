package com.key.dwsurvey.action.sysuser;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.utils.web.Struts2Utils;
import com.key.dwsurvey.entity.SysEmail;
import com.key.dwsurvey.service.SysEmailManager;

/**
 * 系统邮件集成 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespaces({@Namespace("/sy/system"),@Namespace("/sy/system/nosm")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
	@Result(name=CrudActionSupport.SUCCESS,location="/WEB-INF/page/diaowen-system/email-list.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=CrudActionSupport.INPUT,location="/WEB-INF/page/diaowen-system/email-input.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=CrudActionSupport.RELOAD,location="/WEB-INF/page/ref-parent.jsp",type=Struts2Utils.DISPATCHER)
})
public class SysEmailAction extends CrudActionSupport<SysEmail, String>{

	@Autowired
	private SysEmailManager sysEmailManager;
	
	@Override
	public String input() throws Exception {
		return super.input();
	}
	
	@Override
	public String list() throws Exception {
		page=sysEmailManager.findPage(page, filters);
		return super.list();
	}
	
	@Override
	public String save() throws Exception {
		try{
			sysEmailManager.save(entity);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.save();
	}
	
	@Override
	protected void prepareModel() throws Exception {
		entity=sysEmailManager.getModel(id);
	}
}
