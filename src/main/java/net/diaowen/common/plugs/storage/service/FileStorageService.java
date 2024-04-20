package net.diaowen.common.plugs.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface FileStorageService {

    /**
     * 保存MultipartFile类型文件
     * @param multipartFile
     * @param savePath 文件路径
     * @param fileName 文件名称
     * @param isPub 是否公开
     */
    void saveFile(MultipartFile multipartFile, String savePath, String fileName, boolean isPub);

    void saveFile(File file, String savePath, String fileName, boolean isPub);

    /**
     * 保存InputStream为文件
     * @param inputStream
     * @param filePath 文件完整地址
     * @param isPub 是否公开
     */
    public void saveInputStreamFile(InputStream inputStream, String filePath, boolean isPub);

    /**
     * 保存 byte[] 为文件
     * @param data
     * @param filePath 文件完整地址
     * @param isPub 是否公开
     */
    public void saveBinaryFile(byte[] data, String filePath, boolean isPub);

    /**
     * 复制一个文件到指定地址
     * @param fromPath
     * @param toPath
     */
    public void copyFile2Local(String fromPath,String toPath);

    /**
     * 获取一个文件的web访问地址
     * @param filePath
     * @return
     */
    public String getFileWebPath(String filePath);

}
