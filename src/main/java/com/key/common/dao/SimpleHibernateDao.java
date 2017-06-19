/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SimpleHibernateDao.java 1594 2011-05-11 14:22:29Z calvinxiu $
 */
package com.key.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.key.common.utils.AssertUtils;
import com.key.common.utils.ReflectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate, 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <ID> 主键类型
 *
 */
public class SimpleHibernateDao<T, ID extends Serializable> implements ISimpleHibernateDao<T, ID> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	/**
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public SimpleHibernateDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	public SimpleHibernateDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#getSessionFactory()
	 */
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#getSession()
	 */
	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#save(T)
	 */
	@Override
	public void save(final T entity) {
		try {
			AssertUtils.notNull(entity, "entity不能为空");
			getSession().saveOrUpdate(entity);
			logger.debug("save entity: {}", entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#delete(T)
	 */
	@Override
	public void delete(final T entity) {
		AssertUtils.notNull(entity, "entity不能为空");
		getSession().delete(entity);
		logger.debug("delete entity: {}", entity);
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#delete(ID)
	 */
	@Override
	public void delete(final ID id) {
		AssertUtils.notNull(id, "id不能为空");
		delete(get(id));
		logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#get(ID)
	 */
	@Override
	public T get(final ID id) {
		AssertUtils.notNull(id, "id不能为空");
		return (T) getSession().load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#get(java.util.Collection)
	 */
	@Override
	public List<T> get(final Collection<ID> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#getAll()
	 */
	@Override
	public List<T> getAll() {
		return find();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#getAll(java.lang.String, boolean)
	 */
	@Override
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#findBy(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> findBy(final String propertyName, final Object value) {
		AssertUtils.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#findUniqueBy(java.lang.String, java.lang.Object)
	 */
	@Override
	public T findUniqueBy(final String propertyName, final Object value) {
		AssertUtils.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#find(java.lang.String, java.lang.Object)
	 */
	@Override
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#find(java.lang.String, java.util.Map)
	 */
	@Override
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).list();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#findUnique(java.lang.String, java.lang.Object)
	 */
	@Override
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#findUnique(java.lang.String, java.util.Map)
	 */
	@Override
	public <X> X findUnique(final String hql, final Map<String, ?> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#batchExecute(java.lang.String, java.lang.Object)
	 */
	@Override
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#batchExecute(java.lang.String, java.util.Map)
	 */
	@Override
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#createQuery(java.lang.String, java.lang.Object)
	 */
	@Override
	public Query createQuery(final String queryString, final Object... values) {
		AssertUtils.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#createQuery(java.lang.String, java.util.Map)
	 */
	@Override
	public Query createQuery(final String queryString, final Map<String, ?> values) {
		AssertUtils.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#find(org.hibernate.criterion.Criterion)
	 */
	@Override
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#findUnique(org.hibernate.criterion.Criterion)
	 */
	@Override
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#createCriteria(org.hibernate.criterion.Criterion)
	 */
	@Override
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}
	
	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#createCriteria(org.hibernate.criterion.Criterion)
	 */
	@Override
	public Criteria createCriteria(List<Criterion> criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#initProxyObject(java.lang.Object)
	 */
	@Override
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#flush()
	 */
	@Override
	public void flush() {
		getSession().flush();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#distinct(org.hibernate.Query)
	 */
	@Override
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#distinct(org.hibernate.Criteria)
	 */
	@Override
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#getIdName()
	 */
	@Override
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/* (non-Javadoc)
	 * @see com.key.common.orm.hibernate.ISimpleHibernateDao#isPropertyUnique(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue)) {
			return true;
		}
		Object object = findUniqueBy(propertyName, newValue);
		return (object == null);
	}
}