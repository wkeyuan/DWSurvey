package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.key.common.plugs.aliyun.AliyunOSS;
import com.key.common.plugs.baiduyun.BaiduBOS;
import com.key.common.utils.DiaowenProperty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;
	
	public StorageManager() {
		
	}
	
	public static State saveBinaryFile(byte[] data, String rootPath, String path) {
		
		if("local".equals(DiaowenProperty.DWSTORAGETYPE)){
//			File file = new File(path);
			File file = new File(rootPath+path);
			State state = valid(file);
			if (!state.isSuccess()) {
				return state;
			}
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bos.write(data);
				bos.flush();
				bos.close();
				
			} catch (IOException ioe) {
				return new BaseState(false, AppInfo.IO_ERROR);
			}

			state = new BaseState(true, file.getAbsolutePath());
			state.putInfo( "size", data.length );
			state.putInfo( "title", file.getName() );
			return state;
		}else{
			return saveBinaryFileToYun(data, path);
		}
		
	}
	

	public static State saveFileByInputStream(InputStream is, String rootPath, String path,
			long maxSize) {
		if("local".equals(DiaowenProperty.DWSTORAGETYPE)){
			State state = null;

			File tmpFile = getTmpFile();
			byte[] dataBuf = new byte[ 2048 ];
			BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

				int count = 0;
				while ((count = bis.read(dataBuf)) != -1) {
					bos.write(dataBuf, 0, count);
				}
				bos.flush();
				bos.close();

				if (tmpFile.length() > maxSize) {
					tmpFile.delete();
					return new BaseState(false, AppInfo.MAX_SIZE);
				}

				state = saveTmpFile(tmpFile, rootPath+path);

				if (!state.isSuccess()) {
					tmpFile.delete();
				}

				return state;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new BaseState(false, AppInfo.IO_ERROR);	
		}else{
			return saveFileByInputStreamToYun(is, path);
		}
	}

	public static State saveFileByInputStream(InputStream is,String rootPath, String path) {
		if("local".equals(DiaowenProperty.DWSTORAGETYPE)){
			State state = null;

			File tmpFile = getTmpFile();

			byte[] dataBuf = new byte[ 2048 ];
			BufferedInputStream bis = new BufferedInputStream(is, StorageManager.BUFFER_SIZE);

			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(tmpFile), StorageManager.BUFFER_SIZE);

				int count = 0;
				while ((count = bis.read(dataBuf)) != -1) {
					bos.write(dataBuf, 0, count);
				}
				bos.flush();
				bos.close();

				state = saveTmpFile(tmpFile, rootPath+path);

				if (!state.isSuccess()) {
					tmpFile.delete();
				}
				return state;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new BaseState(false, AppInfo.IO_ERROR);
		}else{
			return saveFileByInputStreamToYun(is, path);
		}
	
	}
	

	private static File getTmpFile() {
		File tmpDir = FileUtils.getTempDirectory();
		String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
		return new File(tmpDir, tmpFileName);
	}

	private static State saveTmpFile(File tmpFile, String path) {
		State state = null;
		File targetFile = new File(path);

		if (targetFile.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}
		try {
			FileUtils.moveFile(tmpFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true);
		state.putInfo( "size", targetFile.length() );
		state.putInfo( "title", targetFile.getName() );
		
		return state;
	}

	private static State valid(File file) {
		File parentPath = file.getParentFile();

		if ((!parentPath.exists()) && (!parentPath.mkdirs())) {
			return new BaseState(false, AppInfo.FAILED_CREATE_FILE);
		}

		if (!parentPath.canWrite()) {
			return new BaseState(false, AppInfo.PERMISSION_DENIED);
		}

		return new BaseState(true);
	}

	
	
	/**
	 * SaveToYun
	 */
	

	public static State saveBinaryFileToYun(byte[] data, String savePath) {
		State state = null;
		
		File tmpFile = getTmpFile();
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(tmpFile));
			bos.write(data);
			bos.flush();
			bos.close();
			
			
			if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE)){
				// 阿里云支持 将文件写入到aliyun oss
				AliyunOSS.putObject(DiaowenProperty.UPLOADFILE_BACKET, tmpFile, savePath);
				tmpFile.delete();	
			}else if("baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
				BaiduBOS.putObject(DiaowenProperty.UPLOADFILE_BACKET,tmpFile, savePath);
				tmpFile.delete();
			}
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, tmpFile.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", tmpFile.getName() );
		return state;
	}
	

	public static State saveFileByInputStreamToYun(InputStream inputStream,
			String savePath) {
		State state = null;
		
		savePath=savePath.substring(1);
		
		try {
//			AliyunOSS.pubObjects(AliyunOSS.UPLOADFILE_BACKET, inputStream, savePath);

			if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE)){
				// 阿里云支持 将文件写入到aliyun oss
				AliyunOSS.putObject(DiaowenProperty.UPLOADFILE_BACKET, inputStream, savePath);
			}else if("baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
				BaiduBOS.putObject(DiaowenProperty.UPLOADFILE_BACKET,inputStream, savePath);
			}
			
			state = new BaseState(true);
			
			//state.putInfo( "size", inputStream.length() );
//			state.putInfo( "title", inputStream.getName() );
			return state;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	

}
