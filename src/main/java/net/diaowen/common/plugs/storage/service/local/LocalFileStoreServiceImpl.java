package net.diaowen.common.plugs.storage.service.local;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;
import net.diaowen.common.plugs.storage.service.FileStorageService;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Service
public class LocalFileStoreServiceImpl implements FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void saveFile(MultipartFile multipartFile, String savePath, String fileName,boolean isPub) {
        String dirPath = mkdirs(savePath, isPub);
        logger.info("local save file {} {}",dirPath,fileName);
        if(dirPath!=null){
            File localFile = new File(dirPath+fileName);
            try {
                multipartFile.transferTo(localFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile(File tmpFile, String savePath, String fileName,boolean isPub) {
        String dirPath = mkdirs(savePath, isPub);
        logger.info("local save file {} {}",dirPath,fileName);
        if(dirPath!=null){
            File localFile = new File(dirPath+fileName);
            try {
                FileUtils.moveFile(tmpFile, localFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveInputStreamFile(InputStream inputStream,String filePath,boolean isPub) {
        String dirFilePath = mkFileParentDirs(filePath, isPub);
        logger.info("saveInputStreamFile local save file {}",dirFilePath);
        if(dirFilePath!=null){
            File localFile = new File(dirFilePath);
            byte[] dataBuf = new byte[ 2048 ];
            BufferedInputStream bis = new BufferedInputStream(inputStream, StorageManager.BUFFER_SIZE);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile), StorageManager.BUFFER_SIZE);
                int count = 0;
                while ((count = bis.read(dataBuf)) != -1) bos.write(dataBuf, 0, count);
                bis.close();
                bos.flush();
                bos.close();
            } catch (IOException ioe) {
                logger.error(ioe.getMessage());
            }
        }
    }

    @Override
    public void saveBinaryFile(byte[] data, String filePath,boolean isPub) {
        String dirFilePath = mkFileParentDirs(filePath, isPub);
        logger.info("saveBinaryFile local save file {}",dirFilePath);
        if(dirFilePath!=null){
            File localFile = new File(dirFilePath);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));
                bos.write(data);
                bos.flush();
                bos.close();
            } catch (Exception ioe) {
                logger.error(ioe.getMessage());
            }
        }
    }

    private String mkdirs(String savePath, boolean isPub) {
        //如果私有，应该包含webin安全过滤目录，没有则不可以
        if(!isPub && savePath.indexOf("/webin")<0) {
            logger.error("保存失败 isPub = {}  时 savePath {} 应包含 /webin 安全目录", isPub, savePath);
            return null;
        }
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
        String dirPath = rootPath+ savePath;
        File dirFile = new File(rootPath+ savePath);
        if(!dirFile.exists() && !dirFile.isDirectory()) dirFile.mkdirs();
        return dirPath;
    }

    private String mkFileParentDirs(String savePath, boolean isPub) {
        //如果私有，应该包含webin安全过滤目录，没有则不可以
        if(!isPub && savePath.indexOf("/webin")<0) {
            logger.error("保存失败 isPub = {}  时 savePath {} 应包含 /webin 安全目录", isPub, savePath);
            return null;
        }
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
        String dirPath = rootPath+ savePath;
        File dirFile = new File(rootPath+ savePath);
        if(!dirFile.exists() && !dirFile.getParentFile().exists()) {
            dirFile.getParentFile().mkdirs();
        }
        return dirPath;
    }

    private String getDir(String savePath) {
        //如果私有，应该包含webin安全过滤目录，没有则不可以
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
        String dirPath = rootPath+ savePath;
        return dirPath;
    }

    //复制一个文件到指定位置
    public void copyFile2Local(String fromPath,String toPath){
        String fromFilePath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH + fromPath;
        File fromFile = new File(fromFilePath);
        logger.info("fromfile {}",fromFilePath);
        if (fromFile.exists()) {
            File toFile = new File(toPath);
            try {
                FileUtil.copyFile(fromFile, toFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFile(String filePath) {
        File file = new File(getDir(filePath));
        try {
            logger.info("local read {}",filePath);
            return FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }

    public String getFileWebPath(String filePath) {
        String webPath = DWSurveyConfig.DWSURVEY_WEB_RESOURCE_URL + filePath;
        return webPath;
    }
}
