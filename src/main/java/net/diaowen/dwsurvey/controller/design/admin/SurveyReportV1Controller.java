package net.diaowen.dwsurvey.controller.design.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.utils.ImageUtils;
import net.diaowen.dwsurvey.config.DWSurveyConfig;
import net.diaowen.dwsurvey.entity.SurveyDirectory;
import net.diaowen.dwsurvey.service.SurveyDirectoryManager;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.Map;

/**
 * 分析报告 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping(value = {"/api/dwsurvey/app/survey/survey-report/v1"})
public class SurveyReportV1Controller {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected final static String DEFAULT_REPORT="default_report";
	protected final static String LINE_CHART="line_chart";
	protected final static String PIE_CHART="pie_chart";

	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;

	@RequestMapping("/exportReport2Word.do")
	@ResponseBody
	public HttpResult exportReport2Word(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> reqBody) {
		//生成word
		try {
//			logger.info("questions {}", JSON.toJSONString(reqBody));
			JSONObject reqData = JSON.parseObject(JSON.toJSONString(reqBody));

			String surveyId = reqData.getString("surveyId");
			String surveyName = reqData.getString("surveyName");
			SurveyDirectory survey = surveyDirectoryManager.getSurvey(surveyId);
			if (survey!=null) {
				surveyName = survey.getSurveyName();
				ApplicationHome applicationHome =new ApplicationHome();
				String applicationPath = applicationHome.getDir().getPath();
				String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
//			String rootPath = request.getSession().getServletContext().getRealPath("/");
				XWPFDocument docx = new XWPFDocument();
				XWPFParagraph titleParagraph = docx.createParagraph();
				//设置段落居中
				titleParagraph.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun titleParagraphRun = titleParagraph.createRun();
				titleParagraphRun.setText(surveyName);
				titleParagraphRun.setColor("000000");
				titleParagraphRun.setFontSize(20);
				titleParagraphRun.setBold(true); //加粗
				titleParagraphRun.setColor("000000");//设置颜色
				titleParagraphRun.setFontSize(25);    //字体大小
				//        titleFun.setFontFamily("");//设置字体
				titleParagraphRun.addBreak();    //换行

				//defaultReport
				Map<String,Object> quIds = WebUtils.getParametersStartingWith(request,"quId_");
				JSONArray reqQuestions = reqData.getJSONArray("questions");
				for(int i=0;i<reqQuestions.size();i++) {
					JSONObject reqQuest = reqQuestions.getJSONObject(i);
					String quId = reqQuest.getString("quId");
					String quTitle = reqQuest.getString("quTitle");
					String quTypeName = reqQuest.getString("quTypeName");
					JSONArray quStatOptions = reqQuest.getJSONArray("quStatOptions");
					String chartDataUrl = reqQuest.getString("quCharData");
					logger.info("quId {}, quTitle {}", quId, quTitle);
					logger.info("quStatOptions {}", quStatOptions);
				}

				for(int i=0;i<reqQuestions.size();i++) {

					JSONObject reqQuest = reqQuestions.getJSONObject(i);
					String quId = reqQuest.getString("quId");
					Integer quNum = reqQuest.getInteger("quNum");
					String quTitle = reqQuest.getString("quTitle");
					String quType = reqQuest.getString("quType");
					String quTypeName = reqQuest.getString("quTypeName");
					Integer quAnCount = reqQuest.getInteger("quAnCount");
					JSONArray quStatOptions = reqQuest.getJSONArray("quStatOptions");
					String quCharData = reqQuest.getString("quCharData");
					logger.info("quId {}, quTitle {}", quId, quTitle);
					logger.info("quStatOptions {}", quStatOptions);

					String exportBuildImg = rootPath+"/file/export";
					exportBuildImg = exportBuildImg.replace("/",File.separator);
					exportBuildImg = exportBuildImg.replace("\\",File.separator);
					File imgPathFile = new File(exportBuildImg);
					if (!imgPathFile.exists() || !imgPathFile.isDirectory()) {
						imgPathFile.mkdirs();
					}
					if(quCharData!=null && quCharData.indexOf("data:")>=0){
						quCharData = quCharData.replaceAll(" ","+");
						ImageUtils.generateImage(quCharData,exportBuildImg,quId+".png");
					}

					XWPFParagraph firstParagraph = docx.createParagraph();
					XWPFRun run = firstParagraph.createRun();
					run.setText(quNum+"、"+quTitle+"["+quTypeName+"]");
					run.setColor("696969");
					run.setFontSize(12);
					run.setTextPosition(16);
					run.setBold(true);

					if("FILLBLANK".equals(quType) || "UPLOADFILE".equals(quType) || "CHENFBK".equals(quType) || "SIGNATURE".equals(quType)){

						XWPFParagraph fbkParagraph = docx.createParagraph();
						XWPFRun fbkRun = fbkParagraph.createRun();
						fbkRun.setText("填写回答："+quAnCount+"份");
						fbkRun.setColor("696969");
						fbkRun.setFontSize(12);
						fbkRun.setTextPosition(12);

						docx.createParagraph().createRun();

//					else if("RADIO".equals(quType) || "CHECKBOX".equals(quType) || "SCORE".equals(quType) || "ORDERQU".equals(quType) || "MULTIFILLBLANK".equals(quType))
					}else{
//					Map<String,Object> optionIds = WebUtils.getParametersStartingWith(request,"optionId_"+quId);
						//基本信息表格
						if(quStatOptions.size()>0){

							if ("CHENRADIO".equals(quType) || "CHENCHECKBOX".equals(quType)  || "CHENSCORE".equals(quType)) {
								for(int j=0; j<quStatOptions.size(); j++) {
									JSONObject rowOption = quStatOptions.getJSONObject(j);
									JSONArray rowQuStatOptions = rowOption.getJSONArray("quStatCols");
									String rowOptionName = rowOption.getString("optionName");
									XWPFParagraph fbkParagraph = docx.createParagraph();
									XWPFRun fbkRun = fbkParagraph.createRun();
									fbkRun.setText(rowOptionName);
									fbkRun.setColor("696969");
									fbkRun.setFontSize(12);
									fbkRun.setTextPosition(12);
									buildStatsTable(docx, applicationPath, quType, rowQuStatOptions);
								}
							}else{
								buildStatsTable(docx, applicationPath, quType, quStatOptions);
							}
						}

						docx.createParagraph().createRun();
						if(quCharData!=null && quCharData.indexOf("data:")>=0){
//						InputStream pictureData = new FileInputStream("/Users//Desktop/up/"+quId+".png");
							InputStream pictureData = new FileInputStream(exportBuildImg+File.separator+quId+".png");
							XWPFParagraph xwpfParagraph = docx.createParagraph();
							XWPFRun xwpfRun = xwpfParagraph.createRun();
							xwpfRun.addPicture(pictureData, XWPFDocument.PICTURE_TYPE_PNG,quId+".png", Units.toEMU(425),Units.toEMU(112));
							xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
						}

						docx.createParagraph().createRun();
						docx.createParagraph().createRun();

					}
				}


				CTSectPr sectPr = docx.getDocument().getBody().addNewSectPr();
				XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);

				//添加页眉
				CTP ctpHeader = CTP.Factory.newInstance();
				CTR ctrHeader = ctpHeader.addNewR();
				CTText ctHeader = ctrHeader.addNewT();
				String headerText = "www.diaowen.net";
				headerText = "";
				ctHeader.setStringValue(headerText);
				XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, docx);
				//设置为右对齐
				headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
				XWPFParagraph[] parsHeader = new XWPFParagraph[1];
				parsHeader[0] = headerParagraph;
				policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

				//添加页脚
				CTP ctpFooter = CTP.Factory.newInstance();
				CTR ctrFooter = ctpFooter.addNewR();
				CTText ctFooter = ctrFooter.addNewT();
				String footerText = "调问网-专业的问卷系统提供商，http://www.diaowen.net";
				footerText = "";
				ctFooter.setStringValue(footerText);
				XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, docx);
				headerParagraph.setAlignment(ParagraphAlignment.CENTER);
				XWPFParagraph[] parsFooter = new XWPFParagraph[1];
				parsFooter[0] = footerParagraph;
				policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

				String docxPath = "/webin/expfile/"+surveyId;
				docxPath = docxPath.replace("/",File.separator);
				docxPath = docxPath.replace("\\",File.separator);

				String filePath = rootPath+docxPath;
				File file = new File(filePath);
				if (!file.exists()) file.mkdirs();
				String fileName = surveyId+".docx";
//			FileOutputStream out = new FileOutputStream(new File("/Users//Desktop/up/"+surveyName+".docx"));
				String newFilePath = rootPath+docxPath+"/"+fileName;
				FileOutputStream out = new FileOutputStream(new File(newFilePath));
				docx.write(out);
				out.close();
//			DwsUtils.downloadFile(response, fileName, downFilePath);
//				return HttpResult.SUCCESS(docxPath);
				logger.info(" export docxPath {}", newFilePath);
//				dwfile/file/webin/expfile/ecd95f5b-6283-4063-9835-d093b0ce463e/DWSurvey-ecd95f5b-6283-4063-9835-d093b0ce463e.xlsx
//				DwsUtils.downloadFile(response, fileName, newFilePath);
				return HttpResult.SUCCESS(newFilePath);
			}
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		return HttpResult.FAILURE();
	}

	@RequestMapping("/download.do")
	@ResponseBody
	public void downloadFile(HttpServletResponse response, String surveyId) throws IOException {
		String rootPath = DWSurveyConfig.DWSURVEY_WEB_FILE_PATH;
		String fileName = surveyId+".docx";
		String downFilePath = rootPath + "/webin/expfile/"+surveyId+"/"+fileName;
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

	private void buildStatsTable(XWPFDocument docx, String applicationPath, String quType, JSONArray quStatOptions) throws InvalidFormatException,IOException {
		XWPFTable infoTable = docx.createTable();

		infoTable.setCellMargins(20, 20, 20, 20);
		infoTable.setRowBandSize(80);
		infoTable.setColBandSize(30);

		//表格属性
		CTTblPr tablePr = infoTable.getCTTbl().addNewTblPr();
		//去表格边框
//					tablePr.unsetTblBorders();
		//表格宽度
		CTTblWidth width = tablePr.addNewTblW();
		//设置表格宽度为非自动
		width.setType(STTblWidth.DXA);
		width.setW(BigInteger.valueOf(9072));

		//表格边框颜色
		CTTblBorders borders=infoTable.getCTTbl().getTblPr().addNewTblBorders();
		//表格内部横向表格颜色
		CTBorder hBorder=borders.addNewInsideH();
		hBorder.setVal(STBorder.Enum.forString("single"));
		hBorder.setSz(new BigInteger("1"));
		hBorder.setColor("dddddd");
		//表格内部纵向表格颜色
		CTBorder vBorder=borders.addNewInsideV();
		vBorder.setVal(STBorder.Enum.forString("single"));
		vBorder.setSz(new BigInteger("1"));
		vBorder.setColor("dddddd");
		//表格最左边一条线的样式
		CTBorder lBorder=borders.addNewLeft();
		lBorder.setVal(STBorder.Enum.forString("single"));
		lBorder.setSz(new BigInteger("1"));
		lBorder.setColor("dddddd");
		//表格最右边一条线的样式
		CTBorder rBorder=borders.addNewRight();
		rBorder.setVal(STBorder.Enum.forString("single"));
		rBorder.setSz(new BigInteger("1"));
		rBorder.setColor("dddddd");
		//表格最上边一条线（顶部）的样式
		CTBorder tBorder=borders.addNewTop();
		tBorder.setVal(STBorder.Enum.forString("single"));
		tBorder.setSz(new BigInteger("1"));
		tBorder.setColor("dddddd");
		//表格最下边一条线（底部）的样式
		CTBorder bBorder=borders.addNewBottom();
		bBorder.setVal(STBorder.Enum.forString("single"));
		bBorder.setSz(new BigInteger("1"));
		bBorder.setColor("dddddd");

		int rowIndex = 0;

		XWPFTableRow infoTableRowOne = infoTable.getRow(rowIndex++);
		XWPFTableCell headCell0 = infoTableRowOne.getCell(0);
		XWPFTableCell headCell1 = infoTableRowOne.addNewTableCell();
		XWPFTableCell headCell2 = infoTableRowOne.addNewTableCell();

//						headCell0.removeParagraph(0);
//						headCell1.removeParagraph(0);
//						headCell2.removeParagraph(0);

		XWPFParagraph p0 = headCell0.addParagraph();
		XWPFRun headRun0 = p0.createRun();
		headRun0.setText("选项");
		headRun0.setFontSize(12);
		headRun0.setBold(true);//是否粗体
		headRun0.setTextPosition(12);// 设置上下两行之间的间距
		headCell0.setColor("DEDEDE");

		//垂直居中
		p0.setVerticalAlignment(TextAlignment.CENTER);
		//水平居中
		p0.setAlignment(ParagraphAlignment.CENTER);

		XWPFParagraph p1 = headCell1.addParagraph();
		XWPFRun headRun1 = p1.createRun();

		if("RADIO".equals(quType) || "CHECKBOX".equals(quType) || "CHENRADIO".equals(quType) || "CHENCHECKBOX".equals(quType)){
			headRun1.setText("小计");
		}else if("SCORE".equals(quType) || "CHENSCORE".equals(quType)){
			headRun1.setText("平均分");
		}else if("ORDERQU".equals(quType)){
			headRun1.setText("排名");
		}else if("MULTIFILLBLANK".equals(quType)){
			headRun1.setText("小计");
		}else if("SCALE".equals(quType)){
			headRun1.setText("分值");
		}else{
			headRun1.setText("");
		}

		headRun1.setFontSize(12);
		headRun1.setBold(true);
		headRun1.setTextPosition(12);// 设置上下两行之间的间距
		headCell1.setColor("DEDEDE");

		CTTcPr cellPr1 = headCell1.getCTTc().addNewTcPr();
		cellPr1.addNewTcW().setW(BigInteger.valueOf(1000));

		//垂直居中
		p1.setVerticalAlignment(TextAlignment.CENTER);
		//水平居中
		p1.setAlignment(ParagraphAlignment.CENTER);

		XWPFParagraph p2  = headCell2.addParagraph();
		XWPFRun headRun2 = p2.createRun();
		headRun2.setFontSize(12);
		if("RADIO".equals(quType) || "CHECKBOX".equals(quType)  || "CHENRADIO".equals(quType) || "CHENCHECKBOX".equals(quType) ){
			headRun2.setText("百分比");
		}else if("SCORE".equals(quType)){
			headRun2.setText("占总分比例");
		}else if("SCALE".equals(quType) || "CASCADE".equals(quType)){
			headRun2.setText("频次比例");
		}else if("CHENSCORE".equals(quType)){
			headRun2.setText("平均分占比");
		}else{
			headRun2.setText("排名比例");
		}

		headRun2.setBold(true);
		headRun2.setTextPosition(12);// 设置上下两行之间的间距
		headCell2.setColor("DEDEDE");
		//垂直居中
		p2.setVerticalAlignment(TextAlignment.CENTER);
		//水平居中
		p2.setAlignment(ParagraphAlignment.CENTER);

		CTTcPr cellPr2 = headCell2.getCTTc().addNewTcPr();
		cellPr2.addNewTcW().setW(BigInteger.valueOf(4000));


		for(int j=0; j<quStatOptions.size(); j++) {

			JSONObject reqOption = quStatOptions.getJSONObject(j);
			String optionName = reqOption.getString("optionName");
			String bfbTd = reqOption.getString("percent");
			String quItemAnCount = reqOption.getString("anCount");
			Integer orderNum = reqOption.getInteger("orderNum");

			if (rowIndex>0) infoTable.createRow();
			//表格第一行
			XWPFTableRow infoTableRowGG = infoTable.getRow(rowIndex++);
			XWPFTableCell cell0=infoTableRowGG.getCell(0);
			XWPFTableCell cell1=infoTableRowGG.getCell(1);
			XWPFTableCell cell2=infoTableRowGG.getCell(2);
//							cell0.removeParagraph(0);
//							cell1.removeParagraph(0);
//							cell2.removeParagraph(0);
			cell0.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
			cell1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
			cell2.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);

			XWPFParagraph cellP0 = cell0.addParagraph();
			cellP0.setAlignment(ParagraphAlignment.LEFT);
			cellP0.setVerticalAlignment(TextAlignment.CENTER);
			XWPFRun cellRu0 = cellP0.createRun();
			cellRu0.setText(optionName);
			cellRu0.setFontSize(10);
			cellRu0.setTextPosition(12);// 设置上下两行之间的间距

			XWPFParagraph cellP1 = cell1.addParagraph();
			cellP1.setAlignment(ParagraphAlignment.CENTER);
			cellP1.setVerticalAlignment(TextAlignment.CENTER);
			XWPFRun cellRu1 = cellP1.createRun();

			if("RADIO".equals(quType) || "CHECKBOX".equals(quType) || "CASCADE".equals(quType)){
				cellRu1.setText(quItemAnCount+"次");
			}else if("SCORE".equals(quType) || "CHENSCORE".equals(quType)){
				cellRu1.setText(quItemAnCount+"分");
			}else if("ORDERQU".equals(quType)){
				cellRu1.setText("第"+orderNum+"名");
			}else if("MULTIFILLBLANK".equals(quType)){
				cellRu1.setText(quItemAnCount+"次");
			}else if("SCALE".equals(quType)){
				cellRu1.setText(optionName+"分");
			}else{
				cellRu1.setText(quItemAnCount+"次");
			}

			cellRu1.setFontSize(10);
			cellRu1.setTextPosition(12);// 设置上下两行之间的间距

//							String imgFilePath1 = "/Users//Desktop/up/pro@2x.png";
//							String imgFilePath2 = "/Users//Desktop/up/prog@2x.png";
			String imgFilePath1 = applicationPath+"/dwfile/resource/export/pro@2x.png";
			String imgFilePath2 = applicationPath+"/dwfile/resource/export/prog@2x.png";
			imgFilePath1 = imgFilePath1.replace("/",File.separator);
			imgFilePath1 = imgFilePath1.replace("\\",File.separator);
			imgFilePath2 = imgFilePath2.replace("/",File.separator);
			imgFilePath2 = imgFilePath2.replace("\\",File.separator);
			logger.info("imgFilePath1 {}", imgFilePath1);

			XWPFParagraph cellP2 = cell2.addParagraph();
			cellP2.setAlignment(ParagraphAlignment.LEFT);
			cellP2.setVerticalAlignment(TextAlignment.CENTER);
			XWPFRun cellRun2 = cellP2.createRun();

			if("RADIO".equals(quType) || "CHECKBOX".equals(quType) || "SCORE".equals(quType) || "ORDERQU".equals(quType) || "SCALE".equals(quType) || "CASCADE".equals(quType) || "MULTIFILLBLANK".equals(quType)  || "CHENRADIO".equals(quType) || "CHENCHECKBOX".equals(quType) || "CHENSCORE".equals(quType) || "MULTISLIDER".equals(quType)  || "CHENSCALE".equals(quType)){
				InputStream pictureData1 = new FileInputStream(imgFilePath1);
				InputStream pictureData2 = new FileInputStream(imgFilePath2);
				int tdW = 160;
				float p1W = 60;
				float p2W = 100;
				if(NumberUtils.isNumber(bfbTd)){
					float bfb = Float.parseFloat(bfbTd)/100;
					p1W = bfb*tdW;
					p2W = tdW-p1W;
				}
				cellRun2.addPicture(pictureData1, XWPFDocument.PICTURE_TYPE_PNG,"pQ1.png", Units.toEMU(p1W),Units.toEMU(10));
				cellRun2.addPicture(pictureData2, XWPFDocument.PICTURE_TYPE_PNG,"pQ2.png", Units.toEMU(p2W),Units.toEMU(10));

				if("SCORE".equals(quType) || "CHENSCORE".equals(quType)){
					cellRun2.setText(bfbTd+"%");
				}else if("ORDERQU".equals(quType)){
					cellRun2.setText(bfbTd+"%");
				}else if("SCALE".equals(quType)){
					cellRun2.setText(bfbTd+"%");
				}else{
					cellRun2.setText(bfbTd+"%");
				}

			}else{
				cellRun2.setText(quItemAnCount+"次");
			}

			cellRun2.setFontSize(10);
			cellRun2.setTextPosition(12);// 设置上下两行之间的间距

		}
	}

}
