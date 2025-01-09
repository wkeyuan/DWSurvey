package net.diaowen.common.plugs.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ESService {

    public final static String INDEX_PREV = "test";

//    @Resource(name="clientByPasswd")
    @Resource(name = "noPwdClient")
    private ElasticsearchClient elasticsearchClient;

    // 创建索引
    public void addIndex(String name) throws IOException {
        elasticsearchClient.indices().create(c -> c.index(name));
    }

    public boolean indexExists(String name) throws IOException {
        return elasticsearchClient.indices().exists(b -> b.index(name)).value();
    }

    public void delIndex(String name) throws IOException {
        elasticsearchClient.indices().delete(c -> c.index(name));
    }

    // 保存答卷时，让ES自动生成ID。但我需要返回的ID值。
    // 把答卷IP等信息都记录在ES，原始数据页面直接展现ES的文档记录。
    // 用户提交答卷，新问卷直接保存，并自动生成ID。
    // 如何是用户修改问卷，会有EsID。
    // ES 数据结构
    // surveyAnswer = {id,ip,userId,answerQuestion:[{quId,quRadio:{}}]}
}