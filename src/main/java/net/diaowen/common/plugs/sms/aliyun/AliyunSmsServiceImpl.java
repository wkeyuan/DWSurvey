package net.diaowen.common.plugs.sms.aliyun;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import net.diaowen.common.plugs.sms.SmsService;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

//@Service
public class AliyunSmsServiceImpl implements SmsService {


    public static String AccessKeyID="";
    public static String AccessKeySecret="";

    public boolean sendCode(final String phone,final String code) {
        if("true".equals(DWSurveyConfig.DWSURVEY_SMS_CODE_OPEN)){
           try{
               StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                       // Please ensure that the environment variables ALIBABA_CLOUD_ACCESS_KEY_ID and ALIBABA_CLOUD_ACCESS_KEY_SECRET are set.
//                       .accessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
//                       .accessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"))
                       .accessKeyId(DWSurveyConfig.DWSURVEY_ALIYUN_ACCESS_KEY_ID)
                       .accessKeySecret(DWSurveyConfig.DWSURVEY_ALIYUN_SECRET_ACCESS_KEY)
                       //.securityToken(System.getenv("ALIBABA_CLOUD_SECURITY_TOKEN")) // use STS token
                       .build());

               // Configure the Client
               AsyncClient client = AsyncClient.builder()
                       .region("cn-zhangjiakou") // Region ID
                       //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                       .credentialsProvider(provider)
                       //.serviceConfiguration(Configuration.create()) // Service-level configuration
                       // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                       .overrideConfiguration(
                               ClientOverrideConfiguration.create()
                                       // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                                       .setEndpointOverride("dysmsapi.aliyuncs.com")
                               //.setConnectTimeout(Duration.ofSeconds(30))
                       )
                       .build();

               // Parameter settings for API request
               SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                       .signName("xx")
                       .templateCode("xx")
                       .phoneNumbers(phone)
                       .templateParam("{\"code\":\""+code+"\"}")
                       // Request-level configuration rewrite, can set Http request parameters, etc.
                       // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                       .build();

               // Asynchronously get the return value of the API request
               CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
               // Synchronously get the return value of the API request
               SendSmsResponse resp = response.get();
               System.out.println(new Gson().toJson(resp));
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        return true;
    }

    public static void main(String[] args) {
        AliyunSmsServiceImpl aliyun = new AliyunSmsServiceImpl();
        aliyun.sendCode("", "");
    }


}
