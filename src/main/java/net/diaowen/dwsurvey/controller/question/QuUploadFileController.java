package net.diaowen.dwsurvey.controller.question;

import net.diaowen.common.CheckType;
import net.diaowen.common.QuType;
import net.diaowen.common.plugs.page.Page;
import net.diaowen.dwsurvey.entity.AnUplodFile;
import net.diaowen.dwsurvey.entity.Question;
import net.diaowen.dwsurvey.entity.QuestionLogic;
import net.diaowen.dwsurvey.service.AnUploadFileManager;
import net.diaowen.dwsurvey.service.QuestionManager;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 填空题 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/api/dwsurvey/app/design/qu-upload-file")
public class QuUploadFileController {
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private AnUploadFileManager anUploadFileManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;

	@RequestMapping("/ajaxSave.do")
	public String ajaxSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			Question entity=ajaxBuildSaveOption(request);
			questionManager.save(entity);
			String resultJson=buildResultJson(entity);
			response.getWriter().write(resultJson);
			//返回各部分ID
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("error");
		}
		return null;
	}

	private Question ajaxBuildSaveOption(HttpServletRequest request) throws UnsupportedEncodingException {
		String quId=request.getParameter("quId");
		String belongId=request.getParameter("belongId");
		String quTitle=request.getParameter("quTitle");
		String orderById=request.getParameter("orderById");
		String tag=request.getParameter("tag");
		//isRequired 是否必选
		String isRequired=request.getParameter("isRequired");

		String answerInputWidth=request.getParameter("answerInputWidth");
		String answerInputRow=request.getParameter("answerInputRow");

		String contactsAttr=request.getParameter("contactsAttr");
		String contactsField=request.getParameter("contactsField");

		String checkType=request.getParameter("checkType");
		String paramInt01=request.getParameter("paramInt01");//上传文件类型
		String paramInt02=request.getParameter("paramInt02");

		/** 某一类型题目特有的 **/
		//hv 1水平显示 2垂直显示
		String hv=request.getParameter("hv");
		//randOrder 选项随机排列
		String randOrder=request.getParameter("randOrder");
		String cellCount=request.getParameter("cellCount");

		if("".equals(quId)){
			quId=null;
		}
		Question entity=questionManager.getModel(quId);
		entity.setBelongId(belongId);
		if(quTitle!=null){
			quTitle=URLDecoder.decode(quTitle,"utf-8");
			entity.setQuTitle(quTitle);
		}

		String quNote = request.getParameter("quNote");
		if(quNote!=null){
			quNote=URLDecoder.decode(quNote,"utf-8");
			entity.setQuNote(quNote);
		}

		entity.setOrderById(Integer.parseInt(orderById));
		entity.setTag(Integer.parseInt(tag));
		entity.setQuType(QuType.UPLOADFILE);
		//参数
		isRequired=(isRequired==null || "".equals(isRequired))?"0":isRequired;
		hv=(hv==null || "".equals(hv))?"0":hv;
		randOrder=(randOrder==null || "".equals(randOrder))?"0":randOrder;
		cellCount=(cellCount==null || "".equals(cellCount))?"0":cellCount;
		paramInt01=(paramInt01==null || "".equals(paramInt01))?"0":paramInt01;
		paramInt02=(paramInt02==null || "".equals(paramInt02))?"10":paramInt02;


		contactsAttr=(contactsAttr==null || "".equals(contactsAttr))?"0":contactsAttr;
		entity.setContactsAttr(Integer.parseInt(contactsAttr));
		entity.setContactsField(contactsField);

		answerInputWidth=(answerInputWidth==null || "".equals(answerInputWidth))?"300":answerInputWidth;
		answerInputRow=(answerInputRow==null || "".equals(answerInputRow))?"1":answerInputRow;
		entity.setAnswerInputWidth(Integer.parseInt(answerInputWidth));
		entity.setAnswerInputRow(Integer.parseInt(answerInputRow));

		entity.setIsRequired(Integer.parseInt(isRequired));
		entity.setHv(Integer.parseInt(hv));
		entity.setRandOrder(Integer.parseInt(randOrder));
		entity.setCellCount(Integer.parseInt(cellCount));
		entity.setParamInt01(Integer.parseInt(paramInt01));
		entity.setParamInt02(Integer.parseInt(paramInt02));

		checkType=(checkType==null || "".equals(checkType))?"NO":checkType;
		entity.setCheckType(CheckType.valueOf(checkType));

		//逻辑选项设置
		Map<String, Object> quLogicIdMap=WebUtils.getParametersStartingWith(request, "quLogicId_");
		List<QuestionLogic> quLogics=new ArrayList<QuestionLogic>();
		for (String key : quLogicIdMap.keySet()) {
			String cgQuItemId=request.getParameter("cgQuItemId_"+key);
			String skQuId=request.getParameter("skQuId_"+key);
			String visibility=request.getParameter("visibility_"+key);
			String logicType=request.getParameter("logicType_"+key);
			Object quLogicId=quLogicIdMap.get(key);
			String quLogicIdValue=(quLogicId!=null)?quLogicId.toString():"";

			QuestionLogic quLogic=new QuestionLogic();
			if("".equals(quLogic)){
				quLogic=null;
			}
			quLogic.setId(quLogicIdValue);
			quLogic.setCgQuItemId(cgQuItemId);
			quLogic.setSkQuId(skQuId);
			quLogic.setVisibility(Integer.parseInt(visibility));
			quLogic.setTitle(key);
			quLogic.setLogicType(logicType);
			quLogics.add(quLogic);
		}
		entity.setQuestionLogics(quLogics);

		return entity;
	}

	public static String buildResultJson(Question entity){
		//{id:'null',quItems:[{id:'null',title:'null'},{id:'null',title:'null'}]}
		StringBuffer strBuf=new StringBuffer();
		strBuf.append("{id:'").append(entity.getId());
		strBuf.append("',orderById:");
		strBuf.append(entity.getOrderById());

		strBuf.append(",quLogics:[");
		List<QuestionLogic> questionLogics=entity.getQuestionLogics();
		if(questionLogics!=null){
			for (QuestionLogic questionLogic : questionLogics) {
				strBuf.append("{id:'").append(questionLogic.getId());
				strBuf.append("',title:'").append(questionLogic.getTitle()).append("'},");
			}
		}
		int strLen=strBuf.length();
		if(strBuf.lastIndexOf(",")==(strLen-1)){
			strBuf.replace(strLen-1, strLen, "");
		}
		strBuf.append("]}");
		return strBuf.toString();
	}


	//取上传题结果
	@RequestMapping("/answers.do")
	public ModelAndView answers(Page<AnUplodFile> anPage, String quId, String surveyId) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		anPage.setPageSize(10000);
		Criterion cri1 = Restrictions.eq("quId",quId);
		anPage = anUploadFileManager.findPage(anPage, cri1);
		modelAndView.addObject("anPage",anPage);
		modelAndView.addObject("directory",surveyDirectoryManager.get(surveyId));
		modelAndView.addObject("surveyId",surveyId);
		modelAndView.setViewName("/diaowen-da/upload-files");
		return modelAndView;
	}

	@RequestMapping("/download.do")
	public String download(HttpServletRequest request,HttpServletResponse response,String id) throws Exception {
		String anuploadId = id;
		AnUplodFile anUplodFile = anUploadFileManager.getModel(anuploadId);
		//设置Content-Disposition
		//response.sendRedirect(request.getContextPath()+anUplodFile.getFilePath());
		String fileName =anUplodFile.getFileName();
		String rootPath = request.getServletContext().getRealPath("/");
		String filePath = anUplodFile.getFilePath().replaceAll("\\\\", File.separator);
		filePath = filePath.replaceAll("/", File.separator);
		File file = new File(rootPath+filePath);
		//如果文件不存在
		if(!file.exists()){
			request.setAttribute("message", "您要下载的资源已被删除！！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return null;
		}
		//处理文件名
		fileName=fileName.replaceAll("\\+","");
		fileName=anUplodFile.getRandomCode()+"-"+fileName;
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(file);
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
		out.flush();
		//关闭文件输入流
		in.close();
		//关闭输出流
		out.close();
		return null;
	}



	//批量打包下载 －－ //只打包当前页的
	/*
     * 批量下载另存为
     */
	@RequestMapping("/batDownload.do")
	public String batDownload(HttpServletRequest request,HttpServletResponse response,Page<AnUplodFile> anPage,String quId) throws IOException {
		Criterion cri1 = Restrictions.eq("quId",quId);
		anPage = anUploadFileManager.findPage(anPage, cri1);

		String tmpFileName = "ga_"+quId+".zip";
		byte[] buffer = new byte[1024];
		String rootPath = request.getServletContext().getRealPath("/upload/work/");
		File dir = new File(rootPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String strZipPath = request.getServletContext().getRealPath("/upload/work/"+tmpFileName);
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			List<AnUplodFile> result = anPage.getResult();
			// 下载的文件集合
			for (int i = 0; i < result.size(); i++) {
				AnUplodFile anUplodFile = result.get(i);
				FileInputStream fis = new FileInputStream(request.getServletContext().getRealPath(anUplodFile.getFilePath()));
				out.putNextEntry(new ZipEntry(anUplodFile.getRandomCode()+"-"+anUplodFile.getFileName()));
				//设置压缩文件内的字符编码，不然会变成乱码
//				out.setComment();
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
			//saveAs("work/"+tmpFileName, tmpFileName);

			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(tmpFileName, "UTF-8"));
			//读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(new File(strZipPath));
			//创建输出流
			OutputStream respOut = response.getOutputStream();
			//创建缓冲区
			byte respBuffer[] = new byte[1024];
			int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
				//输出缓冲区的内容到浏览器，实现文件下载
				respOut.write(buffer, 0, len);
			}
			respOut.flush();
			//关闭文件输入流
			in.close();
			//关闭输出流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("批量导出异常！");
		}
		return null;
	}

}
