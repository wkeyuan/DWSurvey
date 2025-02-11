package net.diaowen.common.plugs.sms.sendcloud;

import net.diaowen.common.dao.SuperHttpDao;
import net.diaowen.common.plugs.sms.SmsService;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SendCloudSmsServiceImpl implements SmsService {


    public static String smsUser="";
    public static String smsKey="";

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private SuperHttpDao superHttpDao;

    public boolean sendCode(final String phone,final String code) {
        if("true".equals(DWSurveyConfig.DWSURVEY_SMS_CODE_OPEN)){
            taskExecutor.execute(new Runnable() {
                public void run() {
                    String url = "http://www.sendcloud.net/smsapi/sendCode";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("smsUser", DWSurveyConfig.DWSURVEY_SMS_SC_SMS_USER);
                    params.put("phone", phone);
                    params.put("signId", "6473");
                    //        params.put("signName", "");
                    params.put("code", code);
                    //        params.put("labelId", "");
                    //        params.put("signature", "");
                    //        params.put("timestamp", "");

                    // 对参数进行排序
                    Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {

                        public int compare(String arg0, String arg1) {
                            // 忽略大小写
                            return arg0.compareToIgnoreCase(arg1);
                        }
                    });
                    sortedMap.putAll(params);

                    // 计算签名
                    StringBuilder sb = new StringBuilder();
                    sb.append(DWSurveyConfig.DWSURVEY_SMS_SC_SMS_KEY).append("&");
                    for (String s : sortedMap.keySet()) {
                        sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
                    }
                    sb.append(DWSurveyConfig.DWSURVEY_SMS_SC_SMS_KEY);
                    String sig = DigestUtils.md5Hex(sb.toString());

                    // 将所有参数和签名添加到post请求参数数组里
                    List<NameValuePair> postparams = new ArrayList<NameValuePair>();
                    for (String s : sortedMap.keySet()) {
                        postparams.add(new BasicNameValuePair(s, sortedMap.get(s)));
                    }
                    postparams.add(new BasicNameValuePair("signature", sig));

                    HttpPost httpPost = new HttpPost(url);
                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(postparams, "utf8"));
                        String result = superHttpDao.doPost(httpPost);
                        System.out.println(result);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    } finally {
                        httpPost.releaseConnection();
                    }
                };
            });
        }
        return true;
    }


}
