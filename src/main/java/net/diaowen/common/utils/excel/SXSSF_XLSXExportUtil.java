package net.diaowen.common.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * 用于超大数据量的操作
 * SXSSF可以避免XSSF占用内存过大
 * @author keyuan
 * @date 20211125
 */
public class SXSSF_XLSXExportUtil {

	// 设置cell编码解决中文高位字节截断
	// 定制日期格式
	private static String DATE_FORMAT = " m/d/yy "; // "m/d/yy h:mm"
	// 定制浮点数格式
	private static String NUMBER_FORMAT = " #,##0.00 ";
	private String xlsFileName;
	private String path;
	private SXSSFWorkbook workbook;
	private SXSSFSheet sheet;
	private SXSSFRow row;
	private CellStyle cellStyle;
	private DataFormat dataFormat ;
	/**
	 * 初始化Excel
	 *
	 * @param fileName
	 *            导出文件名
	 */
	public SXSSF_XLSXExportUtil(String fileName, String path) {
		this.xlsFileName = fileName;
		this.path=path;
		this.workbook = new SXSSFWorkbook();
//		this.workbook = new SXSSFWorkbook(null, 100, true, true);
		this.sheet = workbook.createSheet();
		this.cellStyle = workbook.createCellStyle(); // 建立新的cell样式;
		this.dataFormat = workbook.createDataFormat();
		this.sheet.setRandomAccessWindowSize(-1);
		//		SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
	}


	/** */
	/**
	 * 导出Excel文件
	 *
	 */
	public void exportXLS() throws Exception {
		try {
			File file=new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream fOut = new FileOutputStream(path+File.separator+xlsFileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			throw new Exception(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new Exception(" 写入Excel文件出错! ", e);
		}

	}

	/**
	 * 增加一行, 单线程使用
	 *
	 * @param index
	 *            行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}


	public void setCellBlue(int index, String value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(CellType.STRING);
//		CellStyle style = workbook.createCellStyle();
//		Font font = workbook.createFont();
//		font.setColor(IndexedColors.BLUE.getIndex());
//		style.setFont(font);

		cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	public void setCellYellow(int index, String value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(CellType.STRING);

//		Font font = workbook.createFont();
//		font.setColor(IndexedColors.BLUE.getIndex());
//		style.setFont(font);

		cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格, 单线程时可以使用
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	@SuppressWarnings("deprecation")
	public void setCell(int index, String value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	@SuppressWarnings("deprecation")
	public void setCell(int index, Calendar value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellValue(value.getTime());
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, int value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(CellType.NUMERIC);
		cell.setCellValue(value);
	}

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, double value) {
		SXSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(CellType.NUMERIC);
		cell.setCellValue(value);
		cellStyle.setDataFormat(dataFormat.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	public SXSSFSheet getSheet() {
		return sheet;
	}

	public SXSSFRow getRow(int rowIndex) {
		SXSSFRow  row = this.getSheet().getRow(rowIndex);
		return row;
	}

	/**
	 * 多线程使用
	 * @param index
	 */
	public synchronized void createNewRow(int index) {
		this.sheet.createRow(index);
	}

	/**
	 * 多线程同时写入多行时使用
	 * @param row
	 * @param cellIndex
	 * @param value
	 */
	public void setCell(SXSSFRow row, int cellIndex, String value) {
		if(row!=null){
			SXSSFCell cell = row.createCell((short) cellIndex);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(value);
		}
	}

}
