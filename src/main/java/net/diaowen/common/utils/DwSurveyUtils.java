package net.diaowen.common.utils;

import net.diaowen.dwsurvey.config.DWSurveyConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DwSurveyUtils {

    public static void writerJson(String jsonString, String savePath, String fileName) throws IOException {
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
        File dirFile = new File(rootPath+ savePath);
        if  (!dirFile.exists()  && !dirFile .isDirectory()) {
            dirFile.mkdirs();
        }
        File localFile = new File(rootPath+ savePath + fileName);
        if (!localFile.exists()) {
            localFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(localFile);
        fos.write(jsonString.getBytes());
        fos.close();
    }

}
