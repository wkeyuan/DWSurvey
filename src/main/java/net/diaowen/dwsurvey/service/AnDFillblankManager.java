package net.diaowen.dwsurvey.service;

import java.util.List;

import net.diaowen.common.plugs.page.Page;
import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.AnDFillblank;
import net.diaowen.dwsurvey.entity.Question;

/**
 * 多项填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnDFillblankManager extends BaseService<AnDFillblank, String>{
	public List<AnDFillblank> findAnswer(String belongAnswerId, String quId);

	public void findGroupStats(Question question);

    Page<AnDFillblank> findPage(Page<AnDFillblank> anPage, String quItemId);
}
