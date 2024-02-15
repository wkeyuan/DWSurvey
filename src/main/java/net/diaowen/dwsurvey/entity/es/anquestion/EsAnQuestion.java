package net.diaowen.dwsurvey.entity.es.anquestion;

import net.diaowen.dwsurvey.entity.es.anquestion.option.*;

import java.io.Serializable;
import java.util.List;

public class EsAnQuestion {

    //题目的dwId
    private String quDwId;
    //题型，使用QuType类名称来标识(全大写)
    private String quType;
    //题目答卷JSON，注意针对不同题型答卷JSON结构可能不一样。
    //单选题
    private EsAnRadio anRadio;
    //多选题
    private List<EsAnCheckbox> anCheckboxs;
    //填空题
    private EsAnFbk anFbk;
    //多项填空题
    private List<EsAnFbk> anMFbks;
    //上传题
    private List<EsAnUploadFile> anUploadFiles;
    //评分题
    private List<EsAnScore> anScores;
    //排序题
    private List<EsAnOrder> anOrders;

    public String getQuDwId() {
        return quDwId;
    }

    public void setQuDwId(String quDwId) {
        this.quDwId = quDwId;
    }

    public String getQuType() {
        return quType;
    }

    public void setQuType(String quType) {
        this.quType = quType;
    }

    public EsAnRadio getAnRadio() {
        return anRadio;
    }

    public void setAnRadio(EsAnRadio anRadio) {
        this.anRadio = anRadio;
    }

    public List<EsAnCheckbox> getAnCheckboxs() {
        return anCheckboxs;
    }

    public void setAnCheckboxs(List<EsAnCheckbox> anCheckboxs) {
        this.anCheckboxs = anCheckboxs;
    }

    public EsAnFbk getAnFbk() {
        return anFbk;
    }

    public void setAnFbk(EsAnFbk anFbk) {
        this.anFbk = anFbk;
    }

    public List<EsAnFbk> getAnMFbks() {
        return anMFbks;
    }

    public void setAnMFbks(List<EsAnFbk> anMFbks) {
        this.anMFbks = anMFbks;
    }

    public List<EsAnUploadFile> getAnUploadFiles() {
        return anUploadFiles;
    }

    public void setAnUploadFiles(List<EsAnUploadFile> anUploadFiles) {
        this.anUploadFiles = anUploadFiles;
    }

    public List<EsAnScore> getAnScores() {
        return anScores;
    }

    public void setAnScores(List<EsAnScore> anScores) {
        this.anScores = anScores;
    }

    public List<EsAnOrder> getAnOrders() {
        return anOrders;
    }

    public void setAnOrders(List<EsAnOrder> anOrders) {
        this.anOrders = anOrders;
    }
}
