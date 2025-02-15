package net.diaowen.dwsurvey.controller.design;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.license.LicenseStatus;
import co.elastic.clients.elasticsearch.nodes.Http;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.octo.captcha.service.image.ImageCaptchaService;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.ipaddr.IPLocation;
import net.diaowen.common.plugs.ipaddr.IPService;
import net.diaowen.common.plugs.security.token.JwtUtils;
import net.diaowen.common.plugs.web.Token;
import net.diaowen.common.utils.CookieUtils;
import net.diaowen.common.utils.NumberUtils;
import net.diaowen.dwsurvey.common.AnswerCheckData;
import net.diaowen.dwsurvey.common.DwAnswerCheckResult;
import net.diaowen.dwsurvey.common.DwAnswerEsUtils;
import net.diaowen.dwsurvey.common.DwSurveyAnswerResult;
import net.diaowen.dwsurvey.entity.*;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.service.SurveyAnswerJsonManager;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyJsonManager;
import net.diaowen.dwsurvey.service.impl.answer.SubmitSurveyAnswerCheckService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/dwsurvey/none/v6/dw-answer-survey")
public class DwAnswerSurveyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyJsonManager surveyJsonManager;
    @Autowired
    private DwAnswerEsClientService dwAnswerEsClientService;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private IPService ipService;
    @Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private SubmitSurveyAnswerCheckService answerCheckService;
    @Autowired
    private SurveyAnswerJsonManager surveyAnswerJsonManager;

    @RequestMapping(value = "/survey-json-by-sid.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult surveyJsonByCascade(@RequestParam String sId){
//        SurveyJson surveyJson = surveyJsonManager.getByCascade(sId);
        return HttpResult.SUCCESS(null);
    }

    /**
     * 获取问卷
     * @param sid
     * @return
     */
    @RequestMapping(value = "/survey-json-by-survey-id.do",method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<DwSurveyAnswerResult> surveyJsonBySurveyId(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String surveyId, @RequestParam(required = false) String sid, @RequestParam(required = false) String anPwd){
        // 针对答卷场景需要考虑这些数据通过静态缓存获取。
        SurveyJson surveyJson = getSurveyJson(surveyId, sid);
        DwSurveyAnswerResult dwSurveyAnswerResult = new DwSurveyAnswerResult();
        DwAnswerCheckResult dwAnswerCheckResult = getDwAnswerCheckResultBySurveyJson(request, surveyJson, anPwd, false);
        surveyJson = getSurveyJsonResult(dwAnswerCheckResult, surveyJson);
        if (surveyJson!=null) {
            dwSurveyAnswerResult.setSurveyJson(surveyJson);
            // 相关认证信息
            //0、token 访止重复提交
            String token = Token.getToken(request);
            dwAnswerCheckResult.setAnToken(token);
            surveyBgTimeInit(request,response,sid);
        }
        dwSurveyAnswerResult.setAnswerCheckResult(dwAnswerCheckResult);
        return HttpResult.SUCCESS(dwSurveyAnswerResult);
    }

    @RequestMapping("/save-survey-answer.do")
    @ResponseBody
    public HttpResult<DwAnswerCheckResult> saveAnswer(HttpServletRequest request, HttpServletResponse response, @RequestBody SurveyAnswerJson surveyAnswerJson) {
        try {
            String surveyId = surveyAnswerJson.getSurveyId();
            String sid = surveyAnswerJson.getSid();
            SurveyJson surveyJson = getSurveyJson(surveyId, sid);
            //构建答卷数据
            logger.debug("save SurveyAnswerJson {}", JSON.toJSONString(surveyAnswerJson));
            String answerJson = surveyAnswerJson.getAnswerJson();
            DwEsSurveyAnswer dwEsSurveyAnswer = JSON.parseObject(answerJson, DwEsSurveyAnswer.class);
//            dwEsSurveyAnswer.getAnswerCommon().setSid(sid);
            EsAnIp esAnIp = dwEsSurveyAnswer.getAnswerCommon().getAnIp();
            String ipAddr = ipService.getIp(request);
            IPLocation ipLocation = ipService.getIpLocation(ipAddr);
            if(ipLocation!=null){
                esAnIp.setAddr(ipLocation.getAddress());
                esAnIp.setProvince(ipLocation.getProvince());
                esAnIp.setCity(ipLocation.getCity());
                esAnIp.setCityV6(ipLocation.getCity());
                esAnIp.setCounty(ipLocation.getCounty());
                esAnIp.setTown(ipLocation.getTown());
            }
            esAnIp.setIp(ipAddr);
            dwEsSurveyAnswer.getAnswerCommon().setAnIp(esAnIp);
            String anPwd = dwEsSurveyAnswer.getAnPwd();
            logger.debug("anPwd {}", anPwd);
            surveyBgEndTime(request, sid, dwEsSurveyAnswer);
            calcAnQuQum(dwEsSurveyAnswer);
            //执行前置检查
            DwAnswerCheckResult dwAnswerCheckResult = getDwAnswerCheckResultBySurveyJson(request, surveyJson, anPwd, true);
            logger.debug("isShowCaptcha {}", dwAnswerCheckResult.isShowCaptcha());
            if (!dwAnswerCheckResult.isAnCheckIsPass() && dwAnswerCheckResult.getAnCheckResultCode()==409) {
                String anRandomCode = surveyAnswerJson.getAnRandomCode();
                if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), anRandomCode)) {
                    dwAnswerCheckResult.buildResult(DwAnswerCheckResult.CHECK410);
                    return HttpResult.SUCCESS(dwAnswerCheckResult);
                } else {
                    dwAnswerCheckResult.setAnCheckIsPass(true);
                }
            }
            if (!dwAnswerCheckResult.isAnCheckIsPass()) return HttpResult.SUCCESS(dwAnswerCheckResult);
            //检查通过继续执行
            //计算分数
            DwAnswerEsUtils.calcSumScore(surveyJson, dwEsSurveyAnswer);
            DwAnswerEsUtils.addAnSource(request, dwEsSurveyAnswer);
            // 先保存一份到mysql
            String sessionId = request.getRequestedSessionId();
            SurveyAnswer surveyAnswer = new SurveyAnswer();
            surveyAnswer.setHttpSessionId(sessionId);
            surveyAnswerManager.saveAnswerByEsAnswer(surveyAnswer, dwEsSurveyAnswer);
            dwAnswerCheckResult.setDbAnswerId(surveyAnswer.getId());
            // 再保存到es
            try{
                IndexResponse indexResponse = dwAnswerEsClientService.createAnswerDocByObj(dwEsSurveyAnswer);
                if (indexResponse!=null && indexResponse.id()!=null) {
                    String indexResponseId = indexResponse.id();
                    logger.debug(" answer save success id {}", indexResponseId);
                    // 保存成功
                    dwAnswerCheckResult.setIndexResponseId(indexResponseId);
                } else {
                    return HttpResult.SUCCESS(DwAnswerCheckResult.SERVER502);
                }
            } catch (Exception e) {
                logger.error("ES服务异常，答卷功能不受影响，但会影响数据分析功能");
            }
            // 保存成功
            dwAnswerCheckResult.buildResult(DwAnswerCheckResult.SUCCESS201);
            // 如果需要返回答卷结果
            dwAnswerCheckResult.setSumScore(dwEsSurveyAnswer.getAnswerCommon().getSumScore());
            addAnswerNumCookie(request, response, sid);
            return HttpResult.SUCCESS(dwAnswerCheckResult);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.EXCEPTION(e.getMessage());
        }
    }

    //获取答卷数据,要注意先做安全验证
    @RequestMapping(value = "/get-survey-answer.do", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<DwEsSurveyAnswer> answerDataById(HttpServletRequest request, String answerId){
        // 要先做安全验证
//        logger.debug("answerId {}", answerId);
//        DwEsSurveyAnswer dwEsSurveyAnswer = dwAnswerEsClientService.findById(answerId);
//        return HttpResult.SUCCESS(dwEsSurveyAnswer);
        try {
            String sessionId = request.getSession().getId();
            SurveyAnswer answer = null;
            if(StringUtils.isNotEmpty(answerId)){
                answer = surveyAnswerManager.findById(answerId);
            }
            if(answer!=null){
                boolean isPerm = false;
                //自己填写的可以获取结果
                String httpSessionId = answer.getHttpSessionId();
                if(sessionId.equals(httpSessionId)) isPerm = true;
                SurveyDirectory survey = surveyDirectoryManager.findUniqueBy(answer.getSurveyId());
                if(!isPerm) isPerm = answerCheckService.isAnswerPerm(request, answer, survey, null);
                if(isPerm){
                    SurveyAnswerJson surveyAnswerJson = surveyAnswerJsonManager.findByAnswerId(answerId);
                    String answerJson = surveyAnswerJson.getAnswerJson();
                    ObjectMapper objectMapper = new ObjectMapper();
                    DwEsSurveyAnswer dwEsSurveyAnswer = objectMapper.readValue(answerJson, DwEsSurveyAnswer.class);
                    return HttpResult.SUCCESS(dwEsSurveyAnswer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.FAILURE_MSG("没有权限");
    }

    @RequestMapping(value = "/check-answer-pwd.do", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult<DwEsSurveyAnswer> checkAnswerPwd(@RequestParam String sid, @RequestParam String anPwd){
        // 要先做安全验证
        SurveyJson surveyJson = getSurveyJson(null, sid);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode surveyJsonSimpleNode = objectMapper.readTree(surveyJson.getSurveyJsonSimple());
            if (surveyJsonSimpleNode.has("surveyAttrs")) {
                if (surveyJsonSimpleNode.get("surveyAttrs").has("anPwdAttr")) {
                    JsonNode anPwdAttr = surveyJsonSimpleNode.get("surveyAttrs").get("anPwdAttr");
                    boolean enabled = anPwdAttr.get("enabled").asBoolean();
                    String anPwdCode = anPwdAttr.get("anPwdCode").asText();
                    if (enabled && anPwdCode != null && anPwdCode.equals(anPwd)) return HttpResult.SUCCESS();
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return HttpResult.FAILURE();
    }

    /**
     *
     * @param surveyId or sid
     * @return SurveyJson
     */
    private SurveyJson getSurveyJson(String surveyId, String sid) {
        SurveyJson surveyJson = null;
        if (StringUtils.isNotEmpty(surveyId)) {
            surveyJson = surveyJsonManager.findBySurveyId(surveyId);
        }
        if (surveyJson==null && StringUtils.isNotEmpty(sid)) {
            surveyJson = surveyJsonManager.findBySid(sid);
        }
        return surveyJson;
    }

    private DwAnswerCheckResult getDwAnswerCheckResultById(HttpServletRequest request, String surveyId, String anPwd, boolean isSubmit) {
        DwAnswerCheckResult answerCheckResult = new DwAnswerCheckResult();
        if (StringUtils.isNotEmpty(surveyId) && surveyId.length()>30) {
            SurveyJson surveyJson = surveyJsonManager.findBySurveyId(surveyId);
            answerCheckResult = getDwAnswerCheckResultBySurveyJson(request, surveyJson, anPwd, isSubmit);
        } else {
            answerCheckResult.buildResult(DwAnswerCheckResult.CHECK501);
        }
        return answerCheckResult;
    }

    private DwAnswerCheckResult getDwAnswerCheckResultBySurveyJson(HttpServletRequest request, SurveyJson surveyJson, String anPwd, boolean isSubmit) {
        DwAnswerCheckResult answerCheckResult = new DwAnswerCheckResult();
        if (surveyJson !=null) {
            answerCheckResult = checkAnswerUser(request, surveyJson, anPwd, isSubmit);
        } else {
            answerCheckResult.buildResult(DwAnswerCheckResult.CHECK500);
        }
        return answerCheckResult;
    }

    private SurveyJson getSurveyJsonResult(DwAnswerCheckResult answerCheckResult, SurveyJson surveyJson) {
        // 返回的时候处理一下anPwdCode
        if (surveyJson!=null) {
            String surveyJsonSimple = surveyJson.getSurveyJsonSimple();
            String surveyJsonText = surveyJson.getSurveyJsonText();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode surveyJsonSimpleNode = objectMapper.readTree(surveyJsonSimple);
                JsonNode surveyJsonTextNode = objectMapper.readTree(surveyJsonText);
                if (surveyJsonSimpleNode.has("surveyAttrs")) {
                    if (surveyJsonSimpleNode.get("surveyAttrs").has("anPwdAttr")) {
                        JsonNode anPwdAttr1 = surveyJsonSimpleNode.get("surveyAttrs").get("anPwdAttr");
                        ((ObjectNode) anPwdAttr1).put("anPwdCode", "");
                        JsonNode anPwdAttr2 = surveyJsonTextNode.get("surveyAttrs").get("anPwdAttr");
                        ((ObjectNode) anPwdAttr2).put("anPwdCode", "");
                    }
                }
                surveyJson.setSurveyJsonSimple(objectMapper.writeValueAsString(surveyJsonSimpleNode));
                surveyJson.setSurveyJsonText(objectMapper.writeValueAsString(surveyJsonTextNode));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            if (!answerCheckResult.isAnCheckIsPass() && answerCheckResult.getAnCheckResultCode()!=403 && answerCheckResult.getAnCheckResultCode()!=409) {
                SurveyJson tempSurveyJson = new SurveyJson();
                tempSurveyJson.setSurveyJsonText(surveyJson.getSurveyJsonSimple());
                tempSurveyJson.setSurveyId(surveyJson.getSurveyId());
                return tempSurveyJson;
            }
        } else {
            answerCheckResult.buildResult(DwAnswerCheckResult.CHECK406);
        }
        return surveyJson;
    }

    //当一个用户访问答卷时检查对答卷状态进行检查
    public DwAnswerCheckResult checkAnswerUser(HttpServletRequest request, SurveyJson surveyJson, String anPwd, boolean isSubmit){
        DwAnswerCheckResult answerCheckResult = new DwAnswerCheckResult();
        String sessionCode = getSessionCode(request, answerCheckResult);
        String tokenStr = JwtUtils.getRequestToken(request);
        // 对需要处理的属性进行处理
        String surveyId = surveyJson.getSurveyId();
        String sid = surveyJson.getSid();
        String surveyJsonSimple = surveyJson.getSurveyJsonSimple();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SurveyDirectory survey = surveyDirectoryManager.getSurvey(surveyId);

            if(survey==null || survey.getVisibility()!=1){
                answerCheckResult.buildResult(DwAnswerCheckResult.CHECK404);
                return answerCheckResult;
            }

            if (survey.getSurveyState() != 1 ) {
                answerCheckResult.buildResult(DwAnswerCheckResult.CHECK405);
                return answerCheckResult;
            }
            JsonNode surveyJsonNode = objectMapper.readTree(surveyJsonSimple);
            if (surveyJsonNode!=null) {
                if (surveyJsonNode.has("surveyAttrs")) {
                    JsonNode surveyAttrs = surveyJsonNode.get("surveyAttrs");
                    // 回答数量限制检查
                    if (surveyAttrs.has("anBroAttr")) {
                        JsonNode anBroAttr = surveyAttrs.get("anBroAttr");
                        boolean enabled = anBroAttr.get("enabled").asBoolean();
                        int anNum = anBroAttr.get("anNum").asInt();
                        if (enabled && anNum>0) {
                            // 使用cookie
                            int cookieAnNum = getAnNumByCookie(request, sid);
                            if (cookieAnNum>=anNum) {
                                answerCheckResult.buildResult(DwAnswerCheckResult.CHECK401);
                                return answerCheckResult;
                            }
                        }
                    }

                    // IP回答限制检查
                    String ipAddr = ipService.getIp(request);
                    long ipAnCount = dwAnswerEsClientService.getCount(surveyId,ipAddr);
                    if (surveyAttrs.has("anIpAttr")) {
                        JsonNode anIpAttr = surveyAttrs.get("anIpAttr");
                        boolean enabled = anIpAttr.get("enabled").asBoolean();
                        int anNum = anIpAttr.get("anNum").intValue();
                        if (enabled && ipAnCount>=anNum) {
                            answerCheckResult.buildResult(DwAnswerCheckResult.CHECK402);
                            return answerCheckResult;
                        }
                    }

                    //                anRefreshAttr.randomCode   // 重复回答启用验证码, 客户端判断
                    if (surveyAttrs.has("anRefreshAttr")) {
                        JsonNode anRefreshAttr = surveyAttrs.get("anRefreshAttr");
                        boolean randomCode = anRefreshAttr.get("randomCode").asBoolean();
                        if (randomCode && ipAnCount>3) {
                            // 大于3次启用验证码
                            answerCheckResult.setShowCaptcha(true);
                            if (isSubmit) {
                                answerCheckResult.buildResult(DwAnswerCheckResult.CHECK409);
                                return answerCheckResult;
                            }
                        }
                    }

//                 密码回答检查
                    if (surveyAttrs.has("anPwdAttr")) {
                        JsonNode anPwdAttr = surveyAttrs.get("anPwdAttr");
                        boolean enabled = anPwdAttr.get("enabled").asBoolean();
                        String anPwdCode = anPwdAttr.get("anPwdCode").asText();
                        logger.debug("anPwdCode {}", anPwdCode);
                        if (enabled && StringUtils.isNotEmpty(anPwdCode)) {
                            if (StringUtils.isEmpty(anPwd) || !anPwdCode.equals(anPwd)) {
                                answerCheckResult.buildResult(DwAnswerCheckResult.CHECK403);
                                return answerCheckResult;
                            }
                        }
                    }
//                 截止数量检查
                    if (surveyAttrs.has("anEndNumAttr")) {
                        JsonNode anEndNumAttr = surveyAttrs.get("anEndNumAttr");
                        boolean enabled = anEndNumAttr.get("enabled").asBoolean();
                        int endNum = anEndNumAttr.get("endNum").asInt();
                        long answerCount = dwAnswerEsClientService.getCount(surveyId, null);
                        if (enabled && answerCount>=endNum) {
                            answerCheckResult.buildResult(DwAnswerCheckResult.CHECK407);
                            return answerCheckResult;
                        }
                    }

                    //开始时间检查
                    if (surveyAttrs.has("anStartTimeAttr")) {
                        JsonNode anEndTimeAttr = surveyAttrs.get("anStartTimeAttr");
                        boolean enabled = anEndTimeAttr.get("enabled").asBoolean();
                        String anStartTimeStr = anEndTimeAttr.get("startTime").asText();
                        if (enabled && anStartTimeStr!=null && !"null".equals(anStartTimeStr)) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date anStartDate = simpleDateFormat.parse(anStartTimeStr);
                                long anEndDateTime = anStartDate.getTime();
                                Date curDate = new Date();
                                if (curDate.getTime()<=anEndDateTime) {
                                    answerCheckResult.buildResult(DwAnswerCheckResult.CHECK4081);
                                    return answerCheckResult;
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

//                 截止时间检查
                    if (surveyAttrs.has("anEndTimeAttr")) {
                        JsonNode anEndTimeAttr = surveyAttrs.get("anEndTimeAttr");
                        boolean enabled = anEndTimeAttr.get("enabled").asBoolean();
                        String anEndTimeStr = anEndTimeAttr.get("endTime").asText();
                        if (enabled && anEndTimeStr!=null && !"null".equals(anEndTimeStr)) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                Date anEndDate = simpleDateFormat.parse(anEndTimeStr);
                                long anEndDateTime = anEndDate.getTime();
                                Date curDate = new Date();
                                if (curDate.getTime()>anEndDateTime) {
                                    answerCheckResult.buildResult(DwAnswerCheckResult.CHECK408);
                                    return answerCheckResult;
                                }
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                }
            }
            // 通过
            answerCheckResult.buildResult(DwAnswerCheckResult.SUCCESS200);
//            return answerCheckResult;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return answerCheckResult;
    }

    public void calcAnQuQum(DwEsSurveyAnswer dwEsSurveyAnswer) {
        List<EsAnQuestion> anQuestions = dwEsSurveyAnswer.getAnQuestions();
//        for (EsAnQuestion esAnQuestion: anQuestions) {}
        int anNum = 0;
        if (anQuestions!=null) anNum = anQuestions.size();
        dwEsSurveyAnswer.getAnswerCommon().getAnState().setAnQuNum(anNum);
    }

    public void surveyBgEndTime(HttpServletRequest request, String sid, DwEsSurveyAnswer entity) {
        try{
            Date bgAnTime = null;
            String bgTimeAttrName = "bgTime"+ sid;
            Object sessionBgAnTime = request.getSession().getAttribute(bgTimeAttrName);
            if(sessionBgAnTime!=null){
                bgAnTime = (Date)sessionBgAnTime;
            }else{
                Cookie cookieBgTime = CookieUtils.getCookie(request, bgTimeAttrName);
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
                if (cookieBgTime!=null) bgAnTime = dateFormat.parse(cookieBgTime.getValue());
            }
            Date endAnTime = new Date();
            entity.getAnswerCommon().getAnTime().setBgAnDate(bgAnTime);
            entity.getAnswerCommon().getAnTime().setEndAnDate(endAnTime);
            if (bgAnTime!=null) entity.getAnswerCommon().getAnTime().setTotalTime(Float.parseFloat(String.valueOf(endAnTime.getTime() - bgAnTime.getTime())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void surveyBgTimeInit(HttpServletRequest request,HttpServletResponse response, String sid) {
        Date bgDate = new Date();
        String bgTimeKey = "bgTime"+ sid;
        request.getSession().setAttribute(bgTimeKey,bgDate);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        CookieUtils.addCookie(response, bgTimeKey, dateFormat.format(bgDate), 60*60*24, "/");
    }

    private  String getCookieName(String sid, String cookieType) {
        String cookieName = sid+"-NULL";
        if ("anNum".equals(cookieType)) cookieName = sid+"-ANSWER_NUM";
        cookieName = cookieName.toUpperCase();
        return cookieName;
    }
    private void addAnswerNumCookie(HttpServletRequest request, HttpServletResponse response, String sid) {
        // 记录答卷次数
        int cookieAnNum = getAnNumByCookie(request, sid);
        CookieUtils.addCookie(response, getCookieName(sid, "anNum"), String.valueOf(cookieAnNum + 1),24 * 60, "/");
    }

    private int getAnNumByCookie(HttpServletRequest request, String sid) {
        Cookie cookie = CookieUtils.getCookie(request, getCookieName(sid,"anNum"));
        int cookieAnNum = 0;
        if (cookie!=null) {
            String cookieValue = cookie.getValue();
            if (cookieValue != null && NumberUtils.isNumeric(cookieValue))  cookieAnNum = Integer.parseInt(cookieValue);
        }
        return cookieAnNum;
    }

    public String getSessionCode(HttpServletRequest request, DwAnswerCheckResult dwAnswerCheckResult) {
        String sessionCode = request.getHeader("sessionCode");
        if(sessionCode==null) sessionCode = request.getParameter("sessionCode");
        if(sessionCode==null) dwAnswerCheckResult.setSessionCode(request.getRequestedSessionId());
        return sessionCode;
    }
}
