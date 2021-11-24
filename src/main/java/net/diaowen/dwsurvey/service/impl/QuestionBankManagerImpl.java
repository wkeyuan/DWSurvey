package net.diaowen.dwsurvey.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.diaowen.dwsurvey.dao.QuestionBankDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.QuestionBank;
import net.diaowen.dwsurvey.service.QuestionBankManager;
import net.diaowen.dwsurvey.service.QuestionManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseServiceImpl;


/**
 * 题库
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service
public class QuestionBankManagerImpl extends BaseServiceImpl<QuestionBank, String> implements QuestionBankManager {

	@Autowired
	private QuestionBankDao questionBankDao;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private QuestionManager questionManager;
	
	@Override
	public void setBaseDao() {
		this.baseDao=questionBankDao;
	}
	
	@Override
	public void save(QuestionBank t) {
		User user = accountManager.getCurUser();
		if(user!=null){
			t.setUserId(user.getId());
			super.save(t);
		}
	}
	/**
	 * 得到当前目录所在的目录位置
	 */
	public List<QuestionBank> findPath(QuestionBank questionBank) {
		List<QuestionBank> resultList=new ArrayList<QuestionBank>();
		if(questionBank!=null){
			List<QuestionBank> dirPathList=new ArrayList<QuestionBank>();
			dirPathList.add(questionBank);
			String parentUuid=questionBank.getParentId();
			while(parentUuid!=null && !"".equals(parentUuid)){
				QuestionBank questionBank2=get(parentUuid);
				parentUuid=questionBank2.getParentId();
				dirPathList.add(questionBank2);
			}
			for (int i = dirPathList.size()-1; i >=0; i--) {
				resultList.add(dirPathList.get(i));
			}
		}
		return resultList;
	}

	@Override
	public QuestionBank getBank(String id) {
		QuestionBank questionBank=get(id);
		return questionBank;
	}
	@Override
	public QuestionBank findByNameUn(String id, String parentId, String bankName) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		Criterion eqName=Restrictions.eq("bankName", bankName);
		Criterion eqParentId=Restrictions.eq("parentId", parentId);
		criterions.add(eqName);
		criterions.add(eqParentId);
		
		if(id!=null && !"".equals(id)){
			Criterion eqId=Restrictions.ne("id", id);	
			criterions.add(eqId);
		}
		return questionBankDao.findFirst(criterions);
	}
	
	@Override
	public Page<QuestionBank> findPage(Page<QuestionBank> page, QuestionBank entity) {
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("visibility", 1));
		criterions.add(Restrictions.eq("dirType", 2));
		criterions.add(Restrictions.eq("bankState", 1));
		
		Integer bankTag = entity.getBankTag();
		if(bankTag==null){
			bankTag=0;
		}
		criterions.add(Restrictions.eq("bankTag", bankTag));
		String groupId1 = entity.getGroupId1();
		String groupId2 = entity.getGroupId2();
		if(groupId1!=null && !"".equals(groupId1)){
			criterions.add(Restrictions.eq("groupId1", groupId1));
		}
		if(groupId2!=null && !"".equals(groupId2)){
			criterions.add(Restrictions.eq("groupId2", groupId2));
		}
		return questionBankDao.findPageList(page, criterions);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		//设为不可见
		QuestionBank questionBank=get(id);
		questionBank.setVisibility(0);
		questionBankDao.save(questionBank);
		Criterion criterion=Restrictions.eq("parentId", questionBank.getId());
		List<QuestionBank> banks=findList(criterion);
		if(banks!=null){
			for (QuestionBank questionBank2 : banks) {
				delete(questionBank2);
			}
		}
	}
	@Transactional
	public void delete(QuestionBank questionBank) {
		String id=questionBank.getId();
		//目录ID，为1的为系统默认注册用户目录不能删除
		if(!"1".equals(id)){
			//设为不可见
			questionBank.setVisibility(0);
			Criterion criterion=Restrictions.eq("parentId", questionBank.getId());
			List<QuestionBank> banks=findList(criterion);
			if(banks!=null){
				for (QuestionBank questionBank2 : banks) {
					delete(questionBank2);
				}
			}
		}
	}
	
	@Override
	@Transactional
	public void executeBank(String id) {
		QuestionBank questionBank=getBank(id);
		questionBank.setBankState(1);
		//更新下题目数
		List<Question> questions=questionManager.find(id, "1");
		if(questions!=null){
			questionBank.setQuNum(questions.size());
		}
		super.save(questionBank);
	}
	
	@Override
	@Transactional
	public void closeBank(String id) {
		QuestionBank questionBank=getBank(id);
		questionBank.setBankState(0);
		super.save(questionBank);
	}
	
	public List<QuestionBank> newQuestionBank() {
		List<QuestionBank> result=new ArrayList<QuestionBank>();
		try{
			QuestionBank entity=new QuestionBank();
			Page<QuestionBank> page=new Page<QuestionBank>();
			page.setPageSize(15);
			page=findPage(page,entity);
			result=page.getResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
