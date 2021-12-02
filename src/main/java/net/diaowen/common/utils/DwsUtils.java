package net.diaowen.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keyuan on 2019/8/5.
 */
public class DwsUtils {

    public static String BASEURL_DEFAULT = "http://www.surveyform.cn";

    public static String baseUrl(HttpServletRequest request){
        String baseUrl = "";
        baseUrl = request.getScheme() +"://" + request.getServerName()
                + (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" +request.getServerPort())
                + request.getContextPath();
        return baseUrl;
    }


    public static Date str2Date(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadFile(HttpServletResponse response, String fileName, String downFilePath) throws IOException {
        downFilePath = downFilePath.replace("/", File.separator);
        downFilePath = downFilePath.replace("\\", File.separator);
        response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        FileInputStream in = new FileInputStream(downFilePath);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len=in.read(buffer))>0){
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }


}
