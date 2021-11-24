package net.diaowen.common.plugs.file;


import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LittleEndian;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public enum  FileMagic {
    JPEG("JPG", "FFD8FF","jpg"),//JPEG
    PNG("PNG", "89504E47","png"),//PNG
    GIF("GIF", "47494638","gif"),//GIF
    TIFF("TIF", "49492A00","tif"),//TIFF

    BMP("BMP","424D","bmp"),//Windows bitmap
    BMP_16("BMP","424D228C010000000000","bmp"), //16色位图(bmp)
    BMP_24("BMP","424D8240090000000000","bmp"), //24位位图(bmp)
    BMP_256("BMP","424D8E1B030000000000","bmp"), //256色位图(bmp)

    DWG("DWG", "41433130","dwg"),//CAD
    PSD("PSD", "38425053","psd"),//Adobe Photoshop

    RTF("RTF", "7B5C727466","rtf"),//Rich Text Format

    XML("XML", "3C3F786D6C","xml"),//XML
    HTML("HTML", "68746D6C3E","html"),//HTML

    EML("EML", "44656C69766572792D646174653A","eml"),//Email
    DBX("DBX", "CFAD12FEC5FD746F ","dbx"),//Outlook Express

    OLE2("OLE2", "0xD0CF11E0A1B11AE1","doc,xls,dot,ppt,xla,ppa,pps,pot,msi,sdw,db"),//

    //注意：word 和 excel的文件头一样
    XLS("XLS", "D0CF11E0","xls"),//Microsoft 2003 Word
    DOC("DOC", "D0CF11E0","doc"),//Microsoft 2003 Excel

    //可能会出现文件头一样的情况，需要判断后再取一次后缀名
//    DOCX("DOCX", "504b0304140006000800","docx"),//Microsoft 2007 Word
//    XLSX("XLSX", "504b0304140008080800","xlsx"),//Microsoft 2007 Excel

    DOCX("DOCX", "504b030414","docx"),//Microsoft 2007 Word
    XLSX("XLSX", "504b030414","xlsx"),//Microsoft 2007 Excel

    MDB("MDB", "5374616E64617264204A","mdb"),//Microsoft Acces

    WPB("WPB", "FF575043","wpd"),//Word Perfect

    EPS("EPS", "252150532D41646F6265","eps"),//Postscript

    PS("PS", "252150532D41646F6265","ps"),//Postscript

    PDF("PDF", "255044462D312E","pdf"),//Adobe Acrobat

    QDF("qdf", "AC9EBD8F","qdf"),//Quicken

    QDB("qbb", "458600000600","qdb"),//QuickBooks Backup

    PWL("PWL", "E3828596","pwl"),//Windows Password

    ZIP("ZIP", "504b0304140000000800","zip"),//ZIP

    RAR("RAR", "52617221","rar"),//ARAR Archive

    WAV("WAV", "57415645","wav"),//WAVE

    AVI("AVI", "41564920","avi"),//AVI

    RAM("RAM", "2E7261FD","ram"),//Real Audio

    RM("RM", "2E524D46","rm"),//Real Media
    RMVB("RMVB", "2E524D46000000120001","rmvb"),//Real Media

    MPG("MPG", "000001BA","mpg"),//MPEG

    MOV("MOV", "6D6F6F76","mov"),//Quicktime

    ASF("ASF", "3026B2758E66CF11","asf"),//Windows Media

    ARJ("ARJ", "60EA","arj"),//ARJ Archive

    MID("MID", "4D546864","mid"),//MIDI

    MP4("MP4", "00000020667479706D70","mp4"),//MP4

    MP3("MP3", "49443303000000002176","mp3"),//MP3

    FLV("FLV", "464C5601050000000900","flv"),//FLV

    GZ("GZ", "1F8B08","gz"),//

    CSS("CSS", "48544D4C207B0D0A0942","css"),//CSS
    JS("JS", "696B2E71623D696B2E71","js"),//JS

    VSD("VSD", "d0cf11e0a1b11ae10000","vsd"),//Visio

    WPS("WPS", "d0cf11e0a1b11ae10000","wps,et,dps"),//WPS

    TORRENT("TORRENT", "6431303A637265617465","torrent"),

    JSP("JSP", "3C2540207061676520","jsp"),//JSP

    JAVA("JAVA", "7061636B61676520","java"),//JAVA

    CLASS("CLASS", "CAFEBABE0000002E00","class"),//CLASS

    JAR("JAR", "504B03040A000000","jar"),//JAR

    MF("MF", "4D616E69666573742D56","mf"),//MF

    EXE("EXE", "4D5A9000030000000400","exe"),//EXE
    ELF("ELF", "7F454C4601010100","elf"),//ELF
    WK1("WK1", "2000604060","wk1"),//Lotus 123 v1
    WK3("WK3", "00001A0000100400","vk3"),//Lotus 123 v3
    WK4("WK4", "00001A0002100400","vk4"),//Lotus 123 v4
    LWP("LWP", "576F726450726F","lwp"),//Lotus WordPro v9
    SLY("SLY", "53520100","sly,srt,slt,sly"),
    UNKNOWN("", "","");

    static final int MAX_PATTERN_LENGTH = 44;

    //文件类型
    private String fileType;
    //文件类型对应的魔数
    private String fileMagicCode;
    //文件后缀
    private String fileSuffix;

    private FileMagic(String fileType,String fileMagicCode,String fileSuffix) {
        this.fileType = fileType;
        this.fileMagicCode = fileMagicCode;
        this.fileSuffix = fileSuffix;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileMagicCode() {
        return fileMagicCode;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public static FileMagic valueOf(byte[] magic) {
        String magicCode = ByteArrayUtil.toHexString(magic);
        magicCode = magicCode.toLowerCase();
        FileMagic[] fileMagics = values();
        int fileMagicLength = fileMagics.length;
        for(int i = 0; i < fileMagicLength; ++i) {
            FileMagic fm = fileMagics[i];
            String fileMagicCode = fm.fileMagicCode.toLowerCase();
            if (fileMagicCode.startsWith(magicCode)
                    || magicCode.startsWith(fileMagicCode)) {
                return fm;
            }
        }
        return UNKNOWN;
    }

    //根据文件获取对应的文件类型
    public static FileMagic valueOf(MultipartFile inp) throws Exception {
        InputStream fis = inp.getInputStream();
        return valueOf(inp.getName(), fis);
    }


    //根据文件获取对应的文件类型
    public static FileMagic valueOf(File inp) throws Exception {
        FileInputStream fis = new FileInputStream(inp);
        return valueOf(inp.getName(),fis);
    }

    public static FileMagic valueOf(String fileName, InputStream fis) throws IOException {
        FileMagic fileMagic;
        try{
            byte[] data = new byte[44];
            int read = IOUtils.readFully(fis, data, 0, MAX_PATTERN_LENGTH);
            if (read != -1) {
                data = Arrays.copyOf(data, read);
                fileMagic = valueOf(data);
                fileMagic = valueOf(fileMagic,fileName);
                return fileMagic;
            }
        }catch (Exception exception){
            throw exception;
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception var14) {
                    throw var14;
                }
            }
        }
        return UNKNOWN;
    }

    private static FileMagic valueOf(FileMagic fileMagic,String fileName){
        if(fileMagic==FileMagic.DOCX || fileMagic==FileMagic.XLSX || fileMagic==FileMagic.DOC || fileMagic==FileMagic.XLS){
            if(fileName.endsWith(FileMagic.DOCX.fileSuffix)){
                return FileMagic.DOCX;
            }else if(fileName.endsWith(FileMagic.XLSX.fileSuffix)){
                return FileMagic.XLSX;
            }else if(fileName.endsWith(FileMagic.DOC.fileSuffix)){
                return FileMagic.DOC;
            }else if(fileName.endsWith(FileMagic.XLS.fileSuffix)){
                return FileMagic.XLS;
            }
        }
        return fileMagic;
    }

}
