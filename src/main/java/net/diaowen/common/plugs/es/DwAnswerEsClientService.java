package net.diaowen.common.plugs.es;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.diaowen.common.QuType;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.common.AggregationResult;
import net.diaowen.dwsurvey.common.AggregationResultItem;
import net.diaowen.dwsurvey.config.ESClientConfig;
import net.diaowen.dwsurvey.entity.AnScore;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswer;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswerAnOption;
import net.diaowen.dwsurvey.entity.es.answer.DwEsSurveyAnswerCommon;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnIp;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnState;
import net.diaowen.dwsurvey.entity.es.answer.extend.EsAnTime;
import net.diaowen.dwsurvey.entity.es.answer.question.EsAnQuestion;
import net.diaowen.dwsurvey.entity.es.answer.question.option.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DwAnswerEsClientService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //答卷索引名称
    private final static String ANSWER_INDEX_NAME = ESService.INDEX_PREV + "dwsurvey_answer_index";
    private final static String ANSWER_INDEX_NAME_AGG = ESService.INDEX_PREV + "dwsurvey_answer_index_aggs";
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

    public IndexResponse createAnswerDoc(String answerJson) {
        DwEsSurveyAnswer dwEsSurveyAnswer = JSON.parseObject(answerJson, DwEsSurveyAnswer.class);
        return createAnswerDocByObj(dwEsSurveyAnswer);
    }
    //保存答卷
    public IndexResponse createAnswerDocByObj(DwEsSurveyAnswer dwEsSurveyAnswer) {
        try {
            EsAnTime esAnTime = dwEsSurveyAnswer.getAnswerCommon().getAnTime();
//            List<EsAnQuestion> anQuestions = dwEsSurveyAnswer.getAnQuestions();
            String newAnswerJson = JSON.toJSONString(dwEsSurveyAnswer);
            logger.info("save answer source {}", newAnswerJson);
            IndexResponse indexResponse = esClientService.createDoc(ANSWER_INDEX_NAME, newAnswerJson);
            // 保存分析使用的副本-最终放在异步定时任务中
            List<DwEsSurveyAnswerAnOption> dwEsSurveyAnswerAnOptionList = dwEsSurveyAnswerAnOptions(dwEsSurveyAnswer, indexResponse.id());
            for (DwEsSurveyAnswerAnOption esSurveyAnswerAnOption: dwEsSurveyAnswerAnOptionList) {
                String esSurveyAnswerAnOptionJson = JSON.toJSONString(esSurveyAnswerAnOption);
                logger.info("save answer source esSurveyAnswerAnOptionJson {}", esSurveyAnswerAnOptionJson);
                IndexResponse indexResponse2 = esClientService.createDoc(ANSWER_INDEX_NAME_AGG, esSurveyAnswerAnOptionJson);
            }
            return indexResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<DwEsSurveyAnswer> findPageByFromSize(Page<DwEsSurveyAnswer> page, DwEsSurveyAnswer dwEsSurveyAnswer){
        //开始时间，结束时间，问卷状态，答卷地区
        if (dwEsSurveyAnswer!=null) {
            String surveyId = dwEsSurveyAnswer.getAnswerCommon().getSurveyId();
            EsAnTime anTime = dwEsSurveyAnswer.getAnswerCommon().getAnTime();
            EsAnIp anIp = dwEsSurveyAnswer.getAnswerCommon().getAnIp();
            EsAnState anState = dwEsSurveyAnswer.getAnswerCommon().getAnState();
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
            Integer isDelete = dwEsSurveyAnswer.getAnswerCommon().getIsDelete();
            page = findPageByFromSize(page,surveyId,beginDate,endDate,ip,city,handleState,isDelete);
        }
        return page;
    }


    public Page<DwEsSurveyAnswer> findPageByFromSize(Page<DwEsSurveyAnswer> page, String surveyId, String beginDate, String endDate, String ip, String city, Integer handleState, Integer isDelete){
        if (StringUtils.isNotBlank(surveyId)) {
            try {
                List<Query> queryList = new ArrayList<>();
//            必须带的参数
                queryList.add(TermQuery.of(b -> b.field("answerCommon.surveyId").value(surveyId))._toQuery());
//            可选参数，答卷时间
                if (StringUtils.isNotBlank(beginDate)) queryList.add(Query.of(b -> b.range(c -> c.field("answerCommon.anTime.bgAnDate").from(beginDate))));
                if (StringUtils.isNotBlank(endDate)) queryList.add(Query.of(b -> b.range(c -> c.field("answerCommon.anTime.bgAnDate").to(endDate))));
                if (StringUtils.isNotBlank(ip)) queryList.add(Query.of(b -> b.term( c -> c.field("answerCommon.anIp.ip").value(ip))));
//                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.fuzzy(c -> c.field("anIp.city").value(city).fuzziness("1"))));
//                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.match(c -> c.field("anIp.city").field("anIp.addr").query(city))));
                if (StringUtils.isNotBlank(city)) queryList.add(Query.of(b -> b.match(c -> c.field("answerCommon.anIp.city").query(city))));
                if (handleState!=null) queryList.add(Query.of(b -> b.term( c -> c.field("answerCommon.anState.handleState").value(handleState))));
                if (isDelete!=null) queryList.add(Query.of(b -> b.term( c -> c.field("answerCommon.isDelete").value(isDelete))));
                Query query = Query.of(b -> b.bool(c -> c.must(queryList)));

//            Query timeQuery = Query.of(b -> b.range(c -> c.field("anTime.bgAnDate").from("2023-06-27 00:00:00").field("anTime.bgAnDate").to("2023-06-30 00:00:00")));
//            queryList.add(timeQuery);
//            Query query = Query.of(b -> b.bool(c -> c.must(termQuery).must(timeQuery)));

                int from = (page.getPageNo()-1) * page.getPageSize();
                int size = page.getPageSize();

                List<SortOptions> sortOptions = new ArrayList<>();
                sortOptions.add(SortOptions.of(b -> b.field(c -> c.field("answerCommon.anTime.bgAnDate").order(SortOrder.Desc))));

                SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findPageByFromSize(ANSWER_INDEX_NAME, query, sortOptions, from, size, DwEsSurveyAnswer.class);

//            查询结果
                searchResponseData2Page(page, searchResponse);

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
                assert dwEsSurveyAnswer != null;
                dwEsSurveyAnswer.setEsId(answerId);
            } else {
                logger.info ("DwEsSurveyAnswer not found");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dwEsSurveyAnswer;
    }

    public Page<DwEsSurveyAnswer> findPageByScroll(Page<DwEsSurveyAnswer> page, String surveyId) {
        try {
            List<Query> queryList = new ArrayList<>();
//            必须带的参数
            queryList.add(TermQuery.of(b -> b.field("answerCommon.surveyId").value(surveyId))._toQuery());
            Query query = Query.of(b -> b.bool(c -> c.must(queryList)));
            SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findPageByScroll(ANSWER_INDEX_NAME, query, page.getPageSize(), DwEsSurveyAnswer.class);
            searchResponseData2Page(page, searchResponse);
            page.setScrollId(searchResponse.scrollId());
            return page;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void searchResponseData2Page(Page<DwEsSurveyAnswer> page, SearchResponse<DwEsSurveyAnswer> searchResponse) {
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
    }

    public Page<DwEsSurveyAnswer> findPageByScrollId(Page<DwEsSurveyAnswer> page) {
        page.setResult(new ArrayList<>());
        String scrollId = page.getScrollId();
        if (scrollId==null) {
            try {
                SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findPageByScrollId(scrollId, DwEsSurveyAnswer.class);
                searchResponseData2Page(page, searchResponse);
                page.setScrollId(searchResponse.scrollId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return page;
    }

    public Page<DwEsSurveyAnswer> findPageBySearchAfter(){
//        SearchResponse<DwEsSurveyAnswer> searchResponse = esClientService.findByQuery(ANSWER_INDEX_NAME, query, from, size, DwEsSurveyAnswer.class);

        return null;
    }

    public long getCount(String surveyId, String ipAddr) {
        try {
            if (StringUtils.isNotEmpty(surveyId)) {
                List<Query> queryList = new ArrayList<>();
//            必须带的参数
                queryList.add(TermQuery.of(b -> b.field("answerCommon.surveyId").value(surveyId))._toQuery());
                if (StringUtils.isNotEmpty(ipAddr)) queryList.add(Query.of(b -> b.term( c -> c.field("answerCommon.anIp.ip").value(ipAddr))));
                Query query = Query.of(b -> b.bool(c -> c.must(queryList)));
                long count = esClientService.getCount(ANSWER_INDEX_NAME,query);
//                logger.info("getCount surveyId {} {} {}", surveyId, ipAddr, count);
                return count;
            }
        } catch (IOException e) {
            logger.error("es getCountByIp 异常 {}，程序将继续执行，但IP过滤次数功能可能无效", ipAddr);
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Map<String, AggregationResultItem>> aggregationSearch(String surveyId) {
        Map<String, Map<String, AggregationResultItem>> aggMaps = new HashMap<>();
//      分题型一个个查询选项分组数据，然后把数据合并到一个map返回前端使用
//      必须带的参数
        Query mq = TermQuery.of(b -> b.field("answerCommon.surveyId").value(surveyId))._toQuery();
        Map<String, Aggregation> aggKeyMaps = new HashMap<>();
//      "count_level", a -> a.terms(t -> t.field(aggFieldName)
//      计算选项个数，设置聚合数大小
        aggKeyMaps.put("countRadio", Aggregation.of(a -> a.terms(t -> t.field("anQuestions.anRadio.optionDwId").size(2000))));
        aggKeyMaps.put("countCheckbox", Aggregation.of(a -> a.terms(t -> t.field("anQuestions.anCheckboxs.optionDwId").size(2000))));
        aggKeyMaps.put("countQu", Aggregation.of(a -> a.terms(t -> t.field("anQuestions.quDwId").size(2000))));
        Map<String, Aggregate> anAgg = esClientService.aggregationSearch(ANSWER_INDEX_NAME, mq, aggKeyMaps);
        for (String key:anAgg.keySet()) {
            logger.debug("key {}", key);
            Aggregate aggregate = anAgg.get(key);
            StringTermsAggregate stringTermsAggregate = aggregate.sterms();
            Map<String, AggregationResultItem> anAggMap = new HashMap<>();
            List<StringTermsBucket> termsBucketList = stringTermsAggregate.buckets().array();
            for (StringTermsBucket stringTermsBucket : termsBucketList) {
                logger.debug("key {}, value {}", stringTermsBucket.key(), stringTermsBucket.docCount());
                anAggMap.put(stringTermsBucket.key(), new AggregationResultItem(stringTermsBucket.docCount()));
            }
            AggregationResult aggregationResult = new AggregationResult();
            aggregationResult.setAggMap(anAggMap);
            aggregationResult.setSum_other_doc_count(stringTermsAggregate.sumOtherDocCount());
            aggregationResult.setDoc_count_error_upper_bound(stringTermsAggregate.docCountErrorUpperBound());
            aggMaps.put(key,anAggMap);
        }

        aggKeyMaps = new HashMap<>();
        // 包含 QuScore,QuOrder,QuMatrixSlider,QuMatrixScale
        aggKeyMaps.put("optionStats", Aggregation.of(a -> a.terms(t -> t.field("answerOptionDwId").size(2000)).aggregations("optionStatsIn", s -> s.stats(v -> v.field("answerNum")))));
        // matrix_radio
        aggKeyMaps.put("count_matrix_radio", Aggregation.of(a -> a.terms(t -> t.field("esAnMatrixRadio.rowDwId").size(2000)).aggregations("col_count", s -> s.terms(t -> t.field("esAnMatrixRadio.colDwId").size(2000)))));
        // matrix_checkbox
        aggKeyMaps.put("count_matrix_checkbox", Aggregation.of(a -> a.terms(t -> t.field("esAnMatrixCheckbox.rowDwId").size(2000)).aggregations("col_count",s -> s.terms(t -> t.field("esAnMatrixCheckbox.rowAnCheckboxs.optionDwId").size(2000)))));

        Map<String, Aggregate> anAggNum = esClientService.aggregationSearch(ANSWER_INDEX_NAME_AGG, mq, aggKeyMaps);
        logger.debug("anAggNum {}", anAggNum.size());
        Aggregate aggregateTemp = anAggNum.get("optionStats");
        // 检查聚合结果是否存在
        for (String key:anAggNum.keySet()) {
            logger.debug("key {}", key);
            Aggregate aggregate = anAggNum.get(key);
            logger.debug("key aggregate {}", JSON.toJSONString(aggregate));
            StringTermsAggregate stringTermsAggregate = aggregate.sterms();
            Map<String, AggregationResultItem> anAggMap = new HashMap<>();
            List<StringTermsBucket> termsBucketList = stringTermsAggregate.buckets().array();
            logger.debug("termsBucketList size {}", termsBucketList.size());
            for (StringTermsBucket rowTermsBucket : termsBucketList) {
                logger.debug("key {}, value {}", rowTermsBucket.key(), rowTermsBucket.docCount());
                AggregationResultItem aggregationResultItem = new AggregationResultItem(rowTermsBucket.docCount());
                Aggregate  optionStatsIn = rowTermsBucket.aggregations().get("optionStatsIn");
                if (optionStatsIn!=null) {
                    if (optionStatsIn.isStats()) {
                        double avg = optionStatsIn.stats().avg();
                        long count = optionStatsIn.stats().count();
                        double max = optionStatsIn.stats().max();
                        double min = optionStatsIn.stats().min();
                        double sum = optionStatsIn.stats().sum();
                        aggregationResultItem = new AggregationResultItem(rowTermsBucket.docCount(), avg, count, max, min, sum);
                    }
                    anAggMap.put(rowTermsBucket.key(), aggregationResultItem);
                }
                // 查询子聚合，矩阵单选与多选题
                if (rowTermsBucket.aggregations().containsKey("col_count")) {
                    aggregationResultItem.setDocCount(rowTermsBucket.docCount());
                    Map<String, AggregationResultItem> rowAnAggMap = new HashMap<>();
                    Aggregate  colCountAgg = rowTermsBucket.aggregations().get("col_count");
                    if (colCountAgg!=null) {
                        List<StringTermsBucket> colTermsBucketList = colCountAgg.sterms().buckets().array();
                        for (StringTermsBucket colTermsBucket : colTermsBucketList) {
                            logger.debug("row {}, col key {}, value {}", rowTermsBucket.key(), colTermsBucket.key(), colTermsBucket.docCount());
                            rowAnAggMap.put(colTermsBucket.key(), new AggregationResultItem(colTermsBucket.docCount()));
                        }
                    }
                    aggregationResultItem.setRowAnAggMap(rowAnAggMap);
                    anAggMap.put(rowTermsBucket.key(), aggregationResultItem);
                }
            }
            AggregationResult aggregationResult = new AggregationResult();
            aggregationResult.setAggMap(anAggMap);
            aggregationResult.setSum_other_doc_count(stringTermsAggregate.sumOtherDocCount());
            aggregationResult.setDoc_count_error_upper_bound(stringTermsAggregate.docCountErrorUpperBound());
            aggMaps.put(key,anAggMap);
        }
        return aggMaps;
    }

    public List<DwEsSurveyAnswerAnOption> dwEsSurveyAnswerAnOptions (DwEsSurveyAnswer dwEsSurveyAnswer, String responseId) {

        List<EsAnQuestion> anQuestionList = dwEsSurveyAnswer.getAnQuestions();
        List<DwEsSurveyAnswerAnOption> dwEsSurveyAnswerAnOptionList = new ArrayList<>();
        for (EsAnQuestion esAnQuestion:anQuestionList) {
            String quType = esAnQuestion.getQuType();
            if ("SCORE".equals(quType)) {
                List<EsAnScore> esAnScores = esAnQuestion.getAnScores();
                // 处理评分题
                for (EsAnScore esAnScore: esAnScores) {
                    DwEsSurveyAnswerAnOption dwEsSurveyAnswerAnOption = new DwEsSurveyAnswerAnOption();
                    dwEsSurveyAnswerAnOption.setAnswerCommon(dwEsSurveyAnswer.getAnswerCommon());
                    String answerScore = esAnScore.getAnswerScore();
                    if (NumberUtils.isNumber(answerScore)) dwEsSurveyAnswerAnOption.setAnswerNum(Float.parseFloat(answerScore));
                    dwEsSurveyAnswerAnOption.setAnswerOptionDwId(esAnScore.getOptionDwId());
                    dwEsSurveyAnswerAnOption.setQuDwId(esAnQuestion.getQuDwId());
                    dwEsSurveyAnswerAnOption.setQuType(esAnQuestion.getQuType());
                    //                    dwEsSurveyAnswerAnOption.setRelateDwIds();
                    dwEsSurveyAnswerAnOption.setRelateAnswerResponseId(responseId);
                    dwEsSurveyAnswerAnOptionList.add(dwEsSurveyAnswerAnOption);
                }
            } else if ("ORDERQU".equals(quType)) {
                List<EsAnOrder> esAnOrders = esAnQuestion.getAnOrders();
                // 处理评分题
                for (EsAnOrder esAnOrder: esAnOrders) {
                    DwEsSurveyAnswerAnOption dwEsSurveyAnswerAnOption = new DwEsSurveyAnswerAnOption();
                    dwEsSurveyAnswerAnOption.setAnswerCommon(dwEsSurveyAnswer.getAnswerCommon());
                    String orderNum = esAnOrder.getOrderNum();
                    if (NumberUtils.isNumber(orderNum)) dwEsSurveyAnswerAnOption.setAnswerNum(Float.parseFloat(orderNum));
                    dwEsSurveyAnswerAnOption.setAnswerOptionDwId(esAnOrder.getOptionDwId());
                    dwEsSurveyAnswerAnOption.setQuDwId(esAnQuestion.getQuDwId());
                    dwEsSurveyAnswerAnOption.setQuType(esAnQuestion.getQuType());
//                    dwEsSurveyAnswerAnOption.setRelateDwIds();
                    dwEsSurveyAnswerAnOption.setRelateAnswerResponseId(responseId);
                    dwEsSurveyAnswerAnOptionList.add(dwEsSurveyAnswerAnOption);
                }
            } else if ("MATRIX_RADIO".equals(quType)) {
                List<EsAnMatrixRadio> esAnMatrixRadios = esAnQuestion.getAnMatrixRadios();
                for (EsAnMatrixRadio esMatrixRadio:esAnMatrixRadios) {
                    // 单独存储起来
                    DwEsSurveyAnswerAnOption dwEsSurveyAnswerAnOption = new DwEsSurveyAnswerAnOption();
                    dwEsSurveyAnswerAnOption.setAnswerCommon(dwEsSurveyAnswer.getAnswerCommon());
                    dwEsSurveyAnswerAnOption.setEsAnMatrixRadio(esMatrixRadio);
                    dwEsSurveyAnswerAnOption.setQuDwId(esAnQuestion.getQuDwId());
                    dwEsSurveyAnswerAnOption.setQuType(esAnQuestion.getQuType());
                    dwEsSurveyAnswerAnOption.setRelateAnswerResponseId(responseId);
                    dwEsSurveyAnswerAnOptionList.add(dwEsSurveyAnswerAnOption);
                }
            } else if ("MATRIX_CHECKBOX".equals(quType)) {
                List<EsAnMatrixCheckbox> esAnMatrixCheckboxes = esAnQuestion.getAnMatrixCheckboxes();
                for (EsAnMatrixCheckbox esAnMatrixCheckbox:esAnMatrixCheckboxes) {
                    // 单独存储起来
                    DwEsSurveyAnswerAnOption dwEsSurveyAnswerAnOption = new DwEsSurveyAnswerAnOption();
                    dwEsSurveyAnswerAnOption.setAnswerCommon(dwEsSurveyAnswer.getAnswerCommon());
                    dwEsSurveyAnswerAnOption.setEsAnMatrixCheckbox(esAnMatrixCheckbox);
                    dwEsSurveyAnswerAnOption.setQuDwId(esAnQuestion.getQuDwId());
                    dwEsSurveyAnswerAnOption.setQuType(esAnQuestion.getQuType());
                    dwEsSurveyAnswerAnOption.setRelateAnswerResponseId(responseId);
                    dwEsSurveyAnswerAnOptionList.add(dwEsSurveyAnswerAnOption);
                }
            } else if ("MATRIX_SCALE".equals(quType) || "MATRIX_SLIDER".equals(quType)) {
                List<EsAnMatrixScale> esAnMatrixScales = esAnQuestion.getAnMatrixScales();
                for (EsAnMatrixScale esAnMatrixScale:esAnMatrixScales) {
                    DwEsSurveyAnswerAnOption dwEsSurveyAnswerAnOption = new DwEsSurveyAnswerAnOption();
                    dwEsSurveyAnswerAnOption.setAnswerCommon(dwEsSurveyAnswer.getAnswerCommon());
                    String answerScore = esAnMatrixScale.getAnswerScore();
                    if (NumberUtils.isNumber(answerScore)) dwEsSurveyAnswerAnOption.setAnswerNum(Float.parseFloat(answerScore));
                    dwEsSurveyAnswerAnOption.setAnswerOptionDwId(esAnMatrixScale.getRowDwId());
                    dwEsSurveyAnswerAnOption.setQuDwId(esAnQuestion.getQuDwId());
                    dwEsSurveyAnswerAnOption.setQuType(esAnQuestion.getQuType());
                    //                    dwEsSurveyAnswerAnOption.setRelateDwIds();
                    dwEsSurveyAnswerAnOption.setRelateAnswerResponseId(responseId);
                    dwEsSurveyAnswerAnOptionList.add(dwEsSurveyAnswerAnOption);
                }
            }
        }
        return dwEsSurveyAnswerAnOptionList;
    }

    public void deleteById(String esId) throws IOException {
        esClientService.deleteById(ANSWER_INDEX_NAME, esId);
        List<Query> queryList = new ArrayList<>();
        queryList.add(TermQuery.of(b -> b.field("relateAnswerResponseId").value(esId))._toQuery());
        Query query = Query.of(b -> b.bool(c -> c.must(queryList)));
        esClientService.deleteByQuery(ANSWER_INDEX_NAME_AGG, query);
    }

    public void deleteByIds(String[] ids) throws IOException {
        if (ids!=null) {
            for (int i=0; i<ids.length; i++) {
                deleteById(ids[i]);
            }
        }
    }
}
