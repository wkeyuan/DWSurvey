package net.diaowen.dwsurvey.entity.es.answer.question.option;

import java.util.List;

public class EsAnMatrixCheckbox {

    private String rowDwId;
    private List<EsAnCheckbox> rowAnCheckboxs;
    private Double rowAnScore; // 得分

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

    public Double getRowAnScore() {
        return rowAnScore;
    }

    public void setRowAnScore(Double rowAnScore) {
        this.rowAnScore = rowAnScore;
    }
}
