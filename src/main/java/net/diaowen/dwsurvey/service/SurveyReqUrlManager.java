package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.SurveyReqUrl;

/**
 * 短链辅助
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyReqUrlManager extends BaseService<SurveyReqUrl, String> {

	public void save(SurveyReqUrl surveyReqUrl);

    public SurveyReqUrl findBySurveyId(String surveyId, int type);

    public SurveyReqUrl newSurveyReqUrl(String surveyId,int type);
}
