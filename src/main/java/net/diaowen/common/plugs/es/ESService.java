package net.diaowen.common.plugs.es;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 初始化并统一提供所有索引名称
 */
@Service
public class ESService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    // 移入到配置文件中
    private static String INDEX_PREV = "";
    private static String ANSWER_INDEX_NAME = "";
    private static String ANSWER_INDEX_NAME_AGG = "";
    private static String ANSWER_CONTACT_RELATE = "";
    private static String ANSWER_CONTACT = "";

    public void initIndexName () {
        if (StringUtils.isEmpty(ANSWER_INDEX_NAME)) setIndexName();
    }

    @Value("${elasticsearch.index-prev}")
    private void setIndexPrev(String indexPrev) {
        ESService.INDEX_PREV = indexPrev;
        setIndexName();
    }

    private void setIndexName() {
        ANSWER_INDEX_NAME = ESService.INDEX_PREV + "dwsurvey_answer_index";
        ANSWER_INDEX_NAME_AGG = ESService.INDEX_PREV + "dwsurvey_answer_index_aggs";
        ANSWER_CONTACT_RELATE = ESService.INDEX_PREV + "dwsurvey_contact_relate_index";
        ANSWER_CONTACT = ESService.INDEX_PREV + "dwsurvey_contacts_index";
        logger.info("index_name {}, {}, {}, {}", ANSWER_INDEX_NAME, ANSWER_INDEX_NAME_AGG, ANSWER_CONTACT_RELATE, ANSWER_CONTACT);
    }

    public static String getIndexPrev() {
        return INDEX_PREV;
    }

    public static String getAnswerIndexName() {
        return ANSWER_INDEX_NAME;
    }

    public static String getAnswerIndexNameAgg() {
        return ANSWER_INDEX_NAME_AGG;
    }

    public static String getAnswerContact() {
        return ANSWER_CONTACT;
    }

    public static String getAnswerContactRelate() {
        return ANSWER_CONTACT_RELATE;
    }

    // 保存答卷时，让ES自动生成ID。但我需要返回的ID值。
    // 把答卷IP等信息都记录在ES，原始数据页面直接展现ES的文档记录。
    // 用户提交答卷，新问卷直接保存，并自动生成ID。
    // 如何是用户修改问卷，会有EsID。
    // ES 数据结构
    // surveyAnswer = {id,ip,userId,answerQuestion:[{quId,quRadio:{}}]}
}