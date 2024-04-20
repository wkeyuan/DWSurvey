package net.diaowen.common.plugs.storage.service.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

public class OSSAliyun {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取 OSSClient
     * @return
     */
    public static OSS getOSSClient(){
        OSS ossClient = new OSSClientBuilder().build(DWSurveyConfig.DWSURVEY_ALIYUN_ENDPOINT, DWSurveyConfig.DWSURVEY_ALIYUN_ACCESS_KEY_ID, DWSurveyConfig.DWSURVEY_ALIYUN_SECRET_ACCESS_KEY);
        return ossClient;
    }

    /**
     * 创建或获取bulcket
     */
    public static void createAndGetBucketName(OSS ossClient, String bucketName){
//        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
        if (!ossClient.doesBucketExist(bucketName)) {
            // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            ossClient.createBucket(bucketName);
        }
        BucketInfo info = ossClient.getBucketInfo(bucketName);
//        setBucketCors(ossClient, bucketName);
    }

    public static void putObject(byte[] bytes,String savePath, boolean isPub) throws Exception{
        ObjectMetadata metadata = new ObjectMetadata();
        String bucketName = getBucketName(metadata,savePath,isPub);
        OSSAliyun.pubObject(bucketName,bytes,savePath,metadata);
    }

    public static void pubObject(String bucketName, byte[] bytes, String fileName, ObjectMetadata metadata)  throws IOException {
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        createAndGetBucketName(ossClient,bucketName);
        //目录如果开头是 / 会报错
        if (fileName.startsWith("/"))  fileName = fileName.substring(1);
        PutObjectRequest putObjectRequest;
        putObjectRequest = new PutObjectRequest(bucketName, fileName, new ByteArrayInputStream(bytes));
        putObjectRequest.setMetadata(metadata);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }

    public static void putObject(InputStream inputStream,String savePath, boolean isPub) throws Exception{
        ObjectMetadata metadata = new ObjectMetadata();
        String bucketName = getBucketName(metadata,savePath,isPub);
        OSSAliyun.pubObject(bucketName,inputStream,savePath,metadata);
    }

    public static void putObject(File file,String savePath, boolean isPub) throws Exception{
        ObjectMetadata metadata = new ObjectMetadata();
        String bucketName = getBucketName(metadata,savePath,isPub);
        InputStream inputStream = new FileInputStream(file);
        OSSAliyun.pubObject(bucketName,inputStream,savePath,metadata);
    }

    public static void pubObject(String bucketName, InputStream inputStream, String fileName, ObjectMetadata metadata)  throws IOException {
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        createAndGetBucketName(ossClient,bucketName);
        //目录如果开头是 / 会报错
        if (fileName.startsWith("/")) fileName = fileName.substring(1);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
        putObjectRequest.setMetadata(metadata);
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }

    public static void putObject(String bucketName, File file, String fileName,ObjectMetadata metadata)
            throws IOException {
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        // 获取指定文件的输入流
        // File file = new File(fileRealPath + fileName);
        InputStream content = new FileInputStream(file);
        // 创建上传Object的Metadata
        // 必须设置ContentLength
        // metadata.setContentLength(file.length());
        metadata.setContentEncoding("UTF-8");
        // 上传Object.
        String key = fileName;
        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        ossClient.putObject(bucketName, key, content, metadata);
        content.close();
        ossClient.shutdown();
    }

    public static void putObject(String bucketName, InputStream inputStream,
                                 String fileName) throws IOException {
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        // 获取指定文件的输入流
        // File file = new File(fileRealPath + fileName);
        // InputStream content = new FileInputStream(file);
        InputStream content = inputStream;
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        // 必须设置ContentLength
        // meta.setContentLength(file.length());
        // meta.setContentEncoding("UTF-8");
        // 上传Object.
        fileName = clearObjectName(fileName);
        String key = fileName;
        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        ossClient.putObject(bucketName, key, content,meta);
        content.close();
        ossClient.shutdown();
    }


    public static String getBucketName(String fileName){
        String bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEB_PUBLIC_BUCKET;
//        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        if(fileName.indexOf("webin")>=0){
            //安全存储空间 WEB-INF
            bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEB_PRIVATE_BUCKET;
        }
        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        return bucketName;
    }

    public static String getBucketName(ObjectMetadata metadata, String savePath, boolean isPub){

        /*String bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEB_PUBLIC_BUCKET;
        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        if( !isPub || savePath.indexOf("webin")>=0){
            //安全存储空间 webin WEB-INF
            bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEBINF_PRIVATE_BUCKET;
            metadata.setObjectAcl(CannedAccessControlList.Private);
        }*/

        String bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEB_PUBLIC_BUCKET;
//        metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        if( !isPub || savePath.indexOf("webin")>=0){
            //安全存储空间 webin WEB-INF
            bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_WEB_PRIVATE_BUCKET;
            metadata.setObjectAcl(CannedAccessControlList.Private);
        }
        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        return bucketName;
    }

    public static OSSObject getObject(OSS ossClient,String fileName){
        return getObject(ossClient,getBucketName(fileName),fileName);
    }

    public static OSSObject getObject(OSS ossClient,String bucketName,String fileName) {
        OSSObject ossObject = ossClient.getObject(bucketName, clearObjectName(fileName));
        return ossObject;
    }

    public static URL getObjectUrl(String fileName){
        OSS ossClient = getOSSClient();
        // 设置URL过期时间为1小时。
        Date expiration = new Date(new Date().getTime() + 60 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        String bucketName = getBucketName(fileName);
        URL url = ossClient.generatePresignedUrl(bucketName, clearObjectName(fileName), expiration);
        ossClient.shutdown();
        return url;
    }

    public static InputStream getInputStream(OSS ossClient,String fileName) {
        OSSObject ossObject = getObject(ossClient,clearObjectName(fileName));
        InputStream inputStream = ossObject.getObjectContent();
        return inputStream;
    }

    public static List<OSSObjectSummary> getObjectList(String bucketName,
                                                       String prefix, int endIndex, int maxKeys) {
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        bucketName = DWSurveyConfig.DWSURVEY_ALIYUN_BUCKET_PREFIX+"-"+bucketName;
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 递归列出fun目录下的所有文件
        listObjectsRequest.setPrefix(prefix);
        listObjectsRequest.setMaxKeys(maxKeys);
        // listObjectsRequest.setMarker("ueditor/jsp/upload/image/20150614/1434267568217077840.jpg");
        ObjectListing listing = null;
        String nextMarker = null;
        // endIndex>maxKeys
        int pageNum = endIndex / maxKeys;
        for (int i = 0; i < pageNum; i++) {
            if (nextMarker != null) listObjectsRequest.setMarker(nextMarker);
            listing = ossClient.listObjects(listObjectsRequest);
            nextMarker = listing.getNextMarker();
        }
        // 遍历所有Object
        List<OSSObjectSummary> objectSummarys = listing.getObjectSummaries();
        return objectSummarys;
        // 遍历所有CommonPrefix
        /*
         * System.out.println("\nCommonPrefixs:"); for (String commonPrefix :
         * listing.getCommonPrefixes()) { System.out.println(commonPrefix); }
         */
    }

    public static void copyObj(String fromPath,String toPath){
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        // String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey
        String bucketName = getBucketName(fromPath);
        try{
            ossClient.copyObject(bucketName,clearObjectName(fromPath),bucketName,clearObjectName(toPath));
        }catch (Exception e){
            System.out.println("没有可复制的文件："+fromPath);
//            e.printStackTrace();
        }
        ossClient.shutdown();
    }

    public static boolean downFile2Local(String realPath,String toFilePath) {
        OSS ossClient = getOSSClient();
        String filePath = toFilePath.substring(0,toFilePath.lastIndexOf("/"));
        filePath = filePath.replace("/",File.separator);
        File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
        toFilePath = toFilePath.replace("/",File.separator);
        ossClient.getObject(new GetObjectRequest(getBucketName(realPath), clearObjectName(realPath)), new File(toFilePath));
        ossClient.shutdown();
        return true;
    }

    public static void downloadFile(String fromPath,String toPath){
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        // String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey
        String bucketName = getBucketName(fromPath);
        ossClient.getObject(new GetObjectRequest(bucketName, clearObjectName(fromPath)), new File(toPath));
        ossClient.shutdown();
    }

    public static void downloadPath(String fromPath,String toPath){
        // 初始化OSSClient
        OSS ossClient = getOSSClient();
        // String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey
        String bucketName = getBucketName(fromPath);
        ObjectListing objectListing = ossClient.listObjects(bucketName,clearObjectName(fromPath));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            String toFilePath = toPath+s.getKey();
            String filePath = toFilePath.substring(0,toFilePath.lastIndexOf("/"));
            filePath = filePath.replace("/",File.separator);
            File file = new File(filePath);
            if (!file.exists()) file.mkdirs();
            toFilePath = toFilePath.replace("/",File.separator);
            ossClient.getObject(new GetObjectRequest(bucketName, s.getKey()), new File(toFilePath));
        }
        ossClient.shutdown();
    }

    public static Map<String,Integer> getPathChildren(OSS ossClient,String prefixPath){
        Map<String,Integer> resultMap = new HashMap<>();
        // 初始化OSSClient
//        OSS ossClient = getOSSClient();
//        String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey
        String bucketName = getBucketName(prefixPath);
        // 构造ListObjectsV2Request请求。
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request(bucketName);
        // 设置prefix参数来获取fun目录下的所有文件与文件夹。
        listObjectsV2Request.setPrefix(clearObjectName(prefixPath));
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsV2Request.setDelimiter("/");
        // 发起列举请求。
        ListObjectsV2Result result = ossClient.listObjectsV2(listObjectsV2Request);
        // 遍历文件。
        // objectSummaries的列表中给出的是fun目录下的文件。
        for (OSSObjectSummary objectSummary : result.getObjectSummaries()) {
            resultMap.put(objectSummary.getKey(),1);
        }
        // 遍历commonPrefix。
        // commonPrefixs列表中显示的是fun目录下的所有子文件夹。由于fun/movie/001.avi和fun/movie/007.avi属于fun文件夹下的movie目录，因此这两个文件未在列表中。
        for (String commonPrefix : result.getCommonPrefixes()) {
            resultMap.put(commonPrefix,2);
        }
        return resultMap;
    }

    public static void deletePath(String deletepath){
        OSS ossClient = getOSSClient();
        String bucketName = getBucketName(deletepath);
        // 列举所有包含指定前缀的文件并删除。
        String nextMarker = null;
        ObjectListing objectListing = null;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName)
                    .withPrefix(clearObjectName(deletepath))
                    .withMarker(nextMarker);
            objectListing = ossClient.listObjects(listObjectsRequest);
            if (objectListing.getObjectSummaries().size() > 0) {
                List<String> keys = new ArrayList<String>();
                for (OSSObjectSummary s : objectListing.getObjectSummaries()) {
//                    System.out.println("key name: " + s.getKey());
                    keys.add(s.getKey());
                }
                DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(keys);
                ossClient.deleteObjects(deleteObjectsRequest);
            }
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());

        ossClient.shutdown();
    }

    public static String clearObjectName(String objectName){
        //目录如果开头是 / 会报错
        if (objectName.startsWith("/")) {
            objectName = objectName.substring(1);
        }
        return objectName;
    }

    private static void setBucketCors(OSS ossClient, String bucketName) {
        // 添加Referer白名单。Referer参数支持通配符星号（*）和问号（？）。
        SetBucketCORSRequest request = new SetBucketCORSRequest(bucketName);
        // 跨域资源共享规则的容器，每个存储空间最多允许10条规则。
        ArrayList<SetBucketCORSRequest.CORSRule> putCorsRules = new ArrayList<SetBucketCORSRequest.CORSRule>();
        SetBucketCORSRequest.CORSRule corRule = new SetBucketCORSRequest.CORSRule();
        ArrayList<String> allowedOrigin = new ArrayList<String>();
        // 指定允许跨域请求的来源。
        allowedOrigin.add("http://*.diaowen.net");
        allowedOrigin.add("http://*.surveyform.cn");
        allowedOrigin.add("http://localhost:8081");
        ArrayList<String> allowedMethod = new ArrayList<String>();
        // 指定允许的跨域请求方法(GET/PUT/DELETE/POST/HEAD)。
        allowedMethod.add("GET/PUT/DELETE/POST/HEAD");
        ArrayList<String> allowedHeader = new ArrayList<String>();
        // 是否允许预取指令（OPTIONS）中Access-Control-Request-Headers头中指定的Header。
        allowedHeader.add("*");
        // AllowedOrigins和AllowedMethods最多支持一个星号（*）通配符。星号（*）表示允许所有的域来源或者操作。
        corRule.setAllowedMethods(allowedMethod);
        corRule.setAllowedOrigins(allowedOrigin);
        // AllowedHeaders和ExposeHeaders不支持通配符。
        corRule.setAllowedHeaders(allowedHeader);
        // 指定浏览器对特定资源的预取（OPTIONS）请求返回结果的缓存时间，单位为秒。
        corRule.setMaxAgeSeconds(10);
        // 最多允许10条规则。
        putCorsRules.add(corRule);
        // 已存在的规则将被覆盖。
        request.setCorsRules(putCorsRules);
        // 指定是否返回Vary: Origin头。指定为TRUE，表示不管发送的是否为跨域请求或跨域请求是否成功，均会返回Vary: Origin头。指定为False，表示任何情况下都不会返回Vary: Origin头。
        request.setResponseVary(Boolean.TRUE);
        ossClient.setBucketCORS(request);
    }

}
