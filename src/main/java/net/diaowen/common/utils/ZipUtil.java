package net.diaowen.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
     private static final Logger log = LoggerFactory.getLogger(ZipUtil.class);

    private ZipUtil() {
    }
    /**
     * 创建ZIP文件
     * @param sourcePath 文件或文件夹路径
     * @param zipPath 生成的zip文件存在路径（包括文件名）
     * @param isDrop  是否删除原文件:true删除、false不删除
     */
    public static void createZip(String sourcePath, String zipPath,Boolean isDrop) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            //createXmlFile(sourcePath,"293.xml");
            writeZip(new File(sourcePath), "", zos,isDrop);
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败",e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败",e);
            }

        }
    }
    /**
     * 清空文件和文件目录
     *
     * @param f
     */
    public static void clean(File f) throws Exception {
        String cs[] = f.list();
        if (cs == null || cs.length <= 0) {
            boolean isDelete = f.delete();
            if (!isDelete) {
                throw new Exception(f.getName() + "文件删除失败！");
            }
        } else {
            for (int i = 0; i < cs.length; i++) {
                String cn = cs[i];
                String cp = f.getPath() + File.separator + cn;
                File f2 = new File(cp);
                if (f2.exists() && f2.isFile()) {
                    boolean isDelete = f2.delete();
                    if (!isDelete) {
                        throw new Exception(f2.getName() + "文件删除失败！");
                    }
                } else if (f2.exists() && f2.isDirectory()) {
                    clean(f2);
                }
            }
            boolean isDelete = f.delete();
            if (!isDelete) {
                throw new Exception(f.getName() + "文件删除失败！");
            }
        }
    }
    private static void writeZip(File file, String parentPath, ZipOutputStream zos,Boolean isDrop) {
        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                if(files.length != 0)
                {
                    for(File f:files){
                        writeZip(f, parentPath, zos,isDrop);
                    }
                }
                else
                {       //空目录则创建当前目录
                        try {
                            zos.putNextEntry(new ZipEntry(parentPath));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                    }
                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败",e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败",e);
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                        if(isDrop){
                             clean(file);
                        }
                    }catch(IOException e){
                        log.error("创建ZIP文件失败",e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        ZipUtil.createZip("/Users/keyuan/Documents/GIT/my-gitlab/dw/dwsurvey_b1/dwsurvey/target/dwsurvey/file/402880e864e15c150164e3917b930000", "/Users/keyuan/Documents/GIT/my-gitlab/dw/dwsurvey_b1/dwsurvey/target/dwsurvey/file/402880e864e15c150164e3917b930000.zip",false);
    }
}
