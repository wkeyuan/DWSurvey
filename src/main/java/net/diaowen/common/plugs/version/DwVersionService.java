package net.diaowen.common.plugs.version;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.diaowen.common.dao.SuperHttpDao;
import net.diaowen.common.plugs.es.ESService;
import net.diaowen.common.plugs.httpclient.HttpClientUtils;
import net.diaowen.common.plugs.storage.service.local.LocalFileStoreServiceImpl;
import net.diaowen.common.utils.FileUtils;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.SysProperty;
import net.diaowen.dwsurvey.service.SysPropertyManager;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DwVersionService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DWSurveyConfig dwSurveyConfig;
    @Autowired
    private SuperHttpDao superHttpDao;
    @Autowired
    private LocalFileStoreServiceImpl localFileStoreService;
    @Autowired
    private SysPropertyManager sysPropertyManager;

    @PostConstruct
    public void checkVersion(){
        getNewVersionInfo();
    }
    /**
     * 版本检查
     * 根据当前版本信息，获取当前最新版本信息
     * 最好是：能自动下载新新版本发布包，自动更新，但要考虑自动更新的稳定性。
     */
    public JSONObject getNewVersionInfo() {
        try {
            // 根据客户端版本，查询服务器是否有最新版本
            JSONObject jsonParams = new JSONObject();
            // 随机生成一个服务编号
            String filePath = "/webin/client/client-code.dw";
            String clientCode = localFileStoreService.readFile(filePath);
            if (clientCode==null) {
                clientCode = RandomUtils.buildAppSecret();
                localFileStoreService.saveBinaryFile(clientCode.getBytes(),filePath, false);
            }
            jsonParams.put("clientCode", clientCode);
            // 当前部署版本名称
            jsonParams.put("versionName", DWSurveyConfig.DWSURVEY_VERSION_NAME);
            // 当前部署版本信息
            jsonParams.put("versionInfo", DWSurveyConfig.DWSURVEY_VERSION_INFO);
            // 当前部署版本发布日期
            jsonParams.put("versionDate", DWSurveyConfig.DWSURVEY_VERSION_BUILT);
            // 操作系统名称
            jsonParams.put("osName", System.getProperty("os.name"));
            // 操作系统版本
            jsonParams.put("osVersion", System.getProperty("os.version"));
            // JAVA版本
            jsonParams.put("javaVersion", System.getProperty("java.version"));
            // 创建时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonParams.put("clientDate", simpleDateFormat.format(new Date()));
            // 信息类型 setup 启动版本检测
            jsonParams.put("messageType", "setup-version");
            logger.info("version info {}", jsonParams.toJSONString());
            // 信息内容，比如错误信息
            String getNewVersionUrl = DWSurveyConfig.DWSURVEY_VERSION_API+"/api/dws-version/get-new-version.do";
            HttpPost httpPost = HttpClientUtils.buildHttpPostFormJson(getNewVersionUrl,jsonParams.toJSONString());
            String result = superHttpDao.doPost(httpPost);
            if(result!=null) {
                JSONObject resultObjs = JSON.parseObject(result);
                logger.info("new version info {}", resultObjs);
                if (resultObjs.get("data") != null) {
                    JSONObject newVersionResult = resultObjs.getJSONObject("data");
//                    logger.info("data {}", newVersionResult);
                    // 把最新的版本信息同步记录下来
                    String versionInfo = newVersionResult.getString("versionInfo");
                    String updateLevel = newVersionResult.getString("updateLevel");
                    SysProperty sysProperty = new SysProperty();
                    sysProperty.setMaxUpLevel(updateLevel);
                    sysProperty.setNewVersionInfo(versionInfo);
                    sysPropertyManager.save(sysProperty);
                    return newVersionResult;
                }
            }
        }catch (Exception e){
            logger.error("get new version error");
        }
        return null;
    }

    // 自动下载新版本自动更新待实现
    public void download() {

    }

}
