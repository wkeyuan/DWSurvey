package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

import java.io.*;

import net.diaowen.common.plugs.file.FileMagicUtils;
import org.apache.commons.io.FileUtils;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, String path) {

		if(!FileMagicUtils.isUserUpFileType(data,path.substring(path.lastIndexOf(".")))){
			return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
		}

		File file = new File(path);

		State state = valid(file);

		if (!state.isSuccess()) {
			return state;
		}

		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bos.write(data);
			bos.flush();
			bos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new BaseState(false, AppInfo.IO_ERROR);
		}

		state = new BaseState(true, file.getAbsolutePath());
		state.putInfo( "size", data.length );
		state.putInfo( "title", file.getName() );
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		BaseState validateState = isUserUpFileType(is,path.substring(path.lastIndexOf(".")));
		if(!validateState.isSuccess()) return validateState;
		State state = new BaseState(false, AppInfo.IO_ERROR);
		File tmpFile = validateState.getTmpFile();
		if(tmpFile!=null){
			state = saveTmpFile(tmpFile, path);
			deleteTmpFile(tmpFile);
			return state;
		}
		return state;
	}

	public static State saveFileByInputStream(InputStream is, String path) {
		BaseState validateState = isUserUpFileType(is,path.substring(path.lastIndexOf(".")));
		if(!validateState.isSuccess()) return validateState;
		State state = new BaseState(false, AppInfo.IO_ERROR);
		File tmpFile = validateState.getTmpFile();
		if(tmpFile!=null){
			state = saveTmpFile(tmpFile, path);
			deleteTmpFile(tmpFile);
			return state;
		}
		return state;
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

	public static BaseState isUserUpFileType(InputStream is,String fileSuffix) {
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
			bis.close();
			bos.flush();
			bos.close();
			if(!FileMagicUtils.isUserUpFileType(tmpFile,fileSuffix)){
				tmpFile.delete();
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
//			tmpFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseState(false, AppInfo.IO_ERROR);
		}
		return new BaseState(true, AppInfo.SUCCESS, tmpFile);
	}

	private static void deleteTmpFile(File tmpFile) {
		try{
			tmpFile.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
