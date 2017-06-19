package com.key.common.utils.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.RtfWriter2;

public class DocExportUtil {
	
	private String docFileName;
	private String path;
	private Document doc;
	private File file;
	private FileOutputStream fileOutputStream;
	private Font tfont ;
	private Font bfont;
	
	public DocExportUtil(String docFileName,String path){
		this.docFileName=docFileName;
		this.path=path;
	}
	
	public void createDoc() throws FileNotFoundException{
		 /** 创建Document对象（word文档）  */
        Rectangle rectPageSize = new Rectangle(PageSize.A4);
        rectPageSize = rectPageSize.rotate();
        // 创建word文档,并设置纸张的大小
        doc = new Document(PageSize.A4);
        file=new File(path+docFileName);
        fileOutputStream=new FileOutputStream(file);
        /** 建立一个书写器与document对象关联，通过书写器可以将文档写入到输出流中 */
        RtfWriter2.getInstance(doc, fileOutputStream );
        doc.open();
        //设置页边距，上、下25.4毫米，即为72f，左、右31.8毫米，即为90f  
        doc.setMargins(90f, 90f, 72f, 72f);
        //设置标题字体样式，粗体、二号、华文中宋  
        tfont  = DocStyleUtils.setFontStyle("华文中宋", 22f, Font.BOLD);  
        //设置正文内容的字体样式，常规、三号、仿宋_GB2312  
        bfont = DocStyleUtils.setFontStyle("仿宋_GB2312", 16f, Font.NORMAL);
	}
	
	public Font getTfont() {
		return tfont;
	}
	public Font getBfont() {
		return bfont;
	}
	
	public void addElement(Paragraph paragraph) throws DocumentException{
		doc.add(paragraph);
	}
	
	public void export(){
		doc.close();
	}
	
	public void exportBCD(){
		doc.close();
	}
}
