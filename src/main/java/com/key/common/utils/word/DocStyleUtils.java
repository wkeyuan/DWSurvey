package com.key.common.utils.word;
import java.awt.Color;  

import com.lowagie.text.Chunk;  
import com.lowagie.text.Font;  
import com.lowagie.text.Paragraph;  
import com.lowagie.text.Phrase;  

public class DocStyleUtils {  

    /** 
     * 功能说明：设置字体的样式，颜色为黑色</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param family  字体类型 
     * @param size    字体大小，22f为二号，18f为小二号，16f为三号 
     * @param style   字体样式 
     * @return 
     */  
    public static Font setFontStyle(String family , float size , int style){  
        return setFontStyle(family, Color.BLACK, size, style);  
    }  

    /** 
     * 功能说明：设置字体的样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param family  字体类型 
     * @param color   字体颜色 
     * @param size    字体大小，22f为二号，18f为小二号，16f为三号 
     * @param style   字体样式 
     * @return 
     */  
    public static Font setFontStyle(String family , Color color , float size , int style){  
        Font font = new Font();  
        font.setFamily(family);  
        font.setColor(color);  
        font.setSize(size);  
        font.setStyle(style);  
        return font;  
    }  

    /** 
     * 功能说明：为文字填充浅灰色背景</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content   需要填充背景颜色的内容 
     * @param appendStr 不需要填充背景颜色的内容 
     * @return 
     */  
    private static Phrase setPhraseStyle(String content , String appendStr){  
        Chunk chunk = new Chunk(content);  
        //填充的背景颜色为浅灰色  
        chunk.setBackground(Color.LIGHT_GRAY);  
        Phrase phrase = new Phrase(chunk);  
        phrase.add(appendStr);  
        return phrase;  
    }  

    /** 
     * 功能说明：设置段落的样式，设置前半截内容和后半截内容格式不一样的段落样式</BR> 
     * 修改日：2011-04-27 
     * @author myclover 
     * @param content  前半截内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进多少字符，16f约等于一个字符 
     * @param appendStr 后半截内容 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , String appendStr){  
        Paragraph par = setParagraphStyle(content, font, 0f, 12f);  
        Phrase phrase = new Phrase();  
        phrase.add(par);  
        phrase.add(appendStr);  
        Paragraph paragraph = new Paragraph(phrase);  
        paragraph.setFirstLineIndent(firstLineIndent);  
        //设置对齐方式为两端对齐  
        paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);  
        return paragraph;  
    }  

    /** 
     * 功能说明：设置段落的样式，设置前半截内容填充了浅灰色的背景颜色，后半截内容没有背景颜色的段落样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  前半截有背景颜色的内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进的字符，16f约等于一个字符 
     * @param leading  行间距12f表示单倍行距 
     * @param appendStr 后半截内容 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , String appendStr){  
        Phrase phrase = setPhraseStyle(content , appendStr);  
        Paragraph par = new Paragraph(phrase);  
        par.setFont(font);  
        par.setFirstLineIndent(firstLineIndent);  
        par.setLeading(leading);  
        //设置对齐方式为两端对齐  
        par.setAlignment(Paragraph.ALIGN_JUSTIFIED_ALL);  
        return par;  
    }  

    /** 
     * 功能说明：设置段落的样式，一般用于设置标题</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体样式 
     * @param leading  行间距 
     * @param alignment 对齐方式 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float leading , int alignment){  
        return setParagraphStyle(content, font, 0f, leading, 0f, alignment);  
    }  

    /** 
     * 功能说明：设置段落的样式，对齐方式为两端对齐，缩进样式是文本之后0.2毫米</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体的样式 
     * @param firstLineIndent 首行缩进多少字符，16f约等于一个字符 
     * @param leading  行间距 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading){  
        return setParagraphStyle(content, font, firstLineIndent, leading, 0.6f, Paragraph.ALIGN_JUSTIFIED_ALL);  
    }  

    /** 
     * 功能说明：设置段落的样式</BR> 
     * 修改日期：2011-04-27 
     * @author myclover 
     * @param content  段落的内容 
     * @param font     字体的样式 
     * @param firstLineIndent  首行缩进多少字符，16f约等于一个字符 
     * @param leading  行间距 
     * @param indentationRight 缩进样式中的文本之后多少毫米，0.6f相当于0.2毫米 
     * @param alignment 对齐方式 
     * @return 
     */  
    public static Paragraph setParagraphStyle(String content , Font font , float firstLineIndent , float leading , float indentationRight , int alignment){  
        Paragraph par = new Paragraph(content, font);  
        par.setFirstLineIndent(firstLineIndent);  
        par.setLeading(leading);  
        par.setIndentationRight(indentationRight);  
        par.setAlignment(alignment);  
        return par;  
    }  

}  