package net.diaowen.dwsurvey.entity.es.answer.question.option;

/**
 * 矩阵量表与滑块共用
 */
public class EsAnMatrixScale {

    private String rowDwId;
    private String answerScore;

    private Double rowAnScore; // 得分

    public String getRowDwId() {
        return rowDwId;
    }

    public void setRowDwId(String rowDwId) {
        this.rowDwId = rowDwId;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }

    public Double getRowAnScore() {
        return rowAnScore;
    }

    public void setRowAnScore(Double rowAnScore) {
        this.rowAnScore = rowAnScore;
    }
}
