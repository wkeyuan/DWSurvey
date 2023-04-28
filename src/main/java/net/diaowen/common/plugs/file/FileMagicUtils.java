package net.diaowen.common.plugs.file;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileMagicUtils {

    //非登录用户能够上传的文件类型
    public static FileMagic[] anonUpFileType() {
        return new FileMagic[]{FileMagic.PNG,FileMagic.JPG,FileMagic.JPEG,FileMagic.GIF,
                FileMagic.TXT,FileMagic.PDF,
                FileMagic.XLSX,FileMagic.XLS,FileMagic.DOC,FileMagic.DOCX,FileMagic.PPT,FileMagic.PPTX,
                FileMagic.ZIP,FileMagic.RAR,FileMagic.Z7Z};
    }

    //登录用户能够上传的文件类型
    public static FileMagic[] userUpFileType() {
        return new FileMagic[]{FileMagic.PNG,FileMagic.JPG,FileMagic.JPEG,FileMagic.GIF,
                FileMagic.TXT,FileMagic.PDF,
                FileMagic.XLSX,FileMagic.XLS,FileMagic.DOC,FileMagic.DOCX,FileMagic.PPT,FileMagic.PPTX,
                FileMagic.ZIP,FileMagic.RAR,FileMagic.Z7Z};
    }

    //根据文件获取对应的文件类型
    public static FileMagic getFileMagic(File inp, String fileSuffix) throws Exception {
        FileMagic fileMagic = null;
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(inp);
            fileMagic = getFileMagic(fis,fileSuffix);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fis!=null) fis.close();
        }
        return fileMagic;
    }

    //切换到使用最新的tika验测
    public static FileMagic getFileMagic(byte[] bytes,String fileName) throws IOException{
        String mineType = TikaFileUtils.mimeType(bytes,fileName);
        if(mineType!=null){
            FileMagic[] fileMagics = FileMagic.values();
            int fileMagicLength = fileMagics.length;
            for(int i = 0; i < fileMagicLength; ++i) {
                FileMagic fm = fileMagics[i];
                String fileSuffix = fm.getFileSuffix().toLowerCase();
                if (fileSuffix.indexOf(mineType.toLowerCase())>=0) {
                    return fm;
                }
            }
        }
        return FileMagic.UNKNOWN;
    }

    //切换到使用最新的tika验测
    public static FileMagic getFileMagic(InputStream fis, String fileName) throws IOException{
        String mineType = TikaFileUtils.mimeType(fis,fileName);
        if(mineType!=null){
            FileMagic[] fileMagics = FileMagic.values();
            int fileMagicLength = fileMagics.length;
            for(int i = 0; i < fileMagicLength; ++i) {
                FileMagic fm = fileMagics[i];
                String fileSuffix = fm.getFileSuffix().toLowerCase();
                if (fileSuffix.indexOf(mineType.toLowerCase())>=0) {
                    return fm;
                }
            }
        }
        return FileMagic.UNKNOWN;
    }

    public static boolean isUserUpFileType(byte[] bytes, String suffix) {
        try {
            FileMagic fileMagic = getFileMagic(bytes,suffix);
            if (isUserUpFileMagic(fileMagic)) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserUpFileType(File file, String suffix) {
        try {
            FileMagic fileMagic = getFileMagic(file,suffix);
            if (isUserUpFileMagic(fileMagic)) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserUpFileType(InputStream inputStream, String suffix) {
        try {
            FileMagic fileMagic = getFileMagic(inputStream,suffix);
            if (isUserUpFileMagic(fileMagic)) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否在登录用户可上传白名单
     * @param fileMagic
     * @return
     */
    public static boolean isUserUpFileMagic(FileMagic fileMagic) {
        FileMagic[] fileMagics = userUpFileType();
        for (FileMagic magic:fileMagics) {
            if(magic == fileMagic){
                return true;
            }
        }
        return false;
    }

}
