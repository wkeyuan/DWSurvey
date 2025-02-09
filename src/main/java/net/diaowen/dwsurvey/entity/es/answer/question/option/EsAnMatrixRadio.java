package net.diaowen.dwsurvey.entity.es.answer.question.option;

import java.util.List;

public class EsAnMatrixRadio {

    private String rowDwId;
    private String colDwId;

    private Float rowAnScore; // 得分

    public String getRowDwId() {
        return rowDwId;
    }

    public void setRowDwId(String rowDwId) {
        this.rowDwId = rowDwId;
    }

    public String getColDwId() {
        return colDwId;
    }

    public void setColDwId(String colDwId) {
        this.colDwId = colDwId;
    }

    public Float getRowAnScore() {
        return rowAnScore;
    }

    public void setRowAnScore(Float rowAnScore) {
        this.rowAnScore = rowAnScore;
    }
}
