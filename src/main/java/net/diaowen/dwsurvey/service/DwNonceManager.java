package net.diaowen.dwsurvey.service;

import net.diaowen.common.service.BaseService;
import net.diaowen.dwsurvey.entity.DwNonce;

/**
 * 答卷业务
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface DwNonceManager extends BaseService<DwNonce, String> {

    public DwNonce findByNonce(String signature,String nonce);

    public boolean isContainNonce(String signature,String nonce);

}
