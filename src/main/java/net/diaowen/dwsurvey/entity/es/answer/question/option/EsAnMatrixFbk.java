package net.diaowen.dwsurvey.entity.es.answer.question.option;

import java.util.List;

public class EsAnMatrixFbk {

    private String rowDwId;
    private List<EsAnFbk> rowAnFbks;

    public String getRowDwId() {
        return rowDwId;
    }

    public void setRowDwId(String rowDwId) {
        this.rowDwId = rowDwId;
    }

    public List<EsAnFbk> getRowAnFbks() {
        return rowAnFbks;
    }

    public void setRowAnFbks(List<EsAnFbk> rowAnFbks) {
        this.rowAnFbks = rowAnFbks;
    }
}
