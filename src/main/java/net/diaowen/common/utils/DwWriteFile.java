package net.diaowen.common.utils;

import net.diaowen.dwsurvey.config.DWSurveyConfig;

import java.io.*;

public class DwWriteFile {

	/**
	 * OS写到文件
	 * @param fileName
	 * @param fileRealPath
	 * @param os
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static File writeOS(String fileName, String fileRealPath, final ByteArrayOutputStream os) throws IOException,
			FileNotFoundException {
		fileRealPath = fileRealPath.substring(fileRealPath.lastIndexOf("/wjHtml")+1);
		fileRealPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH+fileRealPath;
		File file = new File(fileRealPath);
		if (!file.isDirectory() || !file.exists()) {
			file.mkdirs();
		}
		File newFile = new File(fileRealPath + fileName);
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(newFile);
		os.writeTo(fos);
		fos.close();
		return newFile;
	}

}
