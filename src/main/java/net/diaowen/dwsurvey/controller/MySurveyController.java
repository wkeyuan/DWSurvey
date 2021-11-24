package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.entity.User;
import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.mapper.ObjectMapper;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by keyuan on 17/7/31.
 */
@Controller
@RequestMapping("/design/my-survey")
public class MySurveyController {

    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private AccountManager accountManager;

    @RequestMapping("/list.do")
    public ModelAndView list(HttpServletRequest request,SurveyDirectory entity,Page<SurveyDirectory> page) throws Exception {
        String surveyState = request.getParameter("surveyState");
        if(surveyState==null||"".equals(surveyState)){
            entity.setSurveyState(null);
        }
        page=surveyDirectoryManager.findByUser(page,entity);
//        page = surveyDirectoryManager.findPage(page);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("surveyState",surveyState);
        modelAndView.setViewName("/diaowen-design/list");
        modelAndView.addObject("page",page);
        return modelAndView;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public String delete(String id) throws Exception {
        String result="false";
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                String userId=user.getId();
                SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id,userId);
                if(surveyDirectory!=null){
                    surveyDirectoryManager.delete(id);
                    result="true";
                }
            }
        }catch (Exception e) {
            result="false";
        }
        return result;
    }

    @RequestMapping("/surveyState.do")
    //问卷壮态设置
    @ResponseBody
    public String surveyState(String id,Integer surveyState) throws Exception{
        String result="";
        try{
            User user= accountManager.getCurUser();
            if(user!=null){
                String userId=user.getId();
                SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id, userId);
                if(surveyDirectory!=null){
                    surveyDirectory.setSurveyState(surveyState);
                    surveyDirectoryManager.save(surveyDirectory);
                }
            }
            result="true";
        }catch(Exception e){
            e.printStackTrace();
            result="error";
        }
        return result;
    }

    @RequestMapping("/attrs.do")
    @ResponseBody
    public SurveyDirectory attrs(String id) throws Exception {
        try{
            SurveyDirectory survey=surveyDirectoryManager.findUniqueBy(id);
//            JsonConfig cfg = new JsonConfig();
//            cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
//            JSONObject jsonObject=JSONObject.fromObject(survey,cfg);
//            return jsonObject.toString();
            return survey;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
