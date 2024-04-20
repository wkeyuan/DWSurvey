package net.diaowen.common.plugs.storage.service.aliyun;

import net.diaowen.common.plugs.storage.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

@Service
public class AliYunFileStoreServiceImpl implements FileStorageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void saveFile(MultipartFile multipartFile,String savePath, String fileName, boolean isPub) {
        logger.info("AliYun save file multipartFile {} {}",savePath,fileName);
//        multipartFile.getInputStream()
        try {
            OSSAliyun.putObject(multipartFile.getInputStream(),savePath+fileName, isPub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile(File file, String savePath, String fileName, boolean isPub) {
        logger.info("AliYun save file multipartFile {} {}",savePath,fileName);
        try {
            OSSAliyun.putObject(file,savePath+fileName, isPub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveInputStreamFile(InputStream inputStream, String filePath, boolean isPub) {
        logger.info("AliYun save file inputStream {}",filePath);
        try {
            OSSAliyun.putObject(inputStream, filePath, isPub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveBinaryFile(byte[] data, String filePath, boolean isPub) {
        logger.info("AliYun save file saveBinaryFile {}",filePath);
        try {
            OSSAliyun.putObject(data,filePath,isPub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyFile2Local(String fromPath,String toPath) {
        logger.info("copeFile {} to {}",fromPath,toPath);
        //下载到本地
        OSSAliyun.downloadFile(fromPath,toPath);
    }

    public String getFileWebPath(String filePath) {
        URL url = OSSAliyun.getObjectUrl(filePath);
        if (url!=null) return url.toString();
        return null;
    }
}
