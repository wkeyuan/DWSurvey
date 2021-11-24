package net.diaowen.dwsurvey.controller;

import net.diaowen.common.plugs.zxing.ZxingUtil;
import net.diaowen.common.utils.DiaowenProperty;
import net.diaowen.common.utils.parsehtml.HtmlUtil;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.SurveyReqUrl;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyAnswerManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import net.diaowen.dwsurvey.service.SurveyReqUrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * 问卷 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/survey")
public class SurveyController{

    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private QuestionManager questionManager;
    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyReqUrlManager surveyReqUrlManager;

    //问卷的动态访问方式
    @RequestMapping("/preview.do")
    public ModelAndView preview(HttpServletRequest request,String pid) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        SurveyReqUrl surveyReqUrl = surveyReqUrlManager.get(pid);
        if(surveyReqUrl!=null && surveyReqUrl.getReqUrlType()==3){
            String surveyId = surveyReqUrl.getSurveyId();
            modelAndView = buildSurvey(surveyId);
        }
        modelAndView.setViewName("/diaowen-design/survey_preview");
        return modelAndView;
    }

    //问卷的动态访问方式
    @RequestMapping("/answerSurvey.do")
    public ModelAndView answerSurvey(HttpServletRequest request,String surveyId) throws Exception {
        ModelAndView modelAndView = buildSurvey(surveyId);
        modelAndView.setViewName("/diaowen-design/answer-survey");
        request.getSession().setAttribute("bgTime"+surveyId,new Date());
        return modelAndView;
    }

    //问卷动态访问-移动端
    @RequestMapping("/answerSurveryMobile.do")
    public ModelAndView answerSurveryMobile(HttpServletRequest request,String surveyId) throws Exception {
        ModelAndView modelAndView = buildSurvey(surveyId);
        modelAndView.setViewName("/diaowen-design/answer-survey-mobile");
        request.getSession().setAttribute("bgTime"+surveyId,new Date());
        return modelAndView;
    }

    private ModelAndView buildSurvey(String surveyId) {
        ModelAndView modelAndView = new ModelAndView();
        SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
        survey.setQuestions(questionManager.findDetails(surveyId, "2"));
        modelAndView.addObject("survey", survey);
        modelAndView.addObject("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
        modelAndView.addObject("surveyNameText", HtmlUtil.removeTagFromText(survey.getSurveyName()));
        return modelAndView;
    }

    //回答问卷的二维码
    @RequestMapping("/answerTD.do")
    public String answerTD(HttpServletRequest request,HttpServletResponse response,String surveyId) throws Exception{
        String down=request.getParameter("down");
        String baseUrl = "";
        baseUrl = request.getScheme() +"://" + request.getServerName()
                + (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                + request.getContextPath();
        String encoderContent=baseUrl+"/response/answerMobile.do?surveyId="+surveyId;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        BufferedImage twoDimensionImg = ZxingUtil.qRCodeCommon(encoderContent, "jpg", 7);
                ImageIO.write(twoDimensionImg, "jpg", jpegOutputStream);
        if(down==null){
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(jpegOutputStream.toByteArray());
            responseOutputStream.flush();
            responseOutputStream.close();
        }else{
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(("diaowen_"+surveyId+".jpg").getBytes()));
            byte[] bys = jpegOutputStream.toByteArray();
            response.addHeader("Content-Length", "" + bys.length);
            ServletOutputStream responseOutputStream = response.getOutputStream();
            response.setContentType("application/octet-stream");
            responseOutputStream.write(bys);
            responseOutputStream.flush();
            responseOutputStream.close();
        }
        return null;
    }

}