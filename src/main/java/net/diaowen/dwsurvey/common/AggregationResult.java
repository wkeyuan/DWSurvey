package net.diaowen.dwsurvey.common;

import java.util.HashMap;
import java.util.Map;

public class AggregationResult {

    private Map<String, AggregationResultItem> aggMap = new HashMap<>();
    private Long doc_count_error_upper_bound;
    private Long sum_other_doc_count;

    public Map<String, AggregationResultItem> getAggMap() {
        return aggMap;
    }

    public void setAggMap(Map<String, AggregationResultItem> aggMap) {
        this.aggMap = aggMap;
    }

    public Long getDoc_count_error_upper_bound() {
        return doc_count_error_upper_bound;
    }

    public void setDoc_count_error_upper_bound(Long doc_count_error_upper_bound) {
        this.doc_count_error_upper_bound = doc_count_error_upper_bound;
    }

    public Long getSum_other_doc_count() {
        return sum_other_doc_count;
    }

    public void setSum_other_doc_count(Long sum_other_doc_count) {
        this.sum_other_doc_count = sum_other_doc_count;
    }
}
