package net.diaowen.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.plugs.page.PageRequest;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

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

	public Page<T> findPageCriteria(PageRequest pageRequest, Criteria c);

	/**
	 * action转入id得到模型
	 * @param id
	 * @return
	 */
	public T getModel(ID id);

	/**
	 * 排序--取出一列数据
	 */
	public  List<T>  findByOrder(String orderByProperty, boolean isAsc, Criterion... criterions);

	/**
	 * 取出第一条
	 */
	public  T  findFirst(Criterion... criterions);
	public  T  findFirst(List<Criterion> criterions);
	public  T  findFirst(String orderByProperty, boolean isAsc, Criterion... criterions);
	public  T  findFirst(String orderByProperty, boolean isAsc, List<Criterion> criterions);

	/**
	 * 执行一条hql返回 一个object[]
	 *
	 */
	public Object findUniObjs(String hql, Object... values);

	/**
	 * 返回list<Object[]>
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<Object[]> findList(String hql, Object... values);

	public Page<T> findPageByCri(Page<T> page, List<Criterion> criterions);

	public List<T> findAll(CriteriaQuery criteriaQuery);

}
