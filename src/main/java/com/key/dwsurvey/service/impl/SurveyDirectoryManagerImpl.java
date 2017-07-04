package com.key.dwsurvey.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.key.dwsurvey.dao.SurveyDirectoryDao;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyStats;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyStatsManager;
import com.key.dwsurvey.service.UserManager;
import com.key.dwsurvey.entity.SurveyDirectory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.service.BaseServiceImpl;
import com.key.common.utils.RandomUtils;
import com.key.common.QuType;
import com.key.dwsurvey.service.QuestionBankManager;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.service.SurveyDetailManager;


/**
 * 问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service("surveyDirectoryManager")
public class SurveyDirectoryManagerImpl extends BaseServiceImpl<SurveyDirectory, String> implements SurveyDirectoryManager {

	@Autowired
	private SurveyDirectoryDao surveyDirectoryDao;
	@Autowired
	private SurveyDetailManager surveyDetailManager;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyStatsManager surveyStatsManager;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private QuestionBankManager questionBankManager;
	@Autowired
	private UserManager userManager;
	
	@Override
	public void setBaseDao() {
		this.baseDao=surveyDirectoryDao;
	}
	
	@Transactional
	@Override
	public void save(SurveyDirectory t) {
		User user = accountManager.getCurUser();
		String userId=t.getUserId();
		String id=t.getId();
		if(id==null){
			t.setUserId(user.getId());
			userId=t.getUserId();
		}
		if(userId!=null && userId.equals(user.getId())){
			String sId=t.getSid();
			if(sId==null || "".equals(sId)){
				sId=RandomUtils.randomStr(6, 12);
				t.setSid(sId);
			}
//			super.save(t);
			surveyDirectoryDao.save(t);
			//保存SurveyDirectory
			if(t.getDirType()==2){
				SurveyDetail surveyDetailTemp=t.getSurveyDetail();
				
				SurveyDetail surveyDetail=surveyDetailManager.getBySurveyId(id);
				if(surveyDetail!=null){
					if(surveyDetailTemp!=null){
						surveyDetail.setSurveyNote(surveyDetailTemp.getSurveyNote());
					}
				}else{
					surveyDetail=new SurveyDetail();
					surveyDetail.setSurveyNote("非常感谢您的参与！如有涉及个人信息，我们将严格保密。");
				}
				surveyDetail.setDirId(t.getId());
				surveyDetailManager.save(surveyDetail);
			}
		}
	}
	
	@Transactional
	public void saveByAdmin(SurveyDirectory t){
		String sId=t.getSid();
		if(sId==null || "".equals(sId)){
			sId=RandomUtils.randomStr(6, 12);
			t.setSid(sId);
		}
		surveyDirectoryDao.save(t);
	}
	
	/**
	 * 得到当前目录所在的目录位置
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory) {
		List<SurveyDirectory> resultList=new ArrayList<SurveyDirectory>();
		if(surveyDirectory!=null){
			List<SurveyDirectory> dirPathList=new ArrayList<SurveyDirectory>();
			dirPathList.add(surveyDirectory);
			String parentUuid=surveyDirectory.getParentId();
			while(parentUuid!=null && !"".equals(parentUuid)){
				SurveyDirectory surveyDirectory2=get(parentUuid);
				parentUuid=surveyDirectory2.getParentId();
				dirPathList.add(surveyDirectory2);
			}
			for (int i = dirPathList.size()-1; i >=0; i--) {
				resultList.add(dirPathList.get(i));
			}
		}
		return resultList;
	}

	@Override
	public SurveyDirectory getSurveyBySid(String sid) {
		Criterion criterion=Restrictions.eq("sid", sid);
		SurveyDirectory surveyDirectory = surveyDirectoryDao.findUnique(criterion);
		getSurveyDetail(surveyDirectory.getId(),surveyDirectory);
		return surveyDirectory;
	}
	
	@Override
	public SurveyDirectory getSurvey(String id) {
		if(id==null || "".equals(id)){
			return new SurveyDirectory();
		}
		SurveyDirectory directory=get(id);
		getSurveyDetail(id,directory);
		return directory;
	}
	
	public SurveyDirectory getSurveyByUser(String id,String userId) {
		SurveyDirectory directory=get(id);
		if(userId.equals(directory.getUserId())){
			getSurveyDetail(id,directory);
		    return directory;
		}
		return null;
	}

	public void getSurveyDetail(String id,SurveyDirectory directory) {
		String surveyDetailId=directory.getSurveyDetailId();
		SurveyDetail surveyDetail=null;
		if(surveyDetailId!=null){
			surveyDetail=surveyDetailManager.get(surveyDetailId);
		}
		if(surveyDetail==null){
			surveyDetail=surveyDetailManager.getBySurveyId(id);
		}
		if(surveyDetail==null){
			surveyDetail=new SurveyDetail();
		}
		directory.setSurveyDetail(surveyDetail);
	}
	
	@Override
	public void upSurveyData(SurveyDirectory entity) {
		List<Question> questions=questionManager.findDetails(entity.getId(), "2");
		if(questions!=null){
			int anItemLeastNum=0;
			for (Question question : questions) {
				QuType quType=question.getQuType();
				if(quType==QuType.ENUMQU){//枚举
					anItemLeastNum+=question.getParamInt01();
				}else if(quType==QuType.MULTIFILLBLANK){//组合填空
					anItemLeastNum+=question.getQuMultiFillblanks().size();
				}else if(quType==QuType.SCORE){//评分
					anItemLeastNum+=question.getQuScores().size();
				}else if(quType==QuType.CHENRADIO || quType==QuType.CHENCHECKBOX){//矩阵单选 矩阵多选
					anItemLeastNum+=question.getRows().size();
				}else if(quType==QuType.CHENFBK || quType==QuType.COMPCHENRADIO){//矩阵填空 复合矩阵单选
					anItemLeastNum+=question.getRows().size()*question.getColumns().size();
				}else{
					anItemLeastNum++;
				}
			}
			entity.setSurveyQuNum(questions.size());
			entity.setAnItemLeastNum(anItemLeastNum);
		}
	}
	
	@Override
	@Transactional
	public void executeSurvey(SurveyDirectory entity) {
		entity.setSurveyState(1);
		//计算可以回答的题量
		upSurveyData(entity);
		super.save(entity);
		//生成全局统计结果记录表
		
		SurveyStats surveyStats=new SurveyStats();
		surveyStats.setSurveyId(entity.getId());
		surveyStatsManager.save(surveyStats);
	}

	@Override
	@Transactional
	public void closeSurvey(SurveyDirectory entity) {
		entity.setSurveyState(2);
		//计算可以回答的题量
		super.save(entity);
		//生成全局统计结果记录表
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		//设为不可见
		SurveyDirectory parentDirectory=get(id);
		parentDirectory.setVisibility(0);
		surveyDirectoryDao.save(parentDirectory);
		Criterion criterion=Restrictions.eq("parentId", parentDirectory.getId());
		List<SurveyDirectory> directories=findList(criterion);
		if(directories!=null){
			for (SurveyDirectory surveyDirectory : directories) {
				delete(surveyDirectory);
			}
		}
	}
	
	@Transactional
	public void delete(SurveyDirectory parentDirectory) {
		String id=parentDirectory.getId();
		//目录ID，为1的为系统默认注册用户目录不能删除
		if(!"1".equals(id)){
			//设为不可见
			parentDirectory.setVisibility(0);
			Criterion criterion=Restrictions.eq("parentId", parentDirectory.getId());
			List<SurveyDirectory> directories=findList(criterion);
			if(directories!=null){
				for (SurveyDirectory surveyDirectory : directories) {
					delete(surveyDirectory);
				}
			}
		}
	}

	@Override
	public SurveyDirectory findByNameUn(String id,String parentId, String surveyName) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		Criterion eqName=Restrictions.eq("surveyName", surveyName);
		Criterion eqParentId=Restrictions.eq("parentId", parentId);
		criterions.add(eqName);
		criterions.add(eqParentId);
		
		if(id!=null && !"".equals(id)){
			Criterion eqId=Restrictions.ne("id", id);	
			criterions.add(eqId);
		}
		return surveyDirectoryDao.findFirst(criterions);
	}
	@Override
	public SurveyDirectory findByNameUserUn(String id, String surveyName) {
		User user=accountManager.getCurUser();
		if(user!=null){
			List<Criterion> criterions=new ArrayList<Criterion>();
			Criterion eqName=Restrictions.eq("surveyName", surveyName);
			Criterion eqUserId=Restrictions.eq("userId", user.getId());
			criterions.add(eqName);
			criterions.add(eqUserId);
			
			if(id!=null && !"".equals(id)){
				Criterion eqId=Restrictions.ne("id", id);	
				criterions.add(eqId);
			}
			return surveyDirectoryDao.findFirst(criterions);
		}
		return null;
	}

	@Override
	@Transactional
	public void backDesign(SurveyDirectory entity) {
		entity.setSurveyState(0);
		//计算可以回答的题量
		super.save(entity);
	}
	
	@Transactional
	public void checkUp(SurveyDirectory entity) {
		//计算可以回答的题量
		super.save(entity);
	}
	
	
	@Transactional
	public void upSuveyText(SurveyDirectory t){
		String id=t.getId();
		if(id!=null&&id.length()>0){
			super.save(t);
			//保存SurveyDirectory
			if(t.getDirType()==2){
				SurveyDetail surveyDetail=t.getSurveyDetail();
				surveyDetail.setDirId(t.getId());
				surveyDetailManager.save(surveyDetail);
			}
		}
	}
	
	@Transactional
	public void saveUser(SurveyDirectory t) {
		super.save(t);
		//保存SurveyDirectory
		if(t.getDirType()==2){
			SurveyDetail surveyDetail=t.getSurveyDetail();
			surveyDetail.setDirId(t.getId());
			surveyDetailManager.save(surveyDetail);
		}
	}
	
	public void saveUserSurvey(SurveyDirectory entity) {
		//用户问卷，系统指定存放目录
		//用户存放目录，ID=1，name=用户问卷  之后以用户的LoginName来分子目录
		//创建用户存放目录
		User user = accountManager.getCurUser();
		if(user!=null){
			String enId = entity.getId();
			String userId=user.getId();
			if(enId==null || "".equals(enId)){
				//根据用户名得到目录
				String loginName=user.getLoginName();
				SurveyDirectory surveyDirUser=findByNameUn(null, "1", loginName);
				if(surveyDirUser==null){
					//没有此目录则创建此目录，你ID=1
					surveyDirUser=new SurveyDirectory();
					surveyDirUser.setSurveyName(loginName);
					surveyDirUser.setDirType(1);
					surveyDirUser.setUserId(user.getId());
					surveyDirUser.setParentId("1");
					saveUser(surveyDirUser);
				}
				entity.setParentId(surveyDirUser.getId());
				
				entity.setUserId(userId);
				saveUser(entity);
			}else{
				//判断当前人有无权限修改
				String enUserId=entity.getUserId();
				if(userId.equals(enUserId)){
					entity.setUserId(userId);
					saveUser(entity);
				}
			}
		}
	}

	@Override
	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,
			SurveyDirectory entity) {
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("visibility", 1));
		criterions.add(Restrictions.eq("surveyState", 1));
		
		criterions.add(Restrictions.eq("dirType", 2));
		criterions.add(Restrictions.eq("surveyModel", 1));
		
		Integer share = entity.getIsShare();
		if(share!=null && share==1){
			criterions.add(Restrictions.eq("isShare", share));
		}

		return surveyDirectoryDao.findPageList(page, criterions);
	}
	

	public List<SurveyDirectory> newSurveyList() {
		List<SurveyDirectory> result=new ArrayList<SurveyDirectory>();
		try{
			SurveyDirectory entity=new SurveyDirectory();
			Page<SurveyDirectory> page=new Page<SurveyDirectory>();
			page.setPageSize(25);
			page=findPage(page,entity);
			result=page.getResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	@Transactional
	public void saveAll(SurveyDirectory directory) {
		List<Question> questions=directory.getQuestions();
		directory.setDirType(2);
		directory.setParentId("402880e5428a2dca01428a2f1f290000");
		directory.setSurveyTag(0);
		directory.setSurveyQuNum(questions.size());
		surveyDirectoryDao.save(directory);
		String surveyId=directory.getId();
		//详细信息
		SurveyDetail detail=directory.getSurveyDetail();
		detail.setDirId(surveyId);
		surveyDetailManager.save(detail);
//		directory.setSurveyDetailId(detail.getId());
		//题目列表
		for (Question question : questions) {
			if(question!=null){
				question.setBelongId(surveyId);
				question.setCreateDate(directory.getCreateDate());
				question.setTag(2);
				questionManager.save(question);
			}
		}
//		surveyDirectoryDao.save(directory);
	}

	@Override
	public SurveyDirectory findNext(SurveyDirectory directory) {
		Date date=directory.getCreateDate();
		Criterion criterion=Restrictions.gt("createDate", date);
		return surveyDirectoryDao.findFirst(criterion);
	}
	
	@Override
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page,
											SurveyDirectory entity) {
	    User user=accountManager.getCurUser();
	    if(user!=null){
			List<Criterion> criterions=new ArrayList<Criterion>();

			criterions.add(Restrictions.eq("userId", user.getId()));
			criterions.add(Restrictions.eq("visibility", 1));
			criterions.add(Restrictions.eq("dirType", 2));
			criterions.add(Restrictions.eq("surveyModel", 1));

			if(entity!=null){
				Integer surveyState = entity.getSurveyState();
				if(surveyState!=null && !"".equals(surveyState)){
					criterions.add(Restrictions.eq("surveyState", surveyState));
				}
				String surveyName = entity.getSurveyName();
				if(surveyName!=null && !"".equals(surveyName)){
					criterions.add(Restrictions.like("surveyName", "%"+surveyName+"%"));
				}
			}

			page.setOrderBy("createDate");
			page.setOrderDir("desc");
			page=surveyDirectoryDao.findPageList(page,criterions);
	    }
	    return page;
	}
	
	public Page<SurveyDirectory> findByGroup(String groupId1,String groupId2,Page<SurveyDirectory> page) {
		
		
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(groupId1!=null && !"".equals(groupId1)){
			Criterion cri1=Restrictions.eq("groupId1", groupId1);
			criterions.add(cri1);
		}
		if(groupId2!=null && !"".equals(groupId2)){
			Criterion cri1_2=Restrictions.eq("groupId2", groupId2);
			criterions.add(cri1_2);
		}
	    
	    
	    //Criterion cri2=Restrictions.eq("isShare", isShare);
	    Criterion cri2=Restrictions.eq("visibility", 1);
	    //Criterion cri3=Restrictions.ne("surveyTag", 2);
	    Criterion cri4=Restrictions.eq("surveyModel", 4);
	    //Criterion cri4=Restrictions.gt("surveyQuNum", 5);
	    
	    criterions.add(cri2);
	    criterions.add(cri4);
//	    Page<SurveyDirectory> page=new Page<SurveyDirectory>();
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		
	    return surveyDirectoryDao.findPage(page,criterions.toArray(new Criterion[criterions.size()]) );
	}

	
	@Override
	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
			SurveyDirectory entity) {
		Integer surveyState=entity.getSurveyState();
		String surveyName=entity.getSurveyName();
		List<Criterion> criterions=new ArrayList<Criterion>();
		
		if(surveyState!=null && surveyState.intValue()!=100){
			Criterion cri1=Restrictions.eq("surveyState", surveyState);
			criterions.add(cri1);
		}
		if(surveyName!=null && !"".equals(surveyName)){
			Criterion cri1=Restrictions.like("surveyName", "%"+surveyName+"%");
			criterions.add(cri1);
		}
	    Criterion cri2=Restrictions.eq("visibility", 1);
	    criterions.add(cri2);
//		Criterion cri3=Restrictions.ne("surveyTag", 2);
	    Criterion cri4=Restrictions.eq("surveyModel", 4);
	    criterions.add(cri4);
	    //Criterion cri4=Restrictions.gt("surveyQuNum", 5);
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
	    return surveyDirectoryDao.findPageList(page,criterions);
	}
	
	@Override
	public List<SurveyDirectory> findByIndex() {
	    Criterion cri1=Restrictions.eq("visibility", 1);
	    Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
	    Criterion cri3=Restrictions.eq("surveyTag", 1);
	    Criterion cri4=Restrictions.isNull("sid");
	    //SELECT * FROM `diaowen`.`t_survey_directory` where visibility=1 and dir_type=2 and sid!=''
//	    Criterion cri5=Restrictions.isNotNull("htmlPath");
	    Page<SurveyDirectory> page=new Page<SurveyDirectory>();
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
	    List<SurveyDirectory> surveys = surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	    return surveys;
	}
	
	@Override
	public List<SurveyDirectory> findByT1() {
	    Criterion cri1=Restrictions.eq("visibility", 1);
	    Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
	    Criterion cri3=Restrictions.eq("surveyTag", 1);
	    Criterion cri4=Restrictions.isNull("sid");
	    Page<SurveyDirectory> page=new Page<SurveyDirectory>();
	    page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
	    List<SurveyDirectory> surveys = surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	    return surveys;
	}

	@Override
	public SurveyDirectory createBySurvey(String fromBankId, String surveyName,
										  String tag) {//new
		SurveyDirectory surveyDirectory = buildCopyObj(fromBankId, surveyName,
				tag);

		saveUserSurvey(surveyDirectory);
		String belongId=surveyDirectory.getId();
		List<Question> questions=questionManager.find(fromBankId, tag);
		questionManager.saveBySurvey(belongId, 2 , questions);
		return surveyDirectory;
	}

	private SurveyDirectory buildCopyObj(String fromBankId, String surveyName,String tag) {
		SurveyDirectory surveyDirectory=new SurveyDirectory();
		surveyDirectory.setSurveyName(surveyName);
		surveyDirectory.setDirType(2);
		surveyDirectory.setSurveyDetail(new SurveyDetail());
		SurveyDirectory directory=getSurvey(fromBankId);
		directory.setExcerptNum(directory.getExcerptNum()+1);
		super.save(directory);
		surveyDirectory.setSurveyQuNum(directory.getSurveyQuNum());
		//surveyDirectory.setSurveyName(directory.getSurveyName());
		surveyDirectory.getSurveyDetail().setSurveyNote(surveyDirectory.getSurveyDetail().getSurveyNote());
		return surveyDirectory;
	}

}
