package net.diaowen.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

import net.diaowen.common.base.entity.IdEntity;
import net.diaowen.common.plugs.page.Page;

/**
 * 业务基类接口
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface BaseService<T extends IdEntity,ID extends Serializable> {

	public void setBaseDao();

	public void save(T t);

	public void delete(T t);

	public void delete(ID id);

	public T get(ID id);

	public T getModel(ID id);

	public T findById(ID id);

	public List<T> findList(Criterion... criterions);

	public Page<T> findPage(Page<T> page, Criterion... criterion);
}
