package net.diaowen.dwsurvey.controller;

import net.diaowen.common.base.service.AccountManager;
import net.diaowen.common.plugs.file.FileMagic;
import net.diaowen.common.plugs.file.FileMagicUtils;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.common.UpFileResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/up")
public class UploadController {

    @Autowired
    private AccountManager accountManager;
	/**
	 * 上传文件数据，安全存储
     *
	 */
    @RequestMapping("/up-file.do")
    @ResponseBody
    public HttpResult uploadFile(HttpServletRequest request,HttpServletResponse response) {
        String savePath = File.separator+"file" + File.separator + "images" + File.separator;
        return proUpfile((MultipartHttpServletRequest) request, savePath);
    }


    @RequestMapping("/up-file-wb.do")
    @ResponseBody
    public HttpResult uploadFileWb(HttpServletRequest request,HttpServletResponse response) {
//        String savePath = File.separator+"WEB-INF"+File.separator+"upload" + File.separator;
        String savePath = File.separator+"webin"+File.separator+"upload" + File.separator;
        return proUpfile((MultipartHttpServletRequest) request, savePath);
    }


    private HttpResult proUpfile(MultipartHttpServletRequest request, String savePath) {
        HttpResult httpResult = HttpResult.FAILURE();
        MultipartHttpServletRequest multiRequest = request;
//        String rootPath = multiRequest.getSession().getServletContext().getRealPath("/");
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;

        savePath = savePath + RandomUtils.randomStr(8,10) + File.separator;
        File dirFile = new File(rootPath+savePath);
        if  (!dirFile .exists()  && !dirFile .isDirectory()) {
            dirFile.mkdirs();
        }

        List<UpFileResult> upFileResults = new ArrayList<UpFileResult>();
        //创建一个通用的多部分解析器
        try{

            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//            MultipartHttpServletRequest multiRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());

            //CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(shiroRequest.getSession().getServletContext());
            //判断 request 是否有文件上传,即多部分请求

            System.setProperty("sun.jnu.encoding","utf-8");
            //转换成多部分request
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){

                    FileMagic fileMagic = FileMagic.valueOf(file);
                    if(!FileMagicUtils.isUserUpFileType(fileMagic)) {
                        return HttpResult.FAILURE("不支持类型",fileMagic);
                    }

                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        //重命名上传后的文件名
                        String fileName = file.getOriginalFilename();
                        String ext = fileName.substring(fileName.lastIndexOf("."));
                        String newFileName = RandomUtils.randomStr(8,10)+ext;
                        //定义上传路径
//                        String path = savePath + fileName;
                        String path = savePath + newFileName;
                        File localFile = new File(rootPath+path);
                        file.transferTo(localFile);
//                        files.put(fileName, path);
                        UpFileResult upFileResult = new UpFileResult();
                        upFileResult.setFilename(fileName);
                        upFileResult.setLocation(path);
                        upFileResults.add(upFileResult);
                    }
                }
                //记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
            }

            if(upFileResults.size()<=0){
                httpResult = HttpResult.FAILURE_MSG("未收到上传文件");
            }else{
                httpResult = HttpResult.SUCCESS(upFileResults);
            }

        }catch (Exception e){
//            sb.append("{\"success\":\"false\",\"error\":\"上传失败\"}");
            e.printStackTrace();
            httpResult = HttpResult.EXCEPTION(e.getMessage());
        }
        return httpResult;
    }


    /**
     * 根据类型判断，该后缀是否在白名单中。 允许上传的格式为白名单
     *
     * @param suffix
     * @param type
     * @return
     */
    private static boolean whiteList(String suffix, String type) {
        // 默认为黑名单
        boolean i = false;
        if (StringUtils.isEmpty(type)) {
            // 没有上传类型
            i = whiteList(suffix);
        } else if (!StringUtils.isEmpty(type)) {
            // 有上传类型
            if ("image".equals(type)) {
                i = imageWhiteList(suffix);
            }
        }
        return i;
    }


    /**
     * 判断文件后缀是否满足条件
     */
    private static boolean whiteList(String suffix) {
        // 白名单 允许出现的文件后缀
        List<String> whiteList = Arrays.asList("JAR");
        if (whiteList.contains(suffix.toUpperCase())) {
            // 白名单中存在
            return true;
        }
        // 白名单中不存在
        return false;
    }


    /**
     * 图片后缀 白名单
     * @param suffix
     * @return
     */
    static boolean imageWhiteList(String suffix) {
        List<String> list = Arrays.asList("JPG", "PNG");
        if (!StringUtils.isEmpty(suffix) && list.contains(suffix.toUpperCase())) {
            return true;
        }
        return false;
    }




}
