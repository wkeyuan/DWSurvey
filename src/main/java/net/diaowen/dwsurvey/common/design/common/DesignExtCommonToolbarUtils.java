package net.diaowen.dwsurvey.common.design.common;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.common.QuTemplateBankCommon;
import net.diaowen.dwsurvey.common.design.common.DesignCommonToolbarUtils;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTab;
import net.diaowen.dwsurvey.common.design.entity.DesignSurveyToolbarTabQu;
import net.diaowen.dwsurvey.entity.QuRadio;
import net.diaowen.dwsurvey.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class DesignExtCommonToolbarUtils {




    public static List<Question> extQus() {
        List<Question> questions12 = new ArrayList<Question>();
        // 签名题
        questions12.add(QuTemplateBankCommon.getSignatureQuestion());
        // 图片
        questions12.add(QuTemplateBankCommon.getImgRadio());
        questions12.add(QuTemplateBankCommon.getImgCheckbox());
        // 定位
        questions12.add(QuTemplateBankCommon.getGeolocation());
        // 热力
        questions12.add(QuTemplateBankCommon.getImageHot());
        // 图片上传
        questions12.add(QuTemplateBankCommon.getUploadImage());
        // 颜色选择
        questions12.add(QuTemplateBankCommon.getColorPicker());
        questions12.add(QuTemplateBankCommon.getMatrixAutoAdd());
        return questions12;
    }

    /**
     * 常用题Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu extTabQu () {
        return new DesignSurveyToolbarTabQu("扩展题型",extQus());
    }


    /**
     * 扩展列表
     * @return List<Question>
     */
    public static List<Question> componentQus () {
        List<Question> questions12 = new ArrayList<Question>();
        questions12.add(QuTemplateBankCommon.getDivider());
        questions12.add(QuTemplateBankCommon.getCarousel());
        questions12.add(QuTemplateBankCommon.getMap());
        return questions12;
    }
    /**
     * 常用题Tab集
     * @return DesignSurveyToolbarTabQu
     */
    public static DesignSurveyToolbarTabQu componentTabQu () {
        return new DesignSurveyToolbarTabQu("扩展组件",componentQus());
    }


    public static DesignSurveyToolbarTab extToolbarTab3 () {
        List<DesignSurveyToolbarTabQu> tabQus1 = new ArrayList<>();
        tabQus1.add(extTabQu());
        tabQus1.add(componentTabQu());
        tabQus1.add(DesignCommonToolbarUtils.ppTabQu());
        tabQus1.add(DesignCommonToolbarUtils.clickTabQu());
        return new DesignSurveyToolbarTab("扩展题型",tabQus1);
    }

}
