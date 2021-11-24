package net.diaowen.dwsurvey.service;

import java.util.List;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.QuCheckbox;

/**
 * 多选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuCheckboxManager  extends BaseService<QuCheckbox, String>{

	public List<QuCheckbox> findByQuId(String quId);

	public QuCheckbox upOptionName(String quId, String quItemId, String optionName);

	public List<QuCheckbox> saveManyOptions(String quId, List<QuCheckbox> quCheckboxs);

	public void ajaxDelete(String quItemId);

	public void saveAttr(String quItemId, String isNote);
	
}
