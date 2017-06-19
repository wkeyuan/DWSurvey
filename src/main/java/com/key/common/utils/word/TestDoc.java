package com.key.common.utils.word;

import java.io.FileOutputStream;  
import com.lowagie.text.Document;  
import com.lowagie.text.Font;  
import com.lowagie.text.Paragraph;  
import com.lowagie.text.rtf.RtfWriter2;  

public class TestDoc {  

    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        exportDoc("D:\\test.doc");  

    }  

    public static void exportDoc(String fileName){  
        try {  
            Document doc = new Document();  
            RtfWriter2.getInstance(doc, new FileOutputStream(fileName));  
            // 打开文档  
            doc.open();  
            //设置页边距，上、下25.4毫米，即为72f，左、右31.8毫米，即为90f  
            doc.setMargins(90f, 90f, 72f, 72f);  

            //设置标题字体样式，粗体、二号、华文中宋  
            Font tfont = DocStyleUtils.setFontStyle("华文中宋", 22f, Font.BOLD);  
            //设置正文内容的字体样式，常规、三号、仿宋_GB2312  
            Font bfont = DocStyleUtils.setFontStyle("仿宋_GB2312", 16f, Font.NORMAL);  

            //构建标题，居中对齐，12f表示单倍行距  
            Paragraph title = DocStyleUtils.setParagraphStyle("测试Itext导出Word文档", tfont, 12f, Paragraph.ALIGN_CENTER);  
            //构建正文内容  
            StringBuffer contentSb = new StringBuffer();  
            contentSb.append("最近项目很忙，这个是项目中使用到的，所以现在总结一下，以便今后可以参考使用，");  
            contentSb.append("2011年4月27日 — 2011年5月20日，对以下技术进行使用，");  
            contentSb.append("Itext、");  
            contentSb.append("Excel、");  
            contentSb.append("Word、");  
            contentSb.append("PPT。");  

            //首行缩进2字符，行间距1.5倍行距  
            Paragraph bodyPar = DocStyleUtils.setParagraphStyle(contentSb.toString(), bfont, 32f, 18f);  
            Paragraph bodyEndPar = DocStyleUtils.setParagraphStyle("截至2011年4月28日，各种技术已经完全实现。", bfont, 32f, 18f);  
            //设置空行  
            Paragraph blankRow = new Paragraph(18f, " ", bfont);  
            Paragraph deptPar = DocStyleUtils.setParagraphStyle("（技术开发部盖章）", bfont, 12f, Paragraph.ALIGN_RIGHT);  
            Paragraph datePar = DocStyleUtils.setParagraphStyle("2011-04-30", bfont, 12f, Paragraph.ALIGN_RIGHT);  

            //向文档中添加内容  
            doc.add(title);  
            doc.add(blankRow);  
            doc.add(bodyPar);  
            doc.add(bodyEndPar);  
            doc.add(blankRow);  
            doc.add(blankRow);  
            doc.add(blankRow);  
            doc.add(deptPar);  
            doc.add(datePar);  

            //最后一定要记住关闭  
            doc.close();  

        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }     

}  