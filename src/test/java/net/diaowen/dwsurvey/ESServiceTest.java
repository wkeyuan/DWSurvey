package net.diaowen.dwsurvey;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.alibaba.fastjson.JSON;
import net.diaowen.common.plugs.es.DwAnswerEsClientService;
import net.diaowen.common.plugs.es.ESService;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.config.ESClientConfig;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ESServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ESClientConfig.class);

    @Autowired
    ESService esService;
    @Autowired
    DwAnswerEsClientService dwAnswerEsClientService;

    @Test
    void addIndex() throws Exception {
        String indexName = "dw_test_index";
        Assertions.assertFalse(esService.indexExists(indexName));
        esService.addIndex(indexName);
        Assertions.assertTrue(esService.indexExists(indexName));
        esService.delIndex(indexName);
        Assertions.assertFalse(esService.indexExists(indexName));
    }

    @Test
    void createAnswerIndex() throws Exception {
//        Assertions.assertTrue(dwAnswerEsClientService.createAnswerIndex());
    }

   /* {
        "_index": "dwsurve_answer_2",
            "_id": "8VlGGokBjZ1B-l8yXvl-",
            "_version": 1,
            "result": "created",
            "_shards": {
        "total": 3,
                "successful": 1,
                "failed": 0
    },
        "_seq_no": 0,
            "_primary_term": 2
    }*/
    @Test
    void createAnswerDoc() throws Exception {
        String json = "{ \"surveyDwId\": \"abf679ce-14e4-11ee-aaea-f95923d36a5b\", \"anUser\": { \"userId\": \"aowwwewe3232aa\", \"userName\": \"xw\" }, \"anTime\": { \"bgAnDate\": \"2023-07-01 11:11:11\", \"endAnDate\": \"2023-06-29 11:11:11\", \"totalTime\": 832932 }, \"anIp\": { \"ip\": \"192.168.3.1\", \"city\": \"武汉\", \"addr\": \"湖北省武汉市东湖\" }, \"anState\": { \"anQuNum\": 0, \"isEff\": 1, \"handleState\": 0 }, \"anQuestions\": [ { \"quDwId\": \"b4b872f0-14e4-11ee-bc7e-b1be7ec10e15\", \"quType\": \"CHECKBOX\", \"anCheckboxs\": [ { \"optionDwId\": \"b4b872f2-14e4-11ee-bc7e-b1be7ec10e15\", \"otherText\": null } ] }, { \"quDwId\": \"abf679a1-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679a4-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679a7-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679a8-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679ac-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"UPLOADFILE\", \"anUploadFiles\": [ { \"filePath\": \"/webin/upload/ews8o5239/foqwqj6tz.jpg\", \"fileName\": \"100d0y000000m51xc569A.jpg\" } ] }, { \"quDwId\": \"abf679ad-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679ae-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679b0-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"FILLBLANK\", \"anFbk\": { \"answer\": \"调问是一款非常好的问卷产品\" } }, { \"quDwId\": \"abf679b1-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679b3-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679b5-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"SCORE\", \"anScores\": [ { \"optionDwId\": \"abf679b6-14e4-11ee-aaea-f95923d36a5b\", \"answerScore\": 3 }, { \"optionDwId\": \"abf679b7-14e4-11ee-aaea-f95923d36a5b\", \"answerScore\": 5 } ] }, { \"quDwId\": \"abf679b8-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679ba-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679bb-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"ORDERQU\", \"anOrders\": [ { \"optionDwId\": \"abf679bd-14e4-11ee-aaea-f95923d36a5b\", \"orderNum\": 1 }, { \"optionDwId\": \"abf679be-14e4-11ee-aaea-f95923d36a5b\", \"orderNum\": 1 }, { \"optionDwId\": \"abf679bc-14e4-11ee-aaea-f95923d36a5b\", \"orderNum\": 2 } ] }, { \"quDwId\": \"abf679bf-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"CHECKBOX\", \"anCheckboxs\": [ { \"optionDwId\": \"abf679c1-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } ] }, { \"quDwId\": \"abf679c3-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"MULTIFILLBLANK\", \"anMFbks\": [ { \"optionDwId\": \"abf679c4-14e4-11ee-aaea-f95923d36a5b\", \"answer\": \"喜欢这个\" }, { \"optionDwId\": \"abf679c5-14e4-11ee-aaea-f95923d36a5b\", \"answer\": \"综艺节目\" }, { \"optionDwId\": \"abf679c6-14e4-11ee-aaea-f95923d36a5b\", \"answer\": \"成绩斐然\" } ] }, { \"quDwId\": \"abf679c7-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"RADIO\", \"anRadio\": { \"optionDwId\": \"abf679c8-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } }, { \"quDwId\": \"abf679ca-14e4-11ee-aaea-f95923d36a5b\", \"quType\": \"CHECKBOX\", \"anCheckboxs\": [ { \"optionDwId\": \"abf679cb-14e4-11ee-aaea-f95923d36a5b\", \"otherText\": null } ] } ] }";
        IndexResponse indexResponse = dwAnswerEsClientService.createAnswerDoc(json);
        logger.info("createAnswerDoc indexResponse id {}", indexResponse.id());
        logger.info("createAnswerDoc indexResponse {}", indexResponse.toString());
    }

    @Test
    void findAnswers() throws Exception {
        String surveyDwId="abf679ce-14e4-11ee-aaea-f95923d36a5b";
        String beginDate = "2023-06-27 00:00:00";
        String endDate = "2023-06-30 00:00:00";
        String ip = "192.168.3.1";
        String city = "武汉";
        Integer handleState = 0;
        Integer isDelete = null;
        Page<DwEsSurveyAnswer> page = new Page<>();
        page = dwAnswerEsClientService.findPageByFromSize(page, surveyDwId, beginDate, endDate, ip, city, handleState, isDelete);
        logger.info("findAnswers page {}", JSON.toJSONString(page));
    }
}