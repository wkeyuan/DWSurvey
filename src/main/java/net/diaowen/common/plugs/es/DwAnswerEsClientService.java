package net.diaowen.common.plugs.es;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.val;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.config.ESClientConfig;
import net.diaowen.dwsurvey.entity.es.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.EsAnTime;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DwAnswerEsClientService {
    private static Logger logger = LoggerFactory.getLogger(ESClientConfig.class);
    //答卷索引名称
    private final static String ANSWER_INDEX_NAME = "dwsurvey_answer_index";
    @Resource
    private ESClientService esClientService;

    //创建索引方法
    public boolean createAnswerIndex(){
        try {
            if (!esClientService.indexExists(ANSWER_INDEX_NAME)) {
                return esClientService.createIndex(ANSWER_INDEX_NAME,"conf/es/survey-answer-index.json");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //保存答卷
    public IndexResponse createAnswerDoc(String answerJson){
        try {
            DwEsSurveyAnswer dwEsSurveyAnswer = JSON.parseObject(answerJson, DwEsSurveyAnswer.class);
            EsAnTime esAnTime = dwEsSurveyAnswer.getAnTime();
            esAnTime.setBgAnDate(new Date());
            esAnTime.setEndAnDate(new Date());
            String newAnswerJson = JSON.toJSONString(dwEsSurveyAnswer);
            return esClientService.createDoc(ANSWER_INDEX_NAME, newAnswerJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<DwEsSurveyAnswer> findPageByFromSize(Page<DwEsSurveyAnswer> page, DwEsSurveyAnswer dwEsSurveyAnswer){
        //开始时间，结束时间，问卷状态，答卷地区
        if (dwEsSurveyAnswer!=null) {
            String surveyId = dwEsSurveyAnswer.getSurveyId();
            EsAnTime anTime = dwEsSurveyAnswer.getAnTime();
            EsAnIp anIp = dwEsSurveyAnswer.getAnIp();
            EsAnState anState = dwEsSurveyAnswer.getAnState();
            String beginDate = null;
            String endDate = null;
            String ip = null;
            String city = null;
            Integer handleState = 0;
            if (anTime!=null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date bgAnDate = anTime.getBgAnDate();
                Date endAnDate = anTime.getEndAnDate();
                if (bgAnDate!=null) beginDate = simpleDateFormat.format(bgAnDate);
                if (endAnDate!=null) endDate = simpleDateFormat.format(endAnDate);
            }
            if (anIp!=null) {
                ip = anIp.getIp();
                city = anIp.getCity();
            }
            if (anState!=null) handleState = anState.getHandleState();
            Integer isDelete = dwEsSurveyAnswer.getIsDelete();
            page = findPageByFromSize(page,surveyId,beginDate,endDate,ip,city,handleState,isDelete);
        }
        return page;
    }


    public Page<DwEsSurveyAnswer> findPageByFromSize(Page<DwEsSurveyAnswer> page, String surveyId, String beginDate, String endDate, String ip, String city, Integer handleState, Integer isDelete){
        if (StringUtils.isNotBlank(surveyId)) {
            try {
                List<Query> queryList = new ArrayList<>();
//            必须带的参数
                queryList.add(TermQuery.of(b -> b.field("surveyId").value(surveyId))._toQuery());
//            可选参数，答卷时间
                if (StringUtils.isNotBlank(beginDate)) queryList.add(Query.of(b -> b.range(c -> c.field("anTime.bgAnDate").from(beginDate))));
                if (StringUtils.isNotBlank(endDate)) queryList.add(Query.of(b -> b.range(c -> c.field("anTime.bgAnDate").to(endDate))));
                if (StringUtils.isNotBlank(ip)) queryList.add(Query.of(b -> b.term( c -> c.field("anIp.ip").value(ip))));
//                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.fuzzy(c -> c.field("anIp.city").value(city).fuzziness("1"))));
//                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.match(c -> c.field("anIp.city").field("anIp.addr").query(city))));
                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.match(c -> c.field("anIp.city").query(city))));
                if (handleState!=null) queryList.add(Query.of(b -> b.term( c -> c.field("anState.handleState").value(handleState))));
                if (isDelete!=null) queryList.add(Query.of(b -> b.term( c -> c.field("isDelete").value(isDelete))));
                Query query = Query.of(b -> b.bool(c -> c.must(queryList)));

//            Query timeQuery = Query.of(b -> b.range(c -> c.field("anTime.bgAnDate").from("2023-06-27 00:00:00").field("anTime.bgAnDate").to("2023-06-30 00:00:00")));
//            queryList.add(timeQuery);
//            Query query = Query.of(b -> b.bool(c -> c.must(termQuery).must(timeQuery)));

                int from = (page.getPageNo()-1) * page.getPageSize();
                int size = page.getPageSize();

                List<SortOptions> sortOptions = new ArrayList<>();
                sortOptions.add(SortOptions.of(b -> b.field(c -> c.field("anTime.bgAnDate").order(SortOrder.Desc))));

                SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findPageByFromSize(ANSWER_INDEX_NAME, query, sortOptions, from, size, DwEsSurveyAnswer.class);

//            查询结果
                List<Hit<DwEsSurveyAnswer>> hits = searchResponse.hits().hits();
                for (Hit<DwEsSurveyAnswer> hit: hits) {
                    DwEsSurveyAnswer esSurveyAnswer = hit.source();
                    assert esSurveyAnswer != null;
                    esSurveyAnswer.setEsId(hit.id());
                    page.addResult(esSurveyAnswer);
                }

//            记录总数
                TotalHits totalHits = searchResponse.hits().total();
                assert totalHits != null;
                long totalItems = totalHits.value();
                page.setTotalItems(totalItems);

                logger.info("Indexed with version {}", JSON.toJSONString(searchResponse));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return page;
    }

    public DwEsSurveyAnswer findById(String answerId){
//        SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findPageByFromSize(ANSWER_INDEX_NAME, query, sortOptions, from, size, DwEsSurveyAnswer.class);
        DwEsSurveyAnswer dwEsSurveyAnswer = null;
        try {
            GetResponse<DwEsSurveyAnswer> response = esClientService.getDocById(ANSWER_INDEX_NAME, answerId, DwEsSurveyAnswer.class);
            if (response.found()) {
                dwEsSurveyAnswer = response.source();
            } else {
                logger.info ("DwEsSurveyAnswer not found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dwEsSurveyAnswer;
    }

    public Page<DwEsSurveyAnswer> findPageByScroll(){
//        SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findByQuery(ANSWER_INDEX_NAME, query, from, size, DwEsSurveyAnswer.class);
        return null;
    }

}
