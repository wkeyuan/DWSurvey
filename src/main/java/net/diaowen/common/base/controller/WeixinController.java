package net.diaowen.common.base.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.weixin.WeixinMpService;
import net.diaowen.common.plugs.weixin.aes.MessageUtil;
import net.diaowen.common.plugs.weixin.aes.SHA1;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.common.RoleCode;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.WxUserinfo;
import net.diaowen.dwsurvey.service.RandomCodeManager;
import net.diaowen.dwsurvey.service.UserManager;
import net.diaowen.dwsurvey.service.WxUserinfoManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keyuan on 2019/8/5.
 */
@Controller
@RequestMapping("/api/dwsurvey/anon/weixin")
public class WeixinController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private WeixinMpService weixinMpService;
    @Autowired
    private WxUserinfoManager wxUserinfoManager;
    @Autowired
    private RandomCodeManager randomCodeManager;
//    @Autowired
//    private DwUserRoleManager dwUserRoleManager;


    /**
     * 微信验证token
     */
    @RequestMapping(value = "/token.do",method= RequestMethod.GET)
    public String tokenGet(HttpServletRequest request,HttpServletResponse response){
        String resultEchostr = "error";
        try{
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
//          /weixin/token.do?signature=00f51abf5fe5f72fa320c302cf082a38eb9e4295&echostr=4610130048411495279&timestamp=1596681070&nonce=231696381
            if(timestamp!=null && nonce!=null){
                String[] sortStr =  new String[]{timestamp,nonce, DWSurveyConfig.DWSURVEY_WEIXIN_SERVER_TOKEN};
                String str = SHA1.sortStr(sortStr);
                String sha1_0 =  SHA1.getSha1(str);
                if(sha1_0.equals(signature)){
                    //解析消息体
                    resultEchostr = echostr;
                }
            }
            response.getWriter().write(resultEchostr);
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }

    @RequestMapping(value = "/token.do",method= RequestMethod.POST)
    public String tokenPost(HttpServletRequest request,HttpServletResponse response){
        try{
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
//          /weixin/token.do?signature=00f51abf5fe5f72fa320c302cf082a38eb9e4295&echostr=4610130048411495279&timestamp=1596681070&nonce=231696381
            if(timestamp!=null && nonce!=null){
                String[] sortStr =  new String[]{timestamp,nonce,DWSurveyConfig.DWSURVEY_WEIXIN_SERVER_TOKEN};
                String str = SHA1.sortStr(sortStr);
                String sha1_0 =  SHA1.getSha1(str);
                if(sha1_0.equals(signature)){
                    //解析消息体
                    if(echostr!=null){
                        response.getWriter().write(echostr);
                        return null;
                    }else{
                        Map<String, String> map= MessageUtil.parseXml(request);
                         /*<ToUserName><![CDATA[toUser]]></ToUserName>
  <FromUserName><![CDATA[FromUser]]></FromUserName>
  <CreateTime>123456789</CreateTime>
  <MsgType><![CDATA[event]]></MsgType>
  <Event><![CDATA[subscribe]]></Event>
  <EventKey><![CDATA[qrscene_123123]]></EventKey>
  <Ticket><![CDATA[TICKET]]></Ticket>*/
                        String toUserName = map.get("ToUserName");
                        String fromUserName = map.get("FromUserName");
                        String createTime = map.get("CreateTime");
                        String msgType = map.get("MsgType");
                        String event = map.get("Event");
                        if("event".equals(msgType)){
                            //事件扫送
                            if(event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
                                //用户关注时的推送
                                //判断是否有二维码及场景标识，如果有则是扫一扫绑定
                                String ticket = map.get("Ticket");
                                if(ticket!=null){
                                    String eventKey = map.get("EventKey");
                                    String qrScene = eventKey.replace("qrscene_","");
                                    wxQrScene(fromUserName,qrScene,ticket);
                                }
                            }else if(event.equals("SCAN")){
                                //用户扫码时的推送
                                String ticket = map.get("Ticket");
                                if(ticket!=null){
                                    String eventKey = map.get("EventKey");
                                    wxQrScene(fromUserName,eventKey,ticket);
                                }
                            }
                        }
                    }
                }
            }
            response.getWriter().write("");
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }

    public void wxQrScene(String fromUserName,String sceneStr,String ticket){
        if(sceneStr!=null){
            String[] sceneValues = sceneStr.split("_");
            String sceneType = sceneValues[0];
            String sceneValue = sceneValues[1];
            if(WeixinMpService.USER_LOGIN_WEIXIN.equals(sceneType)) {
                userLoginWXScene(fromUserName,sceneValue,ticket);
            }
        }
    }

    public void userLoginWXScene(String fromUserName,String sceneValue,String ticket){
        //如果openId没有对应的用户，则创建新用户。
        //前台扫码完成后，进行定时任务查询，查询任务状态，如果是成功，则自动触发登录操作。
//        User user = userManager.findBySceneValue(sceneValue);
//        User user = userManager.findById(sceneValue);
        User user = userManager.findByOpenId(fromUserName);

        if(user==null){
            //表示之前没有创建过，创建这个用户
            user = new User();
            user.setWxOpenId(fromUserName);
            user.setLoginName("wx_"+RandomUtils.random(1000000l,9999999l));
            user.setEmail(user.getLoginName()+"@dwsurvey.cn");
            user.setSessionId(sceneValue);
            user.setCreateBy("wx");
            user.setStatus(2);
            String plainPassword = sceneValue + RandomUtils.random(1000000l,9999999l);
            user.setPlainPassword(plainPassword);
            userManager.adminSave(user);
        }else{
            user.setSessionId(sceneValue);
            userManager.adminSave(user);
        }

        if (StringUtils.isNotEmpty(fromUserName)) {
            //获取用户的信息
            WxUserinfo wxUserinfo = weixinMpService.getUserInfo(fromUserName);
            if(wxUserinfo!=null && user!=null) {
                wxUserinfo.setUserId(user.getId());
                wxUserinfoManager.save(wxUserinfo);
                user.setName(wxUserinfo.getNickname());
                userManager.adminSave(user);
            }
        }

        //演示环境，正式处理请去掉
        if("demo".equals(DWSurveyConfig.DWSURVEY_SITE)){
//            dwUserRoleManager.addUserRoleCode(user.getId(), RoleCode.SUPER_ADMIN);
        }

    }

    //getJsApiTicket
    @RequestMapping(value = "/js-api-ticket.do",method= RequestMethod.GET)
    @ResponseBody
    public HttpResult jsApiTicketGet(HttpServletRequest request, HttpServletResponse response){
        String resultEchostr = "error";
        try{
            // 随机字符串
            String url = request.getParameter("url");
            System.out.println("url:"+url);
            String jsapiTicket = weixinMpService.getJsApiTicket();
            String nonceStr = RandomUtils.buildEntCode();
            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
            Map<String,String> maps = new HashMap<>();
            maps.put("jsapiTicket",jsapiTicket);
            maps.put("nonceStr",nonceStr);
            maps.put("timestamp",timestamp);
            maps.put("appId",DWSurveyConfig.DWSURVEY_WEIXIN_APP_ID);

            String sortStr = "jsapi_ticket="+jsapiTicket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
//            sortStr = sortStr.toLowerCase();
            System.out.println("sortStr2:"+sortStr);
            String sha1 =  SHA1.getSha1(sortStr);
            maps.put("signature",sha1);
            System.out.println("sign"+sha1);
            return HttpResult.SUCCESS(maps);
        }catch (Exception e){
            e.printStackTrace();;
        }
        return HttpResult.FAILURE();
    }



}
