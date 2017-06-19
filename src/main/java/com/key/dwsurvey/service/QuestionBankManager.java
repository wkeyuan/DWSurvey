package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.plugs.page.Page;
import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.QuestionBank;

/**
 * 题库
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuestionBankManager extends BaseService<QuestionBank, String>{
	/**
	 * 根据 最底层对象，得到此对象所在的目录结构
	 * @param surveyDirectory
	 * @return
	 */
	public List<QuestionBank> findPath(QuestionBank questionBank);

	public QuestionBank getBank(String parentId);

	public QuestionBank findByNameUn(String id, String parentId, String bankName);

	public Page<QuestionBank> findPage(Page<QuestionBank> page, QuestionBank entity);

	public void executeBank(String id);
	
	public void closeBank(String id);
	
	public List<QuestionBank> newQuestionBank() ;
}
