package net.diaowen.dwsurvey.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

// 配置的前缀
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ESClientConfig {
    private static Logger logger = LoggerFactory.getLogger(ESClientConfig.class);

    @Setter
    private String hosts;
    @Setter
    private String username;
    @Setter
    private String passwd;
    @Setter
    private String apikey;
    @Setter
    private String certName;
    private static String CERT_NAME = "http_ca.crt";
    @Setter
    private String security;
    @Setter
    private String scheme = "cert";

    @PostConstruct
    private void init() {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(certName)) CERT_NAME = certName;
        logger.info("CERT_NAME {}", CERT_NAME);
        logger.info("security {}", security);
        logger.info("scheme {}", scheme);
    }

    /**
     * 解析配置的hosts字符串
     * @return
     */
    private HttpHost[] toHttpHost() {
        if (!StringUtils.hasLength(hosts)) throw new RuntimeException("请检查elasticsearch.hosts配置");
        String[] hostArray = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        HttpHost httpHost;
        for (int i = 0; i < hostArray.length; i++) {
            String[] strings = hostArray[i].split(":");
            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), scheme);
            httpHosts[i] = httpHost;
        }
        return httpHosts;
    }

    /**
     * 构建SSL信息
     * @return SSLContext
     */
    private static SSLContext buildSSLContext() {
        SSLContext sslContext = null;
        try {
            ClassPathResource resource = new ClassPathResource(CERT_NAME);
            if (resource.exists()) {
                CertificateFactory factory = CertificateFactory.getInstance("X.509");
                Certificate trustedCa;
                InputStream is = resource.getInputStream();
                trustedCa = factory.generateCertificate(is);
                KeyStore trustStore = KeyStore.getInstance("pkcs12");
                trustStore.load(null, null);
                trustStore.setCertificateEntry("ca", trustedCa);
                SSLContextBuilder sslContextBuilder = SSLContexts.custom()
                        .loadTrustMaterial(trustStore, null);
                sslContext = sslContextBuilder.build();
            }
        } catch (Exception e) {
            logger.error("ES连接认证失败 {}", e);
        }
        return sslContext;
    }

    /**
     * 依据账号密码，获取一个 ElasticsearchTransport 对象 SSL
     * @param username
     * @param passwd
     * @param hosts
     * @return ElasticsearchTransport
     */
    private static ElasticsearchTransport getElasticsearchTransportSSL(String username, String passwd, HttpHost...hosts) {

        // 账号密码的配置
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, passwd));
        // 自签证书的设置，并且还包含了账号密码
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider);
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(callback)
                .build();
        return new RestClientTransport(client, new JacksonJsonpMapper());
    }

    /**
     * 依据APIKEY，获取一个 ElasticsearchTransport 对象
     * @param apiKey
     * @param hosts
     * @return
     */
    private static ElasticsearchTransport getElasticsearchTransportSSL(String apiKey, HttpHost...hosts) {
        // 将ApiKey放入header中
        Header[] headers = new Header[] {new BasicHeader("Authorization", "ApiKey " + apiKey)};
        // es自签证书的设置
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(callback)
                .setDefaultHeaders(headers)
                .build();
        return new RestClientTransport(client, new JacksonJsonpMapper());
    }

    /**
     * 依据账号密码，获取一个 ElasticsearchTransport 对象
     * @param username
     * @param passwd
     * @param hosts
     * @return ElasticsearchTransport
     */
    private static ElasticsearchTransport getHttpElasticsearchTransport(String username, String passwd, HttpHost...hosts) {
        // 账号密码的配置
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, passwd));
        // 自签证书的设置，并且还包含了账号密码
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(callback)
                .build();
        return new RestClientTransport(client, new JacksonJsonpMapper());
    }


    /**
     * 通过密码认证，获取ElasticsearchClient
     * @return ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient clientBySSLPasswd() throws Exception {
        ElasticsearchTransport transport = getElasticsearchTransportSSL(username, passwd, toHttpHost());
        return new ElasticsearchClient(transport);
    }

    /**
     * 通过APIKEY认证，获取ElasticsearchClient
     * @return ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient clientBySSLApiKey() throws Exception {
        ElasticsearchTransport transport = getElasticsearchTransportSSL(apikey, toHttpHost());
        return new ElasticsearchClient(transport);
    }

    /**
     * 异步方式
     * 通过密码认证，获取ElasticsearchAsyncClient
     * @return ElasticsearchAsyncClient
     */
    @Bean
    public ElasticsearchAsyncClient asyncClientBySSLPasswd() {
        ElasticsearchTransport transport = getElasticsearchTransportSSL(username, passwd, toHttpHost());
        return new ElasticsearchAsyncClient(transport);
    }

    /**
     * 异步方式
     * 通过APIKEY认证，获取ElasticsearchAsyncClient
     * @return ElasticsearchAsyncClient
     */
    @Bean
    public ElasticsearchAsyncClient asyncClientBySSLApiKey() {
        ElasticsearchTransport transport = getElasticsearchTransportSSL(apikey, toHttpHost());
        return new ElasticsearchAsyncClient(transport);
    }


    /**
     * 无密码认证
     * @return
     */
    @Bean
    public ElasticsearchClient noPwdClient(){
        // 用builder创建RestClient对象
        RestClient client = RestClient
                //.builder(new HttpHost("127.0.0.1",9200,"http"))
                .builder(toHttpHost())
                .build();
        return new ElasticsearchClient(new RestClientTransport(client, new JacksonJsonpMapper()));
    }

    /**
     * 通过http密码认证，获取ElasticsearchClient
     * @return ElasticsearchClient
     */
    public ElasticsearchClient clientByPasswd() throws Exception {
        ElasticsearchTransport transport = getHttpElasticsearchTransport(username, passwd, toHttpHost());
        return new ElasticsearchClient(transport);
    }


    @Bean
    public ElasticsearchClient defaultClient() throws Exception {
       // 根据认证方式，判断使用哪种认证提供服务
        if ("pwd".equals(security)) {
            // 密码认证
            return clientByPasswd();
        } else if ("noPwd".equals(security)) {
            // 无认证
            return noPwdClient();
        } else {
            // 证书认证
            return clientBySSLPasswd();
        }
    }

}
