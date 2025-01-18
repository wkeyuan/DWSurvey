package net.diaowen.common.base.service;

import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.version.DwVersionService;
import net.diaowen.common.plugs.weixin.WeixinMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 统一定时任务的service
 */
@Service
public class TaskService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeixinMpService weixinMpService;
    @Autowired
    private DwVersionService dwVersionService;

    public void taskRefAccessToken() {
        weixinMpService.taskRefAccessToken();
    }

    public void checkVersion() {
        dwVersionService.checkVersion();
    }

}
