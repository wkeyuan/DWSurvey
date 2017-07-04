package com.key.common.plugs.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.key.common.utils.DiaowenProperty;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

/**
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public class AliyunOSS {

	/*
	static String ACCESS_KEY_ID = "";
	static String SECRET_ACCESS_KEY = "";
	// 以杭州为例
	//static String endpoint = "http://oss-cn-shenzhen-internal.aliyuncs.com";
	static String ENDPOINT = "http://gz.bcebos.com";
	
	public static String  WENJUANHTML_BACKET="";
	public static String  UPLOADFILE_BACKET="";
	public static String  UPLOADFILE_JM_BACKET="";
	*/
	
	private static OSSClient getOSSClient() {
//		OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID,SECRET_ACCESS_KEY);
		OSSClient client = new OSSClient(DiaowenProperty.ENDPOINT, DiaowenProperty.ACCESS_KEY_ID,
				DiaowenProperty.SECRET_ACCESS_KEY);
		return client;
	}

	public static void putObject(String bucketName, File file, String fileName)
			throws IOException {

		// 初始化OSSClient
		OSSClient client = getOSSClient();

		// 获取指定文件的输入流
		// File file = new File(fileRealPath + fileName);
		InputStream content = new FileInputStream(file);

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		// meta.setContentLength(file.length());
		meta.setContentEncoding("UTF-8");
		// 上传Object.
		String key = fileName;
		PutObjectResult result = client.putObject(bucketName, key, content,
				meta);
		content.close();
	}

	public static void putObject(String bucketName, InputStream inputStream,
			String fileName) throws FileNotFoundException {

		// 初始化OSSClient
		OSSClient client = getOSSClient();

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
		String key = fileName;
		PutObjectResult result = client.putObject(bucketName, key, content,
				meta);
	}

	public static InputStream getObject(String bucketName, String fileName) {
		// 初始化OSSClient
		OSSClient client = getOSSClient();

		// client.getObject(new GetObjectRequest(bucketName, key),new
		// File(filename));
		OSSObject ossObject = client.getObject(bucketName, fileName);
		InputStream inputStream = ossObject.getObjectContent();

		return inputStream;
	}

	public static List<OSSObjectSummary> getObjectList(String bucketName,
			String prefix, int endIndex, int maxKeys) {
		// 初始化OSSClient
		OSSClient client = getOSSClient();

		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(
				bucketName);

		// 递归列出fun目录下的所有文件
		listObjectsRequest.setPrefix(prefix);
		listObjectsRequest.setMaxKeys(maxKeys);
		// listObjectsRequest.setMarker("ueditor/jsp/upload/image/20150614/1434267568217077840.jpg");

		ObjectListing listing = null;
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

		// 遍历所有Object
		List<OSSObjectSummary> objectSummarys = listing.getObjectSummaries();

		return objectSummarys;
		// 遍历所有CommonPrefix
		/*
		 * System.out.println("\nCommonPrefixs:"); for (String commonPrefix :
		 * listing.getCommonPrefixes()) { System.out.println(commonPrefix); }
		 */
	}

	public static void main(String[] args) {

		List<OSSObjectSummary> objectSummaries = AliyunOSS.getObjectList(
				DiaowenProperty.UPLOADFILE_BACKET, "ueditor/jsp/upload/image/", 4, 2);
		System.out.println(objectSummaries.size());
		for (OSSObjectSummary ossObjectSummary : objectSummaries) {
			System.out.println(ossObjectSummary.getKey() + ":"
					+ ossObjectSummary.getSize());
		}
	}
}
