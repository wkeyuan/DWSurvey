package com.key.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.key.common.plugs.page.Page;
import com.key.common.plugs.page.PageRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.key.common.plugs.page.PropertyFilter;
import com.key.common.plugs.page.PropertyFilter.MatchType;

public interface IHibernateDao<T, ID extends Serializable> extends ISimpleHibernateDao<T, ID> {

	public static final String DEFAULT_ALIAS = "x";

	/**
	 * 分页获取全部对象.
	 */
	public abstract Page<T> getAll(final PageRequest pageRequest);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(final PageRequest pageRequest, String hql,
			final Object... values);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param pageRequest 分页参数.
	 * @param hql hql语句.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(final PageRequest pageRequest, String hql);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page 分页参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(final PageRequest pageRequest, String hql,
			final Map<String, ?> values);

	/**
	 * 按Criteria分页查询.
	 * 
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 * 
	 * @return 分页查询结果.附带结果列表及所有查询输入参数.
	 */
	public abstract Page<T> findPage(final PageRequest pageRequest,
			final Criterion... criterions);

	public abstract Page<T> findPageList(final PageRequest pageRequest,
			final List<Criterion> criterions);
	
	public Page<T> findPageCriteria(PageRequest pageRequest,Criteria c);
	/**
	 * 按属性查找对象列表,支持多种匹配方式.
	 * 
	 * @param matchType 匹配方式,目前支持的取值见PropertyFilter的MatcheType enum.
	 */
	public abstract List<T> findBy(final String propertyName,
			final Object value, final MatchType matchType);

	/**
	 * 按属性过滤条件列表查找对象列表.
	 */
	public abstract List<T> find(List<PropertyFilter> filters);

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public abstract Page<T> findPage(final PageRequest pageRequest,
			final List<PropertyFilter> filters);

	/**
	 * 查询所有记录不分页，支持排序
	 * @param pageRequest
	 * @param filters
	 * @return
	 */
	public abstract List<T> findAll(final PageRequest pageRequest,
			final List<PropertyFilter> filters);
	
	/**
	 * action转入id得到模型
	 * @param id
	 * @return
	 */
	public T getModel(ID id);
	
	/**
	 * 排序--取出一列数据
	 */
	public  List<T>  find(String orderByProperty,boolean isAsc,Criterion... criterions);
	
	/**
	 * 取出第一条
	 */
	public  T  findFirst(Criterion... criterions);
	public  T  findFirst(List<Criterion> criterions);
	public  T  findFirst(String orderByProperty,boolean isAsc,Criterion... criterions);
	public  T  findFirst(String orderByProperty, boolean isAsc,List<Criterion> criterions); 
	
	/**
	 * 执行一条hql返回 一个object[]
	 * 
	 */
	public Object findUniObjs(String hql,Object... values );
	
	/**
	 * 返回list<Object[]>
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<Object[]> findList(String hql,Object... values );
	
}
