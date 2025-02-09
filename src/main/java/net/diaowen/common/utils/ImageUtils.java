package net.diaowen.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ImageUtils {

    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String imgBase64ByUrl(String imgURL) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "fail";//连接失败/链接失效/图片不存在
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while ((len = inStream.read(data)) != -1) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(outPut.toByteArray());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    //图片转化成base64字符串
    public static String image2Base64(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data);
        //返回Base64编码过的字节数组字符串
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }


    public static String generateImage(String imgStr, String imgPath, String imgName) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return null;
//        BASE64Decoder decoder = new BASE64Decoder();
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            //判断是base64字符串还是图片路径
            if(imgStr.substring(0,5).equals("data:")){
                //Base64解码
//                byte[] b = decoder.decodeBuffer(imgStr.substring(imgStr.indexOf(",") + 1));
                byte[] b = decoder.decode(imgStr.substring(imgStr.indexOf(",") + 1));
                for(int i=0;i<b.length;++i) {
                    if(b[i]<0) {
                        //调整异常数据
                        b[i]+=256;
                    }
                }
                //生成图片
                String imgFilePath = imgPath+File.separator+imgName;//新生成的图片
                OutputStream out = new FileOutputStream(imgFilePath);
                out.write(b);
                out.flush();
                out.close();
                return imgFilePath;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * base64字符串转化成图片
     * @param imgStr base64字符串
     * @param imageName 本地路径
     */
    /*
    public static boolean generateImage(String imgStr,String filePath,String imageName) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = filePath+File.separator+imageName;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }

//            InputStream inputStream = new BufferedInputStream(new FileInputStream(imgFilePath));
//        InputStream inputStream = new FileInputStream(imgFilePath);

    }

     */

}
