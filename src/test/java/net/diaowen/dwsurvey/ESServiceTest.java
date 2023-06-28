package net.diaowen.dwsurvey;

import net.diaowen.common.plugs.es.ESService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ESServiceTest {

    @Autowired
    ESService esService;

    @Test
    void addIndex() throws Exception {
        String indexName = "dw_test_index";
        Assertions.assertFalse(esService.indexExists(indexName));
        esService.addIndex(indexName);
        Assertions.assertTrue(esService.indexExists(indexName));
        esService.delIndex(indexName);
        Assertions.assertFalse(esService.indexExists(indexName));
    }
}