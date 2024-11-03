package net.diaowen.dwsurvey.service.impl;

import net.diaowen.common.service.BaseServiceImpl;
import net.diaowen.dwsurvey.dao.WxUserinfoDao;
import net.diaowen.dwsurvey.entity.WxUserinfo;
import net.diaowen.dwsurvey.service.WxUserinfoManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author keyuan
 * keyuan258@gmail.com
 *
 */

@Service
public class WxUserinfoManagerImpl extends BaseServiceImpl<WxUserinfo, String> implements WxUserinfoManager {

	@Autowired
	private WxUserinfoDao wxUserinfoDao;

	@Override
	public void setBaseDao() {
		this.baseDao=wxUserinfoDao;
	}

	@Transactional
	@Override
	public WxUserinfo saveAndResult(WxUserinfo wxUserinfo) {
		String openid = wxUserinfo.getOpenid();
		WxUserinfo wxUserinfoDB = findByOpenid(openid);
		if(wxUserinfoDB!=null){

			String nickName = wxUserinfo.getNickname();
			Integer sex = wxUserinfo.getSex();
			String province = wxUserinfo.getProvince();
			String city = wxUserinfo.getCity();
			String country = wxUserinfo.getCountry();
			String headimgurl = wxUserinfo.getHeadimgurl();
			String privilege = wxUserinfo.getPrivilege();
			String unionid = wxUserinfo.getUnionid();
			Date tokenDate = wxUserinfo.getTokenDate();
			String access_token = wxUserinfo.getAccess_token();
			String expires_in = wxUserinfo.getExpires_in();
			String refresh_token = wxUserinfo.getRefresh_token();
			Integer scope = wxUserinfo.getScope();// 1 snsapi_base  2 snsapi_userinfo

			if(nickName!=null) wxUserinfoDB.setNickname(nickName);
			if(sex!=null) wxUserinfoDB.setSex(sex);
			if(province!=null) wxUserinfoDB.setProvince(province);
			if(city!=null) wxUserinfoDB.setCity(city);
			if(country!=null) wxUserinfoDB.setCountry(country);
			if(headimgurl!=null) wxUserinfoDB.setHeadimgurl(headimgurl);
			if(privilege!=null) wxUserinfoDB.setPrivilege(privilege);
			if(unionid!=null) wxUserinfoDB.setUnionid(unionid);
			if(tokenDate!=null) wxUserinfoDB.setTokenDate(tokenDate);
			if(access_token!=null) wxUserinfoDB.setAccess_token(access_token);
			if(expires_in!=null) wxUserinfoDB.setExpires_in(expires_in);
			if(refresh_token!=null) wxUserinfoDB.setRefresh_token(refresh_token);
			if(scope!=null) wxUserinfoDB.setScope(scope);
//			if(wxUserinfoDB.getScope()!=2){
				//防止已经取到userinfo的账户，在另一份问卷不需要获取用户信息的状态下被重置。
				//不用此字段标记，单独有一个字段标记是否取过userinfo
//				if(scope!=null) wxUserinfoDB.setScope(scope);
//			}
			super.save(wxUserinfoDB);
			return wxUserinfoDB;
		}else{
			super.save(wxUserinfo);
			return wxUserinfo;
		}
	}

	@Transactional
	@Override
	public void save(WxUserinfo wxUserinfo) {
		saveAndResult(wxUserinfo);
	}


	public WxUserinfo findByOpenid(String openid) {
		Criterion cri1 = Restrictions.eq("openid",openid);
		return wxUserinfoDao.findFirst("createDate",true,cri1);
	}


	@Override
	public WxUserinfo findByWxCode(String accessTokenCode) {
		Criterion cri1 = Restrictions.eq("accessTokenCode",accessTokenCode);
		return wxUserinfoDao.findUnique(cri1);
	}
}
