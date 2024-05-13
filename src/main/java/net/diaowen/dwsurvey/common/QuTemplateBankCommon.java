package net.diaowen.dwsurvey.common;

import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.entity.*;

import java.util.ArrayList;
import java.util.List;

public class QuTemplateBankCommon {

    public static Question getUploadQuestion() {
        return new Question(QuType.UPLOADFILE, "上传题标题", null);
    }

    public static Question getTextareaQuestion() {
        Question textareaQuestion = new Question(QuType.FILLBLANK, "多行填空题标题", null);
        textareaQuestion.setAnswerInputRow(3);
        return textareaQuestion;
    }

    public static Question getSelectQuestion() {
        Question selectQuestion = new Question(QuType.RADIO, "下拉题标题", null);
        selectQuestion.setHv(4);
        List<QuRadio> selectQuRadios = new ArrayList<>();
        selectQuRadios.add(new QuRadio("选项1","选项1"));
        selectQuRadios.add(new QuRadio("选项2", "选项2"));
        selectQuestion.setQuRadios(selectQuRadios);
        return selectQuestion;
    }

    public static Question getOrderQuestion() {
        Question orderQuestion = new Question(QuType.ORDERQU, "排序题标题", null);
        List<QuOrderby> quOrderbys = new ArrayList<>();
        quOrderbys.add(new QuOrderby("选项1","选项1"));
        quOrderbys.add(new QuOrderby("选项2", "选项2"));
        orderQuestion.setQuOrderbys(quOrderbys);
        return orderQuestion;
    }

    public static Question getScoreQuestion() {
        Question scoreQuestion = new Question(QuType.SCORE, "评分题标题", null);
        scoreQuestion.setParamInt02(5);
        List<QuScore> quScores = new ArrayList<>();
        quScores.add(new QuScore("选项1","选项1"));
        quScores.add(new QuScore("选项2", "选项2"));
        scoreQuestion.setQuScores(quScores);
        return scoreQuestion;
    }

    public static Question getmFbkQuestion() {
        Question mFbkQuestion = new Question(QuType.MULTIFILLBLANK, "多项填空题标题", null);
        List<QuMultiFillblank> quMultiFillblanks = new ArrayList<>();
        quMultiFillblanks.add(new QuMultiFillblank("选项1","选项1"));
        quMultiFillblanks.add(new QuMultiFillblank("选项2", "选项2"));
        mFbkQuestion.setQuMultiFillblanks(quMultiFillblanks);
        return mFbkQuestion;
    }

    public static Question getInputQuestion() {
        return new Question(QuType.FILLBLANK, "填空题标题", null);
    }

    public static Question getCheckboxQuestion() {
        Question checkboxQuestion = new Question(QuType.CHECKBOX, "多选题标题", null);
        List<QuCheckbox> quCheckboxs = new ArrayList<>();
        quCheckboxs.add(new QuCheckbox("选项1","选项1"));
        quCheckboxs.add(new QuCheckbox("选项2", "选项2"));
        checkboxQuestion.setQuCheckboxs(quCheckboxs);
        return checkboxQuestion;
    }

    public static Question getRadioQuestion() {
        Question radioQuestion = new Question(QuType.RADIO, "单选题标题", null);
        List<QuRadio> quRadios = new ArrayList<>();
        quRadios.add(new QuRadio("选项1","选项1"));
        quRadios.add(new QuRadio("选项2", "选项2"));
        radioQuestion.setQuRadios(quRadios);
        return radioQuestion;
    }

    // 获取矩阵题型

    // 获取标准问卷组件集合

}
