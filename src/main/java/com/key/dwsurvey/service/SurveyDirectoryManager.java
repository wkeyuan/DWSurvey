package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.SurveyDirectory;

/**
 * 问卷处理
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyDirectoryManager extends BaseService<SurveyDirectory, String>{

	/**
	 * 根据 最底层对象，得到此对象所在的目录结构
	 * @param surveyDirectory
	 * @return
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory);
	
	public SurveyDirectory getSurvey(String id);
	
	public SurveyDirectory getSurveyBySid(String sId);

	public SurveyDirectory getSurveyByUser(String id,String userId); 
	
	public void getSurveyDetail(String id,SurveyDirectory directory);
	
	public void upSurveyData(SurveyDirectory entity);

	public void executeSurvey(SurveyDirectory entity);

	public void closeSurvey(SurveyDirectory entity);

	public SurveyDirectory findByNameUn(String id,String parentId, String surveyName);

	public void backDesign(SurveyDirectory entity);

//	public void save(SurveyDirectory entity, String[] surGroupIds);

//	public void saveUserSurvey(SurveyDirectory entity, String[] surGroupIds);
	
	public void saveUser(SurveyDirectory t);
	
	public void saveUserSurvey(SurveyDirectory entity);

	public SurveyDirectory findByNameUserUn(String id, String surveyName);

	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,
			SurveyDirectory entity);

	public List<SurveyDirectory> newSurveyList();

	public void upSuveyText(SurveyDirectory entity);

	public void checkUp(SurveyDirectory surveyDirectory);

	public SurveyDirectory findNext(SurveyDirectory directory);
	
	public void saveAll(SurveyDirectory directory);

	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page,SurveyDirectory surveyDirectory);
	
	public Page<SurveyDirectory> findByGroup(String groupId1,String groupId2,Page<SurveyDirectory> page);

	public List<SurveyDirectory> findByIndex();
	
	public List<SurveyDirectory> findByT1();
	
	public void saveByAdmin(SurveyDirectory t);

	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
			SurveyDirectory entity);

	public SurveyDirectory createBySurvey(String fromBankId, String surveyName,
										  String tag);

}
