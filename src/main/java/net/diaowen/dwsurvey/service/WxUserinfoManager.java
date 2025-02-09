package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.WxUserinfo;

/**
 * 答卷业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface WxUserinfoManager extends BaseService<WxUserinfo, String> {

    public WxUserinfo saveAndResult(WxUserinfo wxUserinfo);

    public WxUserinfo findByOpenid(String openid);

    WxUserinfo findByWxCode(String weixinCode);
}
