package com.key.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.base.entity.IdEntity;
import com.key.common.dao.BaseDao;
import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PropertyFilter;

/**
 * 业务基类
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://www.dwsurvey.net
 */
@Transactional
public abstract class BaseServiceImpl<T extends IdEntity, ID extends Serializable>
		implements BaseService<T, ID> {

	protected BaseDao<T, ID> baseDao;
	
	protected BaseDao<T, ID> getBaseDao() {
		if (baseDao == null) {
			setBaseDao();
		}
		return baseDao;
	}

	@Override
	public void save(T t) {
		String id = t.getId();
		if (id != null && "".equals(id)) {
			t.setId(null);
		}
		getBaseDao().save(t);
	}

	@Override
	public void delete(T t) {
		getBaseDao().delete(t);
	}

	public void delete(ID id) {
		getBaseDao().delete(get(id));
	};
	
	public T get(ID id) {
		return getBaseDao().get(id);
	}

	public T getModel(ID id) {
		return getBaseDao().getModel(id);
	};

	public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
		return getBaseDao().findPage(page, filters);
	}

	@Override
	public List<T> findList(List<PropertyFilter> filters) {
		return getBaseDao().find(filters);
	}
	
	@Override
	public List<T> findAll(Page<T> page, List<PropertyFilter> filters) {
		return getBaseDao().findAll(page, filters);
	}
	
	@Override
	public List<T> findList(Criterion... criterions) {
		return getBaseDao().find(criterions);
	}
	
	@Override
	public Page<T> findPage(Page<T> page, Criterion... criterions) {
		return getBaseDao().findPage(page, criterions);
	}

	public Page<T> findPageByCri(Page<T> page, List<Criterion> criterions) {
		return getBaseDao().findPageByCri(page, criterions);
	}
}
