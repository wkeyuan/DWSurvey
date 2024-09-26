package net.diaowen.common.plugs.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.JsonData;
import net.diaowen.dwsurvey.config.ESClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ESClientService {
    private static Logger logger = LoggerFactory.getLogger(ESClientConfig.class);

//    @Resource(name="clientByPasswd")
    @Resource(name = "noPwdClient")
    private ElasticsearchClient elasticsearchClient;

    private static class SomeApplicationData {}

    //判断索引是否存在
    public boolean indexExists(String name) throws IOException {
        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
    }

    //创建索引
    public boolean createIndex(String indexName, String resourcePath) throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            CreateIndexRequest req = CreateIndexRequest.of(b -> b.index(indexName).withJson(inputStream));
            return Boolean.TRUE.equals(elasticsearchClient.indices().create(req).acknowledged());
        }
    }

    //创建文档
    public IndexResponse createDoc(String indexName, String docJson) throws IOException {
        Reader input = new StringReader(docJson);
        IndexRequest<JsonData> request = IndexRequest.of(i -> i.index(indexName).withJson(input));
        IndexResponse response = elasticsearchClient.index(request);
        logger.info("Indexed with version " + response.id());
        return response;
    }

    /**
     * 查询文档 from+size 分页方式
     * @param indexName
     * @param query
     * @param from
     * @param size
     * @param tDocumentClass
     * @return
     * @param <TDocument>
     * @throws IOException
     */
    public  <TDocument> SearchResponse<TDocument> findPageByFromSize(String indexName, Query query, List<SortOptions> sortOptions, int from, int size, Class<TDocument> tDocumentClass) throws IOException {
       /*
       SearchRequest.Builder searchRequest = new SearchRequest.Builder();
        searchRequest.query(q -> q.matchAll(m -> m));
        List<FieldValue> terms = new ArrayList<>();
        terms.add(FieldValue.of(""));
        terms.add(FieldValue.of(""));
        Query query = Query.of(q -> q.terms(t -> t.field("").terms(tt -> tt.value(terms))));
        searchRequest.query(query);
        Query query1 = c.of(t -> t.field("filed").value("value"))._toQuery();
        searchRequest.index("");
//        searchRequest.withJson()
        elasticsearchClient.search(searchRequest.build(), Object.class);
        SearchResponse<ObjectNode> response;
        response = elasticsearchClient.search(searchRequest.trackTotalHits(t->t.enabled(true)).build(), ObjectNode.class);
        String searchText = "bike";
        double maxPrice = 200.0;
        // Search by product name
        Query byName = MatchQuery.of(m -> m.field("name").query(searchText))._toQuery();
        // Search by max price
        Query byMaxPrice = RangeQuery.of(r -> r.field("price").gte(JsonData.of(maxPrice)))._toQuery();
        SearchResponse<ObjectNode> searchResponse = elasticsearchClient.search(s -> s.index("products").query(q -> q.bool(b -> b.must(byName).must(byMaxPrice))), ObjectNode.class);
        */
        int maxResultNum = 10000;
        if ((from+size)>maxResultNum) from = maxResultNum-size; // 防止超过1万
        int queryFrom = from;
        SearchResponse<TDocument> response = elasticsearchClient.search(s -> s.index(indexName).query(query).from(queryFrom).size(size).sort(sortOptions), tDocumentClass);
//        response.took() //用时
        List<Hit<TDocument>> hits = response.hits().hits();
        /*for (Hit<TDocument> hit: hits) {
            DwEsSurveyAnswer product = hit.source();
            assert product != null;
            String surveyDwId = product.getSurveyDwId();
            logger.info("Found product {}", surveyDwId);
            logger.info("Found product {}", product.getAnTime().getBgAnDate());
        }*/
        return response;
    }

    public  <TDocument> SearchResponse<TDocument> findPageByScroll(String indexName, Query query, List<SortOptions> sortOptions, int size, Class<TDocument> tDocumentClass) throws IOException {
       /*
       SearchRequest.Builder searchRequest = new SearchRequest.Builder();
        searchRequest.query(q -> q.matchAll(m -> m));
        List<FieldValue> terms = new ArrayList<>();
        terms.add(FieldValue.of(""));
        terms.add(FieldValue.of(""));
        Query query = Query.of(q -> q.terms(t -> t.field("").terms(tt -> tt.value(terms))));
        searchRequest.query(query);
        Query query1 = c.of(t -> t.field("filed").value("value"))._toQuery();
        searchRequest.index("");
//        searchRequest.withJson()
        elasticsearchClient.search(searchRequest.build(), Object.class);
        SearchResponse<ObjectNode> response;
        response = elasticsearchClient.search(searchRequest.trackTotalHits(t->t.enabled(true)).build(), ObjectNode.class);
        String searchText = "bike";
        double maxPrice = 200.0;
        // Search by product name
        Query byName = MatchQuery.of(m -> m.field("name").query(searchText))._toQuery();
        // Search by max price
        Query byMaxPrice = RangeQuery.of(r -> r.field("price").gte(JsonData.of(maxPrice)))._toQuery();
        SearchResponse<ObjectNode> searchResponse = elasticsearchClient.search(s -> s.index("products").query(q -> q.bool(b -> b.must(byName).must(byMaxPrice))), ObjectNode.class);
        */
        SearchResponse<TDocument> response = elasticsearchClient.search(s -> s.index(indexName).query(query).size(size).sort(sortOptions).scroll(Time.of(t -> t.time("5m"))), tDocumentClass);
//        response.took() //用时
        List<Hit<TDocument>> hits = response.hits().hits();
        /*for (Hit<TDocument> hit: hits) {
            DwEsSurveyAnswer product = hit.source();
            assert product != null;
            String surveyDwId = product.getSurveyDwId();
            logger.info("Found product {}", surveyDwId);
            logger.info("Found product {}", product.getAnTime().getBgAnDate());
        }*/
        return response;
    }

    public  <TDocument> SearchResponse<TDocument> findPageByScrollId(String scrollId, Class<TDocument> tDocumentClass) throws IOException {
//        SearchResponse<TDocument> response = elasticsearchClient.search(s -> s.index(indexName).query(query).size(size).scroll(Time.of(t -> t.time("5m"))), tDocumentClass);
//        SearchResponse<TDocument> response = elasticsearchClient.scroll(s->s.scrollId(scrollId), tDocumentClass);
        // 使用scrollId进行后续的查询
        // 使用scrollId进行搜索
        SearchResponse<TDocument> response = elasticsearchClient.scroll(s -> s.scrollId(scrollId).scroll(t -> t.time("5m")), tDocumentClass);
        return response;
    }

    public <TDocument> GetResponse<TDocument> getDocById(String indexName, String id, Class<TDocument> tDocumentClass) throws IOException {
//        Reader input = new StringReader(docJson);
        return elasticsearchClient.get(g -> g.index(indexName).id(id), tDocumentClass);
    }


    public long getCount(String indexName, Query query) throws IOException {
        CountRequest request = CountRequest.of(i -> i.index(indexName).query(query));
        return elasticsearchClient.count(request).count();
    }

    public Map<String, Aggregate> aggregationSearch(String indexName, Query query, Map<String, Aggregation> aggKeyMaps) {
        try {
//            "count_level", a -> a.terms(t -> t.field(aggFieldName)
            SearchResponse<Void> response = elasticsearchClient.search(s -> s.index(indexName).query(query).aggregations(aggKeyMaps), Void.class);
            logger.debug("response.hits().total().value() {}", response.hits().total().value());
            return response.aggregations();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DeleteResponse deleteById(String indexName, String esId) throws IOException {
        DeleteRequest request = DeleteRequest.of(i -> i.index(indexName).id(esId));
        return elasticsearchClient.delete(request);
    }

    public DeleteByQueryResponse deleteByQuery(String indexName, Query query) throws IOException {
        DeleteByQueryRequest deleteByQueryRequest = DeleteByQueryRequest.of(i -> i.index(indexName).query(query));
        return elasticsearchClient.deleteByQuery(deleteByQueryRequest);
    }

    public void clearScroll(String clearScrollId) throws IOException {
        elasticsearchClient.clearScroll(c->c.scrollId(clearScrollId));
    }
}
