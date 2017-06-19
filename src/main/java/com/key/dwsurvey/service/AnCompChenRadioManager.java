package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.AnCompChenRadio;

/**
 * 复合矩陈单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface AnCompChenRadioManager extends BaseService<AnCompChenRadio, String>{
	
	public List<AnCompChenRadio> findAnswer(String belongAnswerId,String quId);
	
}
