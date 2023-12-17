package net.diaowen.dwsurvey.service;

import java.io.IOException;
import java.util.List;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.SurveyDirectory;

/**
 * 问卷处理
 * @author keyuan(keyuan258@gmail.com)
 *
 */
public interface SurveyDirectoryManager extends BaseService<SurveyDirectory, String>{

	/**
	 * 根据 最底层对象，得到此对象所在的目录结构
	 * @param surveyDirectory
	 * @return
	 */
	public List<SurveyDirectory> findPath(SurveyDirectory surveyDirectory);
	/**
	 * 通过id获取目录
	 * @param id
	 * @return
	 */
	public SurveyDirectory getSurvey(String id);
	/**
	 * 通过id获取唯一目录
	 * @param id
	 * @return
	 */
	public SurveyDirectory findUniqueBy(String id);
	/**
	 * 通过短链接获取survey
	 * @param sId
	 * @return
	 */
	public SurveyDirectory getSurveyBySid(String sId);
	/**
	 * 通过用户id和问卷id获取目录
	 * @param id
	 * @param userId
	 * @return
	 */
	public SurveyDirectory getSurveyByUser(String id, String userId);
	/**
	 * 获取问卷配置的详细信息
	 * @param id
	 * @param directory
	 */
	public void getSurveyDetail(String id, SurveyDirectory directory);
	/**
	 * 更新问卷数据
	 * @param entity
	 */
	public void upSurveyData(SurveyDirectory entity);
	/**
	 * 处理问卷数据
	 * @param entity
	 */
	public void executeSurvey(SurveyDirectory entity);
	/**
	 * 关闭问卷
	 * @param entity
	 */
	public void closeSurvey(SurveyDirectory entity);
	/**
	 * 通过名字寻找全局唯一问卷
	 * @param id
	 * @param parentId
	 * @param surveyName
	 * @return
	 */
	public SurveyDirectory findByNameUn(String id, String parentId, String surveyName);
	/**
	 * 重新设计SurveyDirectory并保存
	 * @param entity
	 */
	public void backDesign(SurveyDirectory entity);
	/**
	 * 保存用户的 SurveyDirectory
	 * @param t
	 */
	public void saveUser(SurveyDirectory t);
	/**
	 * 为当前用户保存问卷
	 * @param entity
	 */
	public void saveUserSurvey(SurveyDirectory entity);
	/**
	 * 通过名字寻找用户唯一问卷
	 * @param id
	 * @param surveyName
	 * @return
	 */
	public SurveyDirectory findByNameUserUn(String id, String surveyName);
	/**
	 * 查找问卷和问卷目录
	 * @param page
	 * @param surveyName
	 * @param surveyState
	 * @param isShare
	 * @return
	 */
	public Page<SurveyDirectory> findPage(Page<SurveyDirectory> page,String surveyName,Integer surveyState,Integer isShare);
	/**
	 * 新建问卷目录
	 * @return
	 */
	public List<SurveyDirectory> newSurveyList();
	/**
	 * 更新SurveyDirectory文本
	 * @param entity
	 */
	public void upSuveyText(SurveyDirectory entity);
	/**
	 * 保存SurveyDirectory对象
	 * @param surveyDirectory
	 */
	public void checkUp(SurveyDirectory surveyDirectory);
	/**
	 * 查找下一个创建的目录（按时间排序）
	 * @param directory
	 * @return
	 */
	public SurveyDirectory findNext(SurveyDirectory directory);
	/**
	 * 保存目录所有内容
	 * @param directory
	 */
	public void saveAll(SurveyDirectory directory);
	/**
	 * 查找用户id下的surveyDirectory
	 * @param page
	 * @param surveyDirectory
	 * @return
	 */
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page, SurveyDirectory surveyDirectory);
	/**
	 * 根据用户id检索surveyDirectory列表下的所有surveyDirectory并根据创建日期降序对它们进行排序
	 * @param page
	 * @param surveyName
	 * @param surveyState
	 * @return
	 */
	public Page<SurveyDirectory> findByUser(Page<SurveyDirectory> page, String surveyName,Integer surveyState);
	/**
	 * 按组检索目录
	 * @param groupId1
	 * @param groupId2
	 * @param page
	 * @return
	 */
	public Page<SurveyDirectory> findByGroup(String groupId1, String groupId2, Page<SurveyDirectory> page);
	/**
	 * 查找问卷
	 * @return
	 */
	public List<SurveyDirectory> findByIndex();
	/**
	 * 查找问卷
	 * @return
	 */
	public List<SurveyDirectory> findByT1();
	/**
	 * admin保存，并设置短链接
	 * @param t
	 */
	public void saveByAdmin(SurveyDirectory t);
	/**
	 * 根据问卷信息检索surveyDirectory列表下的所有surveyDirectory并根据创建日期降序对它们进行排序
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<SurveyDirectory> findModel(Page<SurveyDirectory> page,
                                           SurveyDirectory entity);
	/**
	 * 根据surveyDirectory=创建问卷
	 * @param fromBankId
	 * @param surveyName
	 * @param tag
	 * @return
	 */
	public SurveyDirectory createBySurvey(String fromBankId, String surveyName,
                                          String tag);
	/**
	 * 更新问卷状态并保存问卷
	 * @param survey
	 * @throws IOException
	 */
	public void devSurvey(SurveyDirectory survey) throws IOException;
	/**
	 * 根据问卷id获取问卷信息构造json并保存
	 * @param surveyId
	 * @return
	 */
	public String devSurveyJson(String surveyId);
	/**
	 * 根据id批量删除 surveyDirectory
	 * @param id
	 */
	public void delete(String[] id);
	/**
	 * 更新surveyDirectory 状态
	 * @param surveyId
	 * @param surveyState
	 * @throws IOException
	 */
	void upSurveyState(String surveyId, Integer surveyState) throws IOException;

}
