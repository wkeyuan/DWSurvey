package net.diaowen.common.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.base.entity.IdEntity;
import net.diaowen.common.dao.BaseDao;
import net.diaowen.common.plugs.page.Page;

/**
 * 业务基类
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
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

	@Override
	public T findById(ID id) {
		return getBaseDao().findUniqueBy("id",id);
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
