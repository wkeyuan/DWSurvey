package net.diaowen.dwsurvey.common.design.bank;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.common.design.common.DesignCommonToolbarUtils;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignToolbarBankUtils {

    /**
     * 获取常用题列表
     * @return List<Question>
     */
    public static List<Question> commonQus () {
        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "邮箱题","<i class=\"fa-regular fa-envelope\"></i>");
        emailFbk.setCheckType(CheckType.EMAIL);

        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "性别题", "<i class=\"fa-solid fa-venus-mars\"></i>");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男",null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);

        Question eduRadio = new Question(QuType.RADIO, "请选择学历", "学历题", "<i class=\"fa-solid fa-user-graduate\"></i>");
        List<QuRadio> eduRadios = new ArrayList<>();
        eduRadios.add(new QuRadio("博士","博士"));
        eduRadios.add(new QuRadio("硕士", "硕士"));
        eduRadios.add(new QuRadio("本科", "本科"));
        eduRadios.add(new QuRadio("专科", "专科"));
        eduRadios.add(new QuRadio("其它", "其它"));
        eduRadio.setQuRadios(eduRadios);

        Question incomeRadio = new Question(QuType.RADIO, "请选择月收入", "月收入", "<i class=\"fa-solid fa-coins\"></i>");
        List<QuRadio> incomeRadios = new ArrayList<>();
        incomeRadios.add(new QuRadio("3000元以下","3000元以下"));
        incomeRadios.add(new QuRadio("3000-5000元", "3000-5000元"));
        incomeRadios.add(new QuRadio("5000-10000元", "5000-10000元"));
        incomeRadios.add(new QuRadio("10000-20000元", "10000-20000元"));
        incomeRadios.add(new QuRadio("20000元以上", "20000元以上"));
        incomeRadio.setQuRadios(incomeRadios);

        return questions12;
    }


    public static List<Question> userQus() {
        List<Question> questions12 = new ArrayList<Question>();
        //日期 时间 邮箱 性别 学历 收入
        Question nameFbk = new Question(QuType.FILLBLANK, "请输入姓名", "姓名", "<i class=\"fa-regular fa-user\"></i>");
        Question phoneFbk = new Question(QuType.FILLBLANK, "请输入手机号", "手机号", "<i class=\"fa-solid fa-mobile-screen\"></i>");
        Question emailFbk = new Question(QuType.FILLBLANK, "请输入邮箱", "邮箱", "<i class=\"fa-regular fa-envelope\"></i>");
        Question genderRadio = new Question(QuType.RADIO, "请选择性别", "性别", "<i class=\"fa-solid fa-venus-mars\"></i>");
        List<QuRadio> genderRadios = new ArrayList<>();
        genderRadios.add(new QuRadio("男", null));
        genderRadios.add(new QuRadio("女", null));
        genderRadio.setQuRadios(genderRadios);
        Question codeFbk = new Question(QuType.FILLBLANK, "请输入身份证号", "身份证", "<i class=\"fa-solid fa-id-card\"></i>");
        Question ageFbk = new Question(QuType.FILLBLANK, "请输入年龄", "年龄", "<i class=\"fa-regular fa-moon\"></i>");

        Question eduRadio = new Question(QuType.RADIO, "请选择学历", "学历", "<i class=\"fa-solid fa-user-graduate\"></i>");
        List<QuRadio> eduRadios = new ArrayList<>();
        eduRadios.add(new QuRadio("博士","博士"));
        eduRadios.add(new QuRadio("硕士", "硕士"));
        eduRadios.add(new QuRadio("本科", "本科"));
        eduRadios.add(new QuRadio("专科", "专科"));
        eduRadios.add(new QuRadio("其它", "其它"));
        eduRadio.setQuRadios(eduRadios);
        Question incomeRadio = new Question(QuType.RADIO, "请选择月收入", "月收入", "<i class=\"fa-solid fa-coins\"></i>");
        List<QuRadio> incomeRadios = new ArrayList<>();
        incomeRadios.add(new QuRadio("3000元以下","3000元以下"));
        incomeRadios.add(new QuRadio("3000-5000元", "3000-5000元"));
        incomeRadios.add(new QuRadio("5000-10000元", "5000-10000元"));
        incomeRadios.add(new QuRadio("10000-20000元", "10000-20000元"));
        incomeRadios.add(new QuRadio("20000元以上", "20000元以上"));
        incomeRadio.setQuRadios(incomeRadios);

        nameFbk.setDwsurveyfont("icon-dwsurvey-xingming");
        genderRadio.setDwsurveyfont("icon-dwsurvey-xingbie");
        emailFbk.setDwsurveyfont("icon-dwsurvey-Email");
        phoneFbk.setDwsurveyfont("icon-dwsurvey-shouji");
        ageFbk.setDwsurveyfont("icon-dwsurvey-nianling");
        codeFbk.setDwsurveyfont("icon-dwsurvey-shenfenzheng");
        eduRadio.setDwsurveyfont("icon-dwsurvey-xueli");
        incomeRadio.setDwsurveyfont("icon-dwsurvey-shouru");


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
        questions12.add(eduRadio);
        questions12.add(incomeRadio);

        return questions12;
    }

    /**
     * 常用题Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu commonTabQu () {
        return new DesignSurveyToolbarTabQu("常用题",commonQus());
    }

    /**
     * 常用题Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu userTabQu () {
        return new DesignSurveyToolbarTabQu("人员信息",userQus());
    }

    public static DesignSurveyToolbarTab toolbarTab2 () {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(userTabQu());
        tabQus1.add(commonTabQu());
        tabQus1.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus1.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("常用题库",tabQus1);
    }

}
