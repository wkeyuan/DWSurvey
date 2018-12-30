package com.key.dwsurvey.action.sysuser;

import com.key.dwsurvey.entity.SysDbBackup;
import com.key.dwsurvey.service.SysDbBackupManager;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.key.common.base.action.CrudActionSupport;
import com.key.common.utils.web.Struts2Utils;

/**
 * 系统备份 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Namespaces({@Namespace("/sy/system"),@Namespace("/sy/system/nosm")})
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack") })
@Results({
	@Result(name=CrudActionSupport.SUCCESS,location="/WEB-INF/page/diaowen-system/backup-list.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=CrudActionSupport.INPUT,location="/WEB-INF/page/diaowen-system/backup-input.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name=CrudActionSupport.RELOAD,location="/WEB-INF/page/ref-parent.jsp",type=Struts2Utils.DISPATCHER)
})
public class SysBackupAction extends CrudActionSupport<SysDbBackup, String>{
	
	@Autowired
	private SysDbBackupManager sysDbBackupManager;
	
	@Override
	public String input() throws Exception {
		return super.input();
	}
	
	@Override
	public String list() throws Exception {
		page=sysDbBackupManager.findPage(page, filters);
		return super.list();
	}
	
	@Override
	public String save() throws Exception {
		sysDbBackupManager.save(entity);
		return super.save();
	}
	
	@Override
	protected void prepareModel() throws Exception {
		entity=sysDbBackupManager.getModel(id);
	}
}
