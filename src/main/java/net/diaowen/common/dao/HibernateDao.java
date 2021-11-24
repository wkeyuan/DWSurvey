/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: HibernateDao.java 1547 2011-05-05 14:43:07Z calvinxiu $
 */
package net.diaowen.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.diaowen.common.plugs.page.PageRequest;
import net.diaowen.dwsurvey.entity.Question;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.utils.AssertUtils;
import net.diaowen.common.utils.ReflectionUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 封装SpringSide扩展功能的Hibernat DAO泛型基类.
 *
 * 扩展功能包括分页查询,按属性过滤条件列表查询.
 *
 * @param <T> DAO操作的对象类型
 * @param <ID> 主键类型
 *
 */

public class HibernateDao<T, ID extends Serializable> extends SimpleHibernateDao<T, ID> implements IHibernateDao<T, ID> {

	/**
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends HibernateDao<User, Long>{
	 * }
	 */
	public HibernateDao() {
		super();
	}

	public HibernateDao(Class<T> entityClass) {
		super(entityClass);
	}

	//-- 分页查询函数 --//

	/* (non-Javadoc)
	 * @see net.diaowen.common.orm.hibernate.IHibernateDao#getAll(net.diaowen.common.orm.PageRequest)
	 */
	@Override
	public Page<T> getAll(final PageRequest pageRequest) {
		return findPage(pageRequest);
	}

	/* (non-Javadoc)
	 * @see net.diaowen.common.orm.hibernate.IHibernateDao#findPage(net.diaowen.common.orm.PageRequest, java.lang.String, java.lang.Object)
	 */
	@Override
	public Page<T> findPage(final PageRequest pageRequest, String hql, final Object... values) {
		AssertUtils.notNull(pageRequest, "pageRequest不能为空");

		Page<T> page = new Page<T>(pageRequest);

		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalItems(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHql(hql, pageRequest);
		}
		Query q = createQuery(hql, values);

		setPageParameterToQuery(q, pageRequest);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/* (non-Javadoc)
	 * @see net.diaowen.common.orm.hibernate.IHibernateDao#findPage(net.diaowen.common.orm.PageRequest, java.lang.String)
	 */
	@Override
	public Page<T> findPage(final PageRequest pageRequest, String hql) {
		AssertUtils.notNull(pageRequest, "pageRequest不能为空");
		Page<T> page = new Page<T>(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(hql);
			page.setTotalItems(totalCount);
		}
		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHql(hql, pageRequest);
		}
		Query q = createQuery(hql);
		setPageParameterToQuery(q, pageRequest);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/* (non-Javadoc)
	 * @see net.diaowen.common.orm.hibernate.IHibernateDao#findPage(net.diaowen.common.orm.PageRequest, java.lang.String, java.util.Map)
	 */
	@Override
	public Page<T> findPage(final PageRequest pageRequest, String hql, final Map<String, ?> values) {
		AssertUtils.notNull(pageRequest, "page不能为空");

		Page<T> page = new Page<T>(pageRequest);

		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalItems(totalCount);
		}

		if (pageRequest.isOrderBySetted()) {
			hql = setOrderParameterToHql(hql, pageRequest);
		}

		Query q = createQuery(hql, values);
		setPageParameterToQuery(q, pageRequest);

		List result = q.list();
		page.setResult(result);
		return page;
	}

	/* (non-Javadoc)
	 * @see net.diaowen.common.orm.hibernate.IHibernateDao#findPage(net.diaowen.common.orm.PageRequest, org.hibernate.criterion.Criterion)
	 */
	@Override
	public Page<T> findPage(final PageRequest pageRequest, final Criterion... criterions) {
		AssertUtils.notNull(pageRequest, "page不能为空");

		Page<T> page = new Page<T>(pageRequest);

		Criteria c = createCriteria(criterions);
		if (pageRequest.isCountTotal()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalItems(totalCount);
			pageRequest.setTotalPage(page.getTotalPage());
		}
		if(pageRequest.isIslastpage()){
			pageRequest.setPageNo(pageRequest.getTotalPage());
			page.setPageNo(pageRequest.getPageNo());
		}
		setPageRequestToCriteria(c, pageRequest);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 在HQL的后面添加分页参数定义的orderBy, 辅助函数.
	 */
	protected String setOrderParameterToHql(final String hql, final PageRequest pageRequest) {
		StringBuilder builder = new StringBuilder(hql);
		builder.append(" order by");

		for (PageRequest.Sort orderBy : pageRequest.getSort()) {
			builder.append(String.format(" %s.%s %s,", DEFAULT_ALIAS, orderBy.getProperty(), orderBy.getDir()));
		}

		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final PageRequest pageRequest) {
		q.setFirstResult(pageRequest.getOffset());
		q.setMaxResults(pageRequest.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected Criteria setPageRequestToCriteria(final Criteria c, final PageRequest pageRequest) {
		AssertUtils.isTrue(pageRequest.getPageSize() > 0, "Page Size must larger than zero");

		c.setFirstResult(pageRequest.getOffset());
		c.setMaxResults(pageRequest.getPageSize());

		if (pageRequest.isOrderBySetted()) {
			for (PageRequest.Sort sort : pageRequest.getSort()) {
				if (PageRequest.Sort.ASC.equals(sort.getDir())) {
					c.addOrder(Order.asc(sort.getProperty()));
				} else {
					c.addOrder(Order.desc(sort.getProperty()));
				}
			}
		}
		return c;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 *
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 *
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, ?> values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
	}

	private String prepareCountHql(String orgHql) {
		String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));
		return countHql;
	}

	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	protected long countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
		// 执行Count查询

		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}

		return totalCount;
	}


	@Override
	public T getModel(ID id) {
		T t = null;
		try {
			if (id != null && !"".equals(id)) {
				t = get(id);
			}
			if (t == null) {
				t = entityClass.newInstance();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public List<T> findByOrder(String orderByProperty, boolean isAsc,
			Criterion... criterions) {
		Criteria c = createCriteria(criterions);
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		return c.list();
	}

	@Override
	public T findFirst(String orderByProperty, boolean isAsc,
			Criterion... criterions) {
		Criteria c = createCriteria(criterions);
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		c.setMaxResults(1);
		return (T) c.uniqueResult();
	}

	@Override
	public T findFirst(String orderByProperty, boolean isAsc,
			List<Criterion> criterions) {
		Criteria c = createCriteria(criterions);
		if (isAsc) {
			c.addOrder(Order.asc(orderByProperty));
		} else {
			c.addOrder(Order.desc(orderByProperty));
		}
		c.setMaxResults(1);
		return (T) c.uniqueResult();
	}

	@Override
	public Object findUniObjs(String hql,Object... values ) {
		Query q = createQuery(hql, values);
		return q.uniqueResult();
	}

	@Override
	public List<Object[]> findList(String hql, Object... values) {
		Query q = createQuery(hql, values);
		return q.list();
	}

	@Override
	public T findFirst(Criterion... criterions) {
		Criteria c = createCriteria(criterions);
		c.setMaxResults(1);
		return (T) c.uniqueResult();
	}

	public T findFirst(List<Criterion> criterions) {
		Criteria c = createCriteria(criterions);
		c.setMaxResults(1);
		return (T) c.uniqueResult();
	}

	@Override
	public Page<T> findPageList(PageRequest pageRequest,
			List<Criterion> criterions) {
		if(criterions!=null && criterions.size()>0){
			Criteria c = createCriteria(criterions);
			return findPageCriteria(pageRequest, c);
		}
		return findPage(pageRequest);
	}

	public Page<T> findPageCriteria(PageRequest pageRequest,Criteria c){
		Page<T> page = new Page<T>(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalItems(totalCount);
			pageRequest.setTotalPage(page.getTotalPage());
		}
		if(pageRequest.isIslastpage()){
			pageRequest.setPageNo(pageRequest.getTotalPage());
			page.setPageNo(pageRequest.getPageNo());
		}
		setPageRequestToCriteria(c, pageRequest);

		List result = c.list();
		page.setResult(result);
		return page;
	}

	public Page<T> findPageByCri(Page<T> pageRequest, List<Criterion> criterions){
		Criteria c = createCriteria(criterions);
		Page<T> page = new Page<T>(pageRequest);
		if (pageRequest.isCountTotal()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalItems(totalCount);
			pageRequest.setTotalPage(page.getTotalPage());
		}
		if(pageRequest.isIslastpage()){
			pageRequest.setPageNo(pageRequest.getTotalPage());
			page.setPageNo(pageRequest.getPageNo());
		}
		setPageRequestToCriteria(c, pageRequest);

		List result = c.list();
		page.setResult(result);
		return page;
	}


	public List<T> findAll(CriteriaQuery criteriaQuery) {
		/*CriteriaBuilder crb=getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = crb.createQuery(entityClass);
		Root root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		Predicate[] predicatesArray = predicates.toArray(new Predicate[predicates.size()]);
		criteriaQuery.where(predicatesArray);*/
		TypedQuery query = getSession().createQuery(criteriaQuery);
		return query.getResultList();

	}

}
