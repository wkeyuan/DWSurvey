package net.diaowen.dwsurvey.common;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignSurveyBankUtils {

    /**
     * 获取题库集合
     * @return List<DesignSurveyToolbarTabQu>
     */
    public static List<DesignSurveyToolbarTabQu> quBankToolbarTabQus() {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();

        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question nameFbk = new Question(QuType.FILLBLANK, "请输入姓名", "姓名");
        Question phoneFbk = new Question(QuType.FILLBLANK, "请输入手机号", "手机号");
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "邮箱");
        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "性别");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男", null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);
        Question codeFbk = new Question(QuType.FILLBLANK, "请输入身份证号", "身份证");
        Question ageFbk = new Question(QuType.FILLBLANK, "请输入年龄", "年龄");


        phoneFbk.setCheckType(CheckType.PHONENUM);
        emailFbk.setCheckType(CheckType.EMAIL);
        codeFbk.setCheckType(CheckType.IDENTCODE);
        ageFbk.setCheckType(CheckType.DIGITS);

        questions12.add(nameFbk);
        questions12.add(phoneFbk);
        questions12.add(emailFbk);
        questions12.add(genderRadio);
        questions12.add(codeFbk);
        questions12.add(ageFbk);

        List<Question> questions13 = new ArrayList<Question>();

        Question radio1 = new Question(QuType.RADIO, "对xxx满意度", "满意度");
        List<QuRadio> radioOptions1 = new ArrayList<>();
        radioOptions1.add(new QuRadio("很满意",null));
        radioOptions1.add(new QuRadio("满意", null));
        radioOptions1.add(new QuRadio("一般", null));
        radioOptions1.add(new QuRadio("不满意", null));
        radioOptions1.add(new QuRadio("很不满意", null));
        radio1.setQuRadios(radioOptions1);

        Question radio2 = new Question(QuType.RADIO, "对xxx适合", "适合");
        List<QuRadio> radioOptions2 = new ArrayList<>();
        radioOptions2.add(new QuRadio("非常适合",null));
        radioOptions2.add(new QuRadio("较合适", null));
        radioOptions2.add(new QuRadio("一般", null));
        radioOptions2.add(new QuRadio("不适合", null));
        radioOptions2.add(new QuRadio("很不适合", null));
        radio2.setQuRadios(radioOptions2);

        Question radio3 = new Question(QuType.RADIO, "对xxx难易度", "难易度");
        List<QuRadio> radioOptions3 = new ArrayList<>();
        radioOptions3.add(new QuRadio("非常容易",null));
        radioOptions3.add(new QuRadio("较容易", null));
        radioOptions3.add(new QuRadio("一般", null));
        radioOptions3.add(new QuRadio("较难", null));
        radioOptions3.add(new QuRadio("很难", null));
        radio3.setQuRadios(radioOptions3);

        Question radio4 = new Question(QuType.RADIO, "对xxx满意度", "合理性");
        List<QuRadio> radioOptions4 = new ArrayList<>();
        radioOptions4.add(new QuRadio("非常合理",null));
        radioOptions4.add(new QuRadio("较合理", null));
        radioOptions4.add(new QuRadio("一般", null));
        radioOptions4.add(new QuRadio("不合理", null));
        radioOptions4.add(new QuRadio("很不合理", null));
        radio4.setQuRadios(radioOptions4);

        questions13.add(radio1);
        questions13.add(radio2);
        questions13.add(radio3);
        questions13.add(radio4);

        tabQus1.add(new DesignSurveyToolbarTabQu("满意度",questions13));
        tabQus1.add(new DesignSurveyToolbarTabQu("个人信息",questions12));
        return tabQus1;
    }
}
