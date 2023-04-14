package net.diaowen.dwsurvey.controller;

import net.diaowen.common.plugs.file.FileMagic;
import net.diaowen.common.plugs.file.FileMagicUtils;
import net.diaowen.common.plugs.file.TikaFileUtils;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.utils.RandomUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.common.FileMeta;
import net.diaowen.dwsurvey.common.UpFileResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/dwsurvey/up")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 上传文件数据，安全存储
     * /WebRoot/WEB-INF/upfile
     */
    @RequestMapping("/up-file.do")
    @ResponseBody
    public HttpResult uploadFile(HttpServletRequest request,HttpServletResponse response) {
        String savePath = File.separator+"file" + File.separator + "images"+ File.separator;

        return proUpfile((MultipartHttpServletRequest) request, savePath);
    }


    @RequestMapping("/up-file-wb.do")
    @ResponseBody
    public HttpResult uploadFileWb(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String savePath = File.separator+"webin"+File.separator+"upload" + File.separator ;
        HttpResult result = proUpfile((MultipartHttpServletRequest) request, savePath);
        return result;
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

                    FileMagic fileMagic = FileMagicUtils.getFileMagic(file.getInputStream(),file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                    if(!FileMagicUtils.isUserUpFileMagic(fileMagic)) {
                        logger.warn("不支持类型 {} {}", fileMagic, file.getOriginalFilename());
                        return HttpResult.FAILURE_MSG("不支持类型或类型不一致，实际类型为"+ TikaFileUtils.getMimeType(file.getInputStream(),file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))));
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
     * JUQERY UPLOAD file
     * 调用到的地方：问卷设计中图片单选题，图片多选题。
     */
    /***************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value="/ajax/upload.do",method = {RequestMethod.POST})
    public @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        LinkedList<FileMeta> files = new LinkedList<FileMeta>();
        FileMeta fileMeta = null;
        //创建目录
//        String rootPath = request.getServletContext().getRealPath("/");
        String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
//        String savePath = File.separator+"WEB-INF"+File.separator+"upload" + File.separator;

        String savePath = File.separator+"file"+File.separator+"upload" + File.separator ;
        File dirFile = new File(rootPath + savePath);
        if (!dirFile.exists() && !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }

        try{
            //1. build an iterator
            Iterator<String> itr =  request.getFileNames();
            MultipartFile mpf = null;

            //2. get each file
            while(itr.hasNext()){

                //2.1 get next MultipartFile
                mpf = request.getFile(itr.next());

                FileMagic fileMagic = FileMagicUtils.getFileMagic(mpf.getInputStream(),mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf(".")));
                if(!FileMagicUtils.isUserUpFileMagic(fileMagic)) {
                    logger.warn("不支持类型 {} {}", fileMagic, mpf.getOriginalFilename());
//                    return HttpResult.FAILURE_MSG("不支持类型或类型不一致，实际类型为"+ TikaFileUtils.getMimeType(mpf.getInputStream(),mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."))));
                    return files;
                }

                //2.2 if files > 10 remove the first from the list
                if(files.size() >= 10)
                    files.pop();

                String fileName = mpf.getOriginalFilename();
                fileName = fileName.toLowerCase();//6S兼容
                String newFileName = rendomFileName(mpf);

                //2.3 create new fileMeta
                fileMeta = new FileMeta();
                fileMeta.setFileName(fileName);
                fileMeta.setNewFileName(newFileName);
                fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
                fileMeta.setFileType(mpf.getContentType());

                try {
//                fileMeta.setBytes(mpf.getBytes());
                    String filePath = savePath + newFileName;
                    fileMeta.setFilePath(filePath);
                    // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(rootPath+filePath));

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //2.4 add to files
                files.add(fileMeta);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        return files;
    }


    public String rendomFileName(MultipartFile file){
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9' };
        StringBuffer fileName = new StringBuffer("");
        Random r = new Random();
        int pos = -1;
        for (int i = 0; i < 15; i++) {
            pos = Math.abs(r.nextInt(36));
            fileName.append(str[pos]);
        }
        String extName = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf(".") + 1);
        return fileName.toString().trim() + "." + extName;
    }




}
