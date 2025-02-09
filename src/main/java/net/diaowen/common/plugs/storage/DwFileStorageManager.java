package net.diaowen.common.plugs.storage;

import net.diaowen.common.plugs.storage.service.aliyun.AliYunFileStoreServiceImpl;
import net.diaowen.common.plugs.storage.service.local.LocalFileStoreServiceImpl;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

@Service
public class DwFileStorageManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LocalFileStoreServiceImpl localFileStoreService;
    @Autowired
    private AliYunFileStoreServiceImpl yunFileStoreService;


    //保存创建文件
    public String saveFile(MultipartFile multipartFile,String filePath,String fileName){
        String savePath = DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+filePath;
        boolean isPub = true;
        if(savePath.indexOf("/webin")>=0) isPub=false;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            yunFileStoreService.saveFile(multipartFile,savePath,fileName,isPub);
        }else{
            //local
            localFileStoreService.saveFile(multipartFile,savePath,fileName,isPub);
        }
        return savePath+fileName;
    }

    public String saveFile(File file,String filePath,String fileName){
        String savePath = DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+filePath;
        boolean isPub = true;
        if(savePath.indexOf("/webin")>=0) isPub=false;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            yunFileStoreService.saveFile(file,savePath,fileName,isPub);
        }else{
            //local
            localFileStoreService.saveFile(file,savePath,fileName,isPub);
        }
        return savePath+fileName;
    }

    //保存创建文件
    public String saveInputStreamFile(InputStream inputStream,String filePath){
        String savePath = DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+filePath;
        boolean isPub = true;
        if(savePath.indexOf("/webin")>=0) isPub=false;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            yunFileStoreService.saveInputStreamFile(inputStream,savePath,isPub);
        }else{
            //local
            localFileStoreService.saveInputStreamFile(inputStream,savePath,isPub);
        }
        return savePath;
    }

    public String saveBinaryFile(byte[] data, String filePath) {
        String savePath = DWSurveyConfig.DWSURVEY_FILE_STORAGE_PREFIX+filePath;
        boolean isPub = true;
        if(savePath.indexOf("/webin")>=0) isPub=false;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            yunFileStoreService.saveBinaryFile(data,savePath, isPub);
        }else{
            //local
            localFileStoreService.saveBinaryFile(data,savePath, isPub);
        }
        return savePath;
    }

    public void copyFile2Local(String fromPath,String toPath) {
        String content = null;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            yunFileStoreService.copyFile2Local(fromPath,toPath);
        }else{
            //local
            localFileStoreService.copyFile2Local(fromPath,toPath);
        }
    }

    public String getFileWebPath(String filePath) {
        String content = null;
        if("yun".equals(DWSurveyConfig.DWSURVEY_FILE_STORAGE_TYPE)){
            //yun
            content = yunFileStoreService.getFileWebPath(filePath);
        }else{
            //local
            content = localFileStoreService.getFileWebPath(filePath);
        }
        content+="?dwTime="+(new Date().getTime());
        logger.info("content {}",content);
        return content;
    }

    //删除文件

    //判断是否有文件


}
