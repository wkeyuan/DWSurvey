package net.diaowen.dwsurvey.entity.es.answer.question;

import net.diaowen.dwsurvey.entity.es.answer.question.option.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsAnQuestion {

    //题目的dwId
    private String quDwId;
    //题型，使用QuType类名称来标识(全大写)
    private String quType;
    //题目答卷JSON，注意针对不同题型答卷JSON结构可能不一样。
    private float quAnScore; // 得分
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

    private List<EsAnMatrixRadio> anMatrixRadios;
    private List<EsAnMatrixCheckbox> anMatrixCheckboxes;
    private List<EsAnMatrixFbk> anMatrixFbks;
    //量表与滑块共用
    private List<EsAnMatrixScale> anMatrixScales;

    private EsAnLocation anLocation;

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

    public List<EsAnMatrixRadio> getAnMatrixRadios() {
        return anMatrixRadios;
    }

    public void setAnMatrixRadios(List<EsAnMatrixRadio> anMatrixRadios) {
        this.anMatrixRadios = anMatrixRadios;
    }

    public List<EsAnMatrixCheckbox> getAnMatrixCheckboxes() {
        return anMatrixCheckboxes;
    }

    public void setAnMatrixCheckboxes(List<EsAnMatrixCheckbox> anMatrixCheckboxes) {
        this.anMatrixCheckboxes = anMatrixCheckboxes;
    }

    public List<EsAnMatrixFbk> getAnMatrixFbks() {
        return anMatrixFbks;
    }

    public void setAnMatrixFbks(List<EsAnMatrixFbk> anMatrixFbks) {
        this.anMatrixFbks = anMatrixFbks;
    }

    public List<EsAnMatrixScale> getAnMatrixScales() {
        return anMatrixScales;
    }

    public void setAnMatrixScales(List<EsAnMatrixScale> anMatrixScales) {
        this.anMatrixScales = anMatrixScales;
    }

    public float getQuAnScore() {
        return quAnScore;
    }

    public void setQuAnScore(float quAnScore) {
        this.quAnScore = quAnScore;
    }

    public EsAnLocation getAnLocation() {
        return anLocation;
    }

    public void setAnLocation(EsAnLocation anLocation) {
        this.anLocation = anLocation;
    }

    /** 以下为未持久化字段 **/
    private Map<String, Object> anOtherMaps;

    public Map<String, Object> getAnOtherMaps() {
        return anOtherMaps;
    }

    public void setAnOtherMaps(Map<String, Object> anOtherMaps) {
        this.anOtherMaps = anOtherMaps;
    }

}
