package net.diaowen.dwsurvey.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.QuType;
import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.common.utils.RandomUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.dao.SurveyDirectoryDao;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.SurveyDetail;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyStats;
import net.diaowen.dwsurvey.service.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Service("surveyDirectoryManager")
public class SurveyDirectoryManagerImpl extends BaseServiceImpl<SurveyDirectory, String> implements SurveyDirectoryManager {

	@Resource
	private SurveyDirectoryManagerImpl surveyDirectoryManager;

	@Autowired
	private SurveyDirectoryDao surveyDirectoryDao;
	@Autowired
	private SurveyDetailManager surveyDetailManager;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyStatsManager surveyStatsManager;
	@Autowired
	private AccountManager accountManager;



	@Override
	public void setBaseDao() {
		this.baseDao=surveyDirectoryDao;
	}

	/**
	 * 保存问卷目录或者问卷
	 * @param t
	 */
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

	/**
	 * admin保存，并设置短链接
	 * @param t
	 */
	@Transactional
	public void saveByAdmin(SurveyDirectory t){
		String sId=t.getSid();
		if(sId==null || sId.isEmpty()){
			sId=RandomUtils.randomStr(6, 12);
			t.setSid(sId);
		}
		surveyDirectoryDao.save(t);
	}

	/**
	 * 得到当前目录所在的目录位置
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory) {
		List<SurveyDirectory> resultList=new ArrayList<>();
		if(surveyDirectory!=null){
			List<SurveyDirectory> dirPathList=new ArrayList<>();
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

	/**
	 * 通过短链接获取survey
	 * @param sid
	 * @return
	 */
	@Override
	public SurveyDirectory getSurveyBySid(String sid) {
		Criterion criterion=Restrictions.eq("sid", sid);
		SurveyDirectory surveyDirectory = surveyDirectoryDao.findUnique(criterion);
		getSurveyDetail(surveyDirectory.getId(),surveyDirectory);
		return surveyDirectory;
	}

	/**
	 * 通过id获取目录
	 * @param id
	 * @return
	 */
	@Override
	public SurveyDirectory getSurvey(String id) {
		if(id==null || id.isEmpty()){
			return new SurveyDirectory();
		}
		SurveyDirectory directory=get(id);
		getSurveyDetail(id,directory);
		return directory;
	}

	/**
	 * 通过id获取唯一目录
	 * @param id
	 * @return
	 */
	public SurveyDirectory findUniqueBy(String id) {
		if(id==null || id.isEmpty()){
			return new SurveyDirectory();
		}
		SurveyDirectory directory=surveyDirectoryDao.findUniqueBy("id",id);
		getSurveyDetail(id,directory);
		return directory;
	}

	/**
	 * 通过用户id和问卷id获取目录
	 * @param id
	 * @param userId
	 * @return
	 */
	public SurveyDirectory getSurveyByUser(String id,String userId) {
		SurveyDirectory directory=get(id);
		if(userId.equals(directory.getUserId())){
			getSurveyDetail(id,directory);
			return directory;
		}
		return null;
	}

	/**
	 * 获取问卷配置的详细信息
	 * @param id
	 * @param directory
	 */
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

	/**
	 * 更新问卷数据
	 * @param entity
	 */
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
				}else{
					anItemLeastNum++;
				}
			}
			entity.setSurveyQuNum(questions.size());
			entity.setAnItemLeastNum(anItemLeastNum);
		}
	}

	/**
	 * 处理问卷数据
	 * @param entity
	 */
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

	/**
	 * 关闭问卷
	 * @param entity
	 */
	@Override
	@Transactional
	public void closeSurvey(SurveyDirectory entity) {
		entity.setSurveyState(2);
		//计算可以回答的题量
		super.save(entity);
		//生成全局统计结果记录表
	}

	/**
	 * 根据id删除问卷
	 * @param id
	 */
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
				surveyDirectoryManager.delete(surveyDirectory);
			}
		}
	}

	/**
	 * 删除整个问卷目录
	 * @param parentDirectory
	 */
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
					surveyDirectoryManager.delete(surveyDirectory);
				}
			}
		}
	}

	/**
	 * 通过名字寻找全局唯一问卷
	 * @param id
	 * @param parentId
	 * @param surveyName
	 * @return
	 */
	@Override
	public SurveyDirectory findByNameUn(String id,String parentId, String surveyName) {
		List<Criterion> criterions=new ArrayList<>();
		Criterion eqName=Restrictions.eq("surveyName", surveyName);
		Criterion eqParentId=Restrictions.eq("parentId", parentId);
		criterions.add(eqName);
		criterions.add(eqParentId);

		if(id!=null && !id.isEmpty()){
			Criterion eqId=Restrictions.ne("id", id);
			criterions.add(eqId);
		}
		return surveyDirectoryDao.findFirst(criterions);
	}

	/**
	 * 通过名字寻找用户唯一问卷
	 * @param id
	 * @param surveyName
	 * @return
	 */
	@Override
	public SurveyDirectory findByNameUserUn(String id, String surveyName) {
		User user=accountManager.getCurUser();
		if(user!=null){
			List<Criterion> criterions=new ArrayList<>();
			Criterion eqName=Restrictions.eq("surveyName", surveyName);
			Criterion eqUserId=Restrictions.eq("userId", user.getId());
			criterions.add(eqName);
			criterions.add(eqUserId);

			if(id!=null && !id.isEmpty()){
				Criterion eqId=Restrictions.ne("id", id);
				criterions.add(eqId);
			}
			return surveyDirectoryDao.findFirst(criterions);
		}
		return null;
	}

	/**
	 * 重新设计SurveyDirectory并保存
	 * @param entity
	 */
	@Override
	@Transactional
	public void backDesign(SurveyDirectory entity) {
		entity.setSurveyState(0);
		//计算可以回答的题量
		super.save(entity);
	}

	/**
	 * 保存SurveyDirectory对象
	 * @param entity
	 */
	@Transactional
	public void checkUp(SurveyDirectory entity) {
		//计算可以回答的题量
		super.save(entity);
	}


	/**
	 * 更新SurveyDirectory文本
	 * @param t
	 */
	@Transactional
	public void upSuveyText(SurveyDirectory t){
		String id=t.getId();
		if(id!=null&&!id.isEmpty()){
			super.save(t);
			//保存SurveyDirectory
			if(t.getDirType()==2){
				SurveyDetail surveyDetail=t.getSurveyDetail();
				surveyDetail.setDirId(t.getId());
				surveyDetailManager.save(surveyDetail);
			}
		}
	}

	/**
	 * 保存用户的 SurveyDirectory
	 * @param t
	 */
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

	/**
	 * 为当前用户保存问卷
	 * @param entity
	 */
	public void saveUserSurvey(SurveyDirectory entity) {
		User user = accountManager.getCurUser();
		if(user!=null){
			String enId = entity.getId();
			String userId=user.getId();
			if(enId==null || enId.isEmpty()){
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
					surveyDirectoryManager.saveUser(surveyDirUser);
				}
				entity.setParentId(surveyDirUser.getId());

				entity.setUserId(userId);
				surveyDirectoryManager.saveUser(entity);
			}else{
				//判断当前人有无权限修改
				String enUserId=entity.getUserId();
				if(userId.equals(enUserId)){
					entity.setUserId(userId);
					surveyDirectoryManager.saveUser(entity);
				}
			}
		}
	}

	/**
	 * 查找问卷和问卷目录
	 * @param page
	 * @param surveyName
	 * @param surveyState
	 * @param isShare
	 * @return
	 */
	@Override
	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,String surveyName,Integer surveyState,Integer isShare) {
		page.setOrderBy("createDate");
		page.setOrderDir("desc");

		List<Criterion> criterions=new ArrayList<>();
		criterions.add(Restrictions.eq("visibility", 1));
		criterions.add(Restrictions.eq("dirType", 2));
		criterions.add(Restrictions.eq("surveyModel", 1));

		if(surveyName!=null){
			criterions.add(Restrictions.like("surveyName", "%"+surveyName+"%"));
		}
		if(surveyState!=null){
			criterions.add(Restrictions.eq("surveyState", surveyState));
		}
		if(isShare!=null){
			criterions.add(Restrictions.eq("isShare", isShare));
		}

		return surveyDirectoryDao.findPageList(page, criterions);
	}

	/**
	 * 新建问卷目录
	 * @return
	 */
	public List<SurveyDirectory> newSurveyList() {
		List<SurveyDirectory> result=new ArrayList<>();
		try{
			Page<SurveyDirectory> page=new Page<>();
			page.setPageSize(25);
			page=findPage(page,null,1,null);
			result=page.getResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 保存目录所有内容
	 * @param directory
	 */
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
	}

	/**
	 * 查找下一个创建的目录（按时间排序）
	 * @param directory
	 * @return
	 */
	@Override
	public SurveyDirectory findNext(SurveyDirectory directory) {
		Date date=directory.getCreateDate();
		Criterion criterion=Restrictions.gt("createDate", date);
		return surveyDirectoryDao.findFirst(criterion);
	}

	/**
	 * 查找用户id下的surveyDirectory
	 * @param page
	 * @param entity
	 * @return
	 */
	@Override
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page,
											SurveyDirectory entity) {
		if(entity!=null) return findByUser(page,entity.getSurveyName(),entity.getSurveyState());
		return page;
	}

	/**
	 * 根据用户id检索surveyDirectory列表下的所有surveyDirectory并根据创建日期降序对它们进行排序
	 * @param page
	 * @param surveyName
	 * @param surveyState
	 * @return
	 */
	@Override
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page, String surveyName, Integer surveyState) {
		User user=accountManager.getCurUser();
		if(user!=null){
			List<Criterion> criterions=new ArrayList<>();
			criterions.add(Restrictions.eq("userId", user.getId()));
			criterions.add(Restrictions.eq("visibility", 1));
			criterions.add(Restrictions.eq("dirType", 2));
			criterions.add(Restrictions.eq("surveyModel", 1));
			if(surveyState!=null) criterions.add(Restrictions.eq("surveyState", surveyState));
			if(StringUtils.isNotEmpty(surveyName)) criterions.add(Restrictions.like("surveyName", "%"+surveyName+"%"));
			page.setOrderBy("createDate");
			page.setOrderDir("desc");
			page=surveyDirectoryDao.findPageList(page,criterions);
		}
		return page;
	}

	/**
	 * 按组检索目录
	 * @param groupId1
	 * @param groupId2
	 * @param page
	 * @return
	 */
	public Page<SurveyDirectory> findByGroup(String groupId1, String groupId2, Page<SurveyDirectory> page) {


		List<Criterion> criterions = new ArrayList<>();
		if(groupId1!=null && !"".equals(groupId1)){
			Criterion cri1=Restrictions.eq("groupId1", groupId1);
			criterions.add(cri1);
		}
		if(groupId2!=null && !"".equals(groupId2)){
			Criterion cri1_2=Restrictions.eq("groupId2", groupId2);
			criterions.add(cri1_2);
		}

		Criterion cri2=Restrictions.eq("visibility", 1);
		Criterion cri4=Restrictions.eq("surveyModel", 4);

		criterions.add(cri2);
		criterions.add(cri4);
		page.setOrderBy("createDate");
		page.setOrderDir("desc");

		return surveyDirectoryDao.findPage(page,criterions.toArray(new Criterion[criterions.size()]) );
	}


	/**
	 * 根据问卷信息检索surveyDirectory列表下的所有surveyDirectory并根据创建日期降序对它们进行排序
	 * @param page
	 * @param entity
	 * @return
	 */
	@Override
	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
										   SurveyDirectory entity) {
		Integer surveyState=entity.getSurveyState();
		String surveyName=entity.getSurveyName();
		List<Criterion> criterions=new ArrayList<>();

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
		Criterion cri4=Restrictions.eq("surveyModel", 4);
		criterions.add(cri4);
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		return surveyDirectoryDao.findPageList(page,criterions);
	}

	/**
	 * 查找问卷
	 * @return
	 */
	@Override
	public List<SurveyDirectory> findByIndex() {
		Criterion cri1=Restrictions.eq("visibility", 1);
		Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
		Criterion cri3=Restrictions.eq("surveyTag", 1);
		Criterion cri4=Restrictions.isNull("sid");
		Page<SurveyDirectory> page=new Page<>();
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
        return surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	}
	/**
	 * 查找问卷
	 * @return
	 */
	@Override
	public List<SurveyDirectory> findByT1() {
		Criterion cri1=Restrictions.eq("visibility", 1);
		Criterion cri2=Restrictions.eq("parentId", "402880e5428a2dca01428a2f1f290000");
		Criterion cri3=Restrictions.eq("surveyTag", 1);
		Criterion cri4=Restrictions.isNull("sid");
		Page<SurveyDirectory> page=new Page<>();
		page.setOrderBy("createDate");
		page.setOrderDir("desc");
		page.setPageSize(10);
        return surveyDirectoryDao.findPage(page, cri1,cri2,cri3,cri4).getResult();
	}

	/**
	 * 根据surveyDirectory=创建问卷
	 * @param fromBankId
	 * @param surveyName
	 * @param tag
	 * @return
	 */
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

	/**
	 * 拷贝 surveyDirectory=
	 * @param fromBankId
	 * @param surveyName
	 * @param tag
	 * @return
	 */
	private SurveyDirectory buildCopyObj(String fromBankId, String surveyName,String tag) {
		SurveyDirectory surveyDirectory=new SurveyDirectory();
		surveyDirectory.setSurveyName(surveyName);
		surveyDirectory.setSurveyNameText(surveyName);
		surveyDirectory.setDirType(2);
		surveyDirectory.setSurveyDetail(new SurveyDetail());
		SurveyDirectory directory=getSurvey(fromBankId);
		directory.setExcerptNum(directory.getExcerptNum()+1);
		super.save(directory);
		surveyDirectory.setSurveyQuNum(directory.getSurveyQuNum());
		surveyDirectory.getSurveyDetail().setSurveyNote(surveyDirectory.getSurveyDetail().getSurveyNote());
		return surveyDirectory;
	}


	/**
	 * 根据id批量删除 surveyDirectory
	 * @param id
	 */
	@Transactional
	@Override
	public void delete(String[] id) {
		if(id!=null){
			for (String idValue:id) {
				surveyDirectoryManager.delete(idValue);
			}
		}
	}

	/**
	 * 更新surveyDirectory 状态
	 * @param surveyId
	 * @param surveyState
	 * @throws IOException
	 */
	@Transactional
	@Override
	public void upSurveyState(String surveyId, Integer surveyState) throws IOException {
		if(surveyId!=null && surveyState!=null){
			SurveyDirectory survey = findUniqueBy(surveyId);
			if(surveyState==1){
				surveyDirectoryManager.devSurvey(survey);
			}else{
				survey.setSurveyState(surveyState);
				super.save(survey);
			}
		}

	}

	/**
	 * 更新问卷状态并保存问卷
	 * @param survey
	 * @throws IOException
	 */
	@Transactional
	public void devSurvey(SurveyDirectory survey) throws IOException {
		String surveyId = survey.getId();
		survey.setSurveyState(1);
		super.save(survey);
		String path = devSurveyJson(surveyId);
		survey.setJsonPath(path);
		super.save(survey);
	}

	/**
	 * 根据问卷id获取问卷信息构造json并保存
	 * @param surveyId
	 * @return
	 */
	@Override
	public String devSurveyJson(String surveyId) {
		try {
			SurveyDirectory surveyDirectory = findUniqueBy(surveyId);
			String sid = surveyDirectory.getSid();
			ObjectMapper mapper = new ObjectMapper();

			String infoJsonString = mapper.writeValueAsString(HttpResult.SUCCESS(surveyDirectory));
			String savePath = File.separator+"file"+File.separator+"survey"+File.separator+sid+File.separator;
			writerJson(infoJsonString, savePath, sid+"_info.json");

			List<Question> questions=questionManager.findDetails(surveyDirectory.getId(), "2");
			surveyDirectory.setQuestions(questions);
			surveyDirectory.setSurveyQuNum(questions.size());
			String jsonString = mapper.writeValueAsString(HttpResult.SUCCESS(surveyDirectory));
			String fileName = sid+".json";
			writerJson(jsonString, savePath, fileName);

			return savePath+fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向指定名字本地文件写入 json，不存在则创建
	 * @param jsonString
	 * @param savePath
	 * @param fileName
	 * @throws IOException
	 */
	private void writerJson(String jsonString, String savePath, String fileName) throws IOException {
		String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
		File dirFile = new File(rootPath+ savePath);
		if  (!dirFile.exists()  && !dirFile .isDirectory()) {
			dirFile.mkdirs();
		}
		File localFile = new File(rootPath+ savePath + fileName);
		if (!localFile.exists()) {
			localFile.createNewFile();
		}
		try(FileOutputStream fos = new FileOutputStream(localFile)){
			fos.write(jsonString.getBytes());
		}
	}




}
