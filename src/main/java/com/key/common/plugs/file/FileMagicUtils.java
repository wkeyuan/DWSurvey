package com.key.common.plugs.file;


import java.io.File;

public class FileMagicUtils {

    public static boolean isUserUpFileType(byte[] bytes) {
        try {
            FileMagic fileMagic = FileMagic.valueOf(bytes);
            FileMagic[] fileMagics = userUpFileType();
            for (FileMagic magic:fileMagics) {
                if(magic == fileMagic){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserUpFileType(File file) {
        try {
            FileMagic fileMagic = FileMagic.valueOf(file);
            FileMagic[] fileMagics = userUpFileType();
            for (FileMagic magic:fileMagics) {
                if(magic == fileMagic){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否在白名单
     * @param fileMagic
     * @return
     */
    public static boolean isUserUpFileType(FileMagic fileMagic){
        FileMagic[] fileMagics = userUpFileType();
        for (FileMagic magic:fileMagics) {
            if(magic == fileMagic){
                return true;
            }
        }
        return false;
    }

    //非登录用户能够上传的文件类型
    public static FileMagic[] anonUpFileType() {
        return new FileMagic[]{FileMagic.PNG,FileMagic.JPEG,FileMagic.GIF};
    }

    //登录用户能够上传的文件类型
    public static FileMagic[] userUpFileType() {
        return new FileMagic[]{FileMagic.PNG,FileMagic.JPEG,FileMagic.GIF,FileMagic.XLSX,FileMagic.XLS};
    }
}
