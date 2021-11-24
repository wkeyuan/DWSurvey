package net.diaowen.dwsurvey.dao.impl;


import java.util.List;

import net.diaowen.dwsurvey.dao.QuestionDao;
import net.diaowen.dwsurvey.entity.QuOrderby;
import net.diaowen.dwsurvey.entity.QuestionLogic;
import net.diaowen.common.QuType;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.QuCheckbox;
import net.diaowen.dwsurvey.entity.QuMultiFillblank;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.QuScore;
import net.diaowen.dwsurvey.entity.Question;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 题基础 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

//@Repository("questionDao")
@Repository
public class QuestionDaoImpl extends BaseDaoImpl<Question, String> implements QuestionDao {

	public List<Question> findByBelongTag(String qubankId,String tag){
		Page<Question> page=new Page<Question>();
		page.setOrderBy("orderById");
		page.setOrderDir("asc");

		/*List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_belongId", qubankId));
		filters.add(new PropertyFilter("EQI_tag", tag));
		filters.add(new PropertyFilter("NEI_quTag", "3"));
		return findAll(page, filters);*/

		CriteriaBuilder criteriaBuilder=getSession().getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Question.class);
		Root root = criteriaQuery.from(Question.class);
		criteriaQuery.select(root);
		Predicate eqBelongId = criteriaBuilder.equal(root.get("belongId"),qubankId);
		Predicate eqTag = criteriaBuilder.equal(root.get("tag"),tag);
		Predicate quTag = criteriaBuilder.notEqual(root.get("quTag"),3);
		criteriaQuery.where(eqBelongId,eqTag,quTag);
		return findAll(criteriaQuery);
	}

	public void update(Question entity ){
		super.save(entity);
	}
	/**
	 * 保存题目DAO入口
	 */
	@Override
	public void save(Question entity) {
		Session session=getSession();
		saveQuestion(entity, session);
	}
	private void saveQuestion(Question entity, Session session) {
		boolean isnew=false;
		String id=entity.getId();
		String belongId=entity.getBelongId();
		int orderById=entity.getOrderById();
		if(id==null || "".equals(id)){//如果是新增的题目，则根据已有的题来设置排序号
			isnew=true;
		}
		//保存题目的题干部分
		session.saveOrUpdate(entity);
		//判断题目类型
		QuType quType=entity.getQuType();
		if(quType==QuType.RADIO || quType==QuType.COMPRADIO){
			saveRadio(entity, session);
		}else if(quType==QuType.CHECKBOX || quType==QuType.COMPCHECKBOX){
			saveCheckbox(entity, session);
		}else if(quType==QuType.MULTIFILLBLANK){
			saveMultiFillblank(entity, session);
		}else if(quType==QuType.BIGQU){
			saveQuBig(entity, session);
		}else if(quType==QuType.SCORE){
			saveQuScore(entity,session);
		}else if(quType==QuType.ORDERQU){
			saveQuOrderby(entity,session);
		}
		//更新排序号--如果是新增
		List<QuestionLogic> questionLogics=entity.getQuestionLogics();
		if(questionLogics!=null){
			for (QuestionLogic questionLogic : questionLogics) {
				String qulogicId=questionLogic.getId();
				if("".equals(qulogicId)){
					questionLogic.setId(null);
				}
				questionLogic.setCkQuId(entity.getId());
				session.saveOrUpdate(questionLogic);
			}
		}
		if(isnew){
			quOrderByIdAdd1(belongId, orderById);
		}
	}

	/**
	 * 保存评分题
	 * @param entity
	 * @param session
	 */
	private void saveQuScore(Question entity, Session session) {
		List<QuScore> quScores=entity.getQuScores();
		for (QuScore quScore : quScores) {
			quScore.setQuId(entity.getId());
			session.saveOrUpdate(quScore);
		}
	}

	/**
	 * 保存排序题
	 * @param entity
	 * @param session
	 */
	private void saveQuOrderby(Question entity, Session session) {
		List<QuOrderby> quOrderbys=entity.getQuOrderbys();
		for (QuOrderby quOrderby : quOrderbys) {
			quOrderby.setQuId(entity.getId());
			session.saveOrUpdate(quOrderby);
		}
	}
	/**
	 * 保存大题
	 * @param entity
	 * @param session
	 */
	private void saveQuBig(Question entity, Session session) {
		List<Question> questions=entity.getQuestions();
		session.save(entity);
		for (Question question : questions) {
			question.setParentQuId(entity.getId());
			saveQuestion(question,session);
		}
	}
	/**
	 * 保存单选题的单选项
	 * @param entity
	 * @param session
	 */
	private void saveRadio(Question entity,Session session){
		List<QuRadio> quRadios=entity.getQuRadios();

		for (QuRadio quRadio : quRadios) {
			String quRadioId=quRadio.getId();
			if(quRadioId!=null && "0".equals(quRadioId)){
				quRadio.setId(null);
			}
			quRadio.setQuId(entity.getId());
			session.saveOrUpdate(quRadio);
		}
	}
	/**
	 * 保存多选题选项
	 * @param entity
	 * @param session
	 */
	private void saveCheckbox(Question entity,Session session){
		List<QuCheckbox> quCheckboxs=entity.getQuCheckboxs();

		for (QuCheckbox quCheckbox : quCheckboxs) {
			String quRadioId=quCheckbox.getId();
			if(quRadioId!=null && "0".equals(quRadioId)){
				quCheckbox.setId(null);
			}
			quCheckbox.setQuId(entity.getId());
			session.saveOrUpdate(quCheckbox);
		}

	}

	/**
	 * 保存多项填空题选项
	 * @param entity
	 * @param session
	 */
	private void saveMultiFillblank(Question entity,Session session){
		List<QuMultiFillblank> quMultiFillblanks=entity.getQuMultiFillblanks();

		for (QuMultiFillblank quMultiFillblank : quMultiFillblanks) {
			quMultiFillblank.setQuId(entity.getId());
			session.saveOrUpdate(quMultiFillblank);
		}
		// 执行要删除的选项
		String[] removeOptionUuIds=entity.getRemoveOptionUuIds();
		if(removeOptionUuIds!=null){
			for (String optionUuId : removeOptionUuIds) {
				if(optionUuId!=null && !"".equals(optionUuId)){
					QuMultiFillblank quMultiFillblank=new QuMultiFillblank();
					quMultiFillblank.setId(optionUuId);
					session.delete(quMultiFillblank);//删除

				}
			}
		}
	}

	/**
	 * 更新orderbyId
	 * 属性 belongId所有题目，只要大于等于orderById+1
	 * @param belongId
	 * @param orderById
	 */
	private void quOrderByIdAdd1(String belongId,Integer orderById){
		if(belongId!=null && !"".equals(belongId)){
			String sql="update t_question set order_by_id=order_by_id+1 where belong_id=? and order_by_id>=?";
			//更新排序号
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setParameter(1, belongId);
			query.setParameter(2, orderById);
			query.executeUpdate();
		}
	}

	public void quOrderByIdDel1(String belongId,Integer orderById){
		if(belongId!=null && !"".equals(belongId)){
			String sql="update t_question set order_by_id=order_by_id-1 where belong_id=? and order_by_id>=?";
			//更新排序号
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setParameter(1, belongId);
			query.setParameter(2, orderById);
			query.executeUpdate();
		}
	}
}
