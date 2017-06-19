package com.key.common.plugs.baiduyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObject;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.key.common.utils.DiaowenProperty;

/**
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public class BaiduBOS {	
	/*
	static String ACCESS_KEY_ID = "";
	static String SECRET_ACCESS_KEY = "";
	static String ENDPOINT = "http://gz.bcebos.com";
	
	public static String  WENJUANHTML_BACKET="";
	public static String  UPLOADFILE_BACKET="";
	public static String  UPLOADFILE_JM_BACKET="";
	*/
	
	public static BosClient getClient(){
		 // 初始化一个BosClient
	    BosClientConfiguration config = new BosClientConfiguration();
//	    config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID,SECRET_ACCESS_KEY));
//	    config.setEndpoint(ENDPOINT);
	    config.setCredentials(new DefaultBceCredentials(DiaowenProperty.ACCESS_KEY_ID,DiaowenProperty.SECRET_ACCESS_KEY));
	    config.setEndpoint(DiaowenProperty.ENDPOINT);
	    BosClient client = new BosClient(config);
	    return client;
	}
	
	public static void putObject(String bucketName,File file,String fileName) throws IOException{
	
	    BosClient client = getClient();
	    // 获取数据流
	    InputStream content = new FileInputStream(file);
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		
		// 必须设置ContentLength
//		meta.setContentLength(file.length());
//		meta.setContentEncoding("UTF-8");
		
		// 上传Object.
		String objectKey=fileName;
		PutObjectResponse result = client.putObject(bucketName, objectKey, content, meta);
		System.out.println(objectKey);
		// 打印ETag
		System.out.println(result.getETag());
		content.close();
	}
	
	
	public static void putObject(String bucketName,InputStream inputStream,String fileName) throws FileNotFoundException{
	
		// 初始化OSSClient
		BosClient client = getClient();
		// 获取指定文件的输入流
	//	File file = new File(fileRealPath + fileName);
	//	InputStream content = new FileInputStream(file);
		InputStream content = inputStream;
		
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		
		// 必须设置ContentLength
	//	meta.setContentLength(file.length());
//		meta.setContentEncoding("UTF-8");
		// 上传Object.
		String key=fileName;
		PutObjectResponse result = client.putObject(bucketName, key, content, meta);
		System.out.println(key);
		// 打印ETag
		System.out.println(result.getETag());
	}
	
	
	public static InputStream getObject(String bucketName,String fileName){
		// 初始化OSSClient
		BosClient client = getClient();
		//client.getObject(new GetObjectRequest(bucketName, key),new File(filename));
		BosObject bosObject=client.getObject(bucketName, fileName);
		
		InputStream inputStream=bosObject.getObjectContent();
		
		 // 获取Object，返回结果为BosObject对象
		return inputStream;
	}
	
	
	public static List<BosObjectSummary> getObjectList(String bucketName,String prefix,int endIndex,int maxKeys){
		

		System.out.println(prefix);
		// 初始化OSSClient
		BosClient client = getClient();

		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

		// 递归列出目录下的所有文件
		listObjectsRequest.setPrefix(prefix);
		listObjectsRequest.setMaxKeys(maxKeys);

		
		
		// listObjectsRequest.setMarker("ueditor/jsp/upload/image/20150614/1434267568217077840.jpg");

		ListObjectsResponse listing = null;
		String nextMarker = null;

		// endIndex>maxKeys
		int pageNum = endIndex / maxKeys;
		for (int i = 0; i < pageNum; i++) {
			if (nextMarker != null) {
				listObjectsRequest.setMarker(nextMarker);
			}
			listing = client.listObjects(listObjectsRequest);
			nextMarker = listing.getNextMarker();
		}

		System.out.println("nextMarker:" + nextMarker);
		// 遍历所有Object
		List<BosObjectSummary> objectSummarys = listing.getContents();
		
		return objectSummarys;
		// 遍历所有CommonPrefix
		/*
		 * System.out.println("\nCommonPrefixs:"); for (String commonPrefix :
		 * listing.getCommonPrefixes()) { System.out.println(commonPrefix); }
		 */
	}
	
	
	public static void main(String[] args) {
		
//		 BaiduBOS.getObjectList(BaiduBOS.UPLOADFILE_BACKET, "ueditor/jsp/upload/image/",4,2);
		 
		BaiduBOS.getObject(DiaowenProperty.WENJUANHTML_BACKET, "index.html");
	}
}

