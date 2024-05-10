package net.diaowen.dwsurvey.entity.es.answer.question.option;

import java.util.List;

public class EsAnMatrixCheckbox {

    private String rowDwId;
    private List<EsAnCheckbox> rowAnCheckboxs;
    private float rowAnScore; // 得分

    public String getRowDwId() {
        return rowDwId;
    }

    public void setRowDwId(String rowDwId) {
        this.rowDwId = rowDwId;
    }

    public List<EsAnCheckbox> getRowAnCheckboxs() {
        return rowAnCheckboxs;
    }

    public void setRowAnCheckboxs(List<EsAnCheckbox> rowAnCheckboxs) {
        this.rowAnCheckboxs = rowAnCheckboxs;
    }

    public float getRowAnScore() {
        return rowAnScore;
    }

    public void setRowAnScore(float rowAnScore) {
        this.rowAnScore = rowAnScore;
    }
}
