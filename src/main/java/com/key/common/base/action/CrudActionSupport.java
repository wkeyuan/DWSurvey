package com.key.common.base.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.key.common.base.entity.IdEntity;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 *
 * @param <T> CRUDAction所管理的对象类型.
 * 
 */
public abstract class CrudActionSupport<T extends IdEntity,ID extends Serializable> extends ActionSupport implements ModelDriven<T>, Preparable {

	private static final long serialVersionUID = -3250152265646142566L;

	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名.
	 */
	public static final String RELOAD = "reload";

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Action函数, 默认的action函数, 默认调用list()函数.
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	//-- CRUD Action函数 --//
	/**
	 * Action函数, 显示Entity列表.
	 * 建议return SUCCESS.
	 */
//	public abstract String list() throws Exception;
	public String list() throws Exception{return SUCCESS;};

	/**
	 * Action函数,显示新增或修改Entity界面.
	 * 建议return INPUT.
	 */
	@Override
//	public abstract String input() throws Exception;
	public String input() throws Exception{return INPUT;};

	/**
	 * Action函数,新增或修改Entity. 
	 * 建议return RELOAD.
	 */
//	public abstract String save() throws Exception;
	public String save() throws Exception{return RELOAD;};

	/**
	 * Action函数,删除Entity.
	 * 建议return RELOAD.
	 */
//	public abstract String delete() throws Exception;
	public String delete() throws Exception{return RELOAD;};

	//-- Preparable函数 --//
	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	@Override
	public void prepare() throws Exception {
	}

	/**
	 * 在input()前执行二次绑定.
	 */
	public void prepareInput() throws Exception {
		prepareModel();
	}

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}

	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用. 
	 */
//	protected abstract void prepareModel() throws Exception;
	protected void prepareModel() throws Exception{};

	protected T entity;
	
	protected ID id;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	protected List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
	protected Page<T> page=new Page<T>();

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	
	@Override
	public T getModel() {
		return entity;
	}
	
}
