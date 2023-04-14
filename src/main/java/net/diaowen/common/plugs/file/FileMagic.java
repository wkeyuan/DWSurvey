package net.diaowen.common.plugs.file;


public enum  FileMagic {
    JPG("JPG",".jpg"),//JPEG
    JPEG("JPEG",".jpeg"),//JPEG
    PNG("PNG",".png"),//PNG
    GIF("GIF",".gif"),//GIF
    TIFF("TIF",".tif"),//TIFF
    BMP("BMP",".bmp"),//Windows bitmap
    DWG("DWG", ".dwg"),//CAD
    PSD("PSD", ".psd"),//Adobe Photoshop
    RTF("RTF", ".rtf"),//Rich Text Format
    TXT("TXT", ".txt"),//txt
    XML("XML", ".xml"),//XML
    HTML("HTML", ".html"),//HTML
    EML("EML", ".eml"),//Email
    DBX("DBX", ".dbx"),//Outlook Express
    OLE2("OLE2",".doc,.xls,.dot,.ppt,.xla,.ppa,.pps,.pot,.msi,.sdw,.db"),//
    PPT("PPT", ".ppt"),//Microsoft 2003 Word
    XLS("XLS", ".xls"),//Microsoft 2003 Word
    DOC("DOC", ".doc"),//Microsoft 2003 Excel
    PPTX("PPTX", ".pptx"),//Microsoft 2007 ppt
    DOCX("DOCX", ".docx"),//Microsoft 2007 Word
    XLSX("XLSX", ".xlsx"),//Microsoft 2007 Excel
    MDB("MDB",".mdb"),//Microsoft Acces
    WPB("WPB", ".wpd"),//Word Perfect
    EPS("EPS", ".eps"),//Postscript
    PS("PS", ".ps"),//Postscript
    PDF("PDF", ".pdf"),//Adobe Acrobat
    QDF("qdf", ".qdf"),//Quicken
    QDB("qbb", ".qdb"),//QuickBooks Backup
    PWL("PWL", ".pwl"),//Windows Password
    ZIP("ZIP", ".zip"),//ZIP
    RAR("RAR", ".rar"),//ARAR Archive
    Z7Z("7Z", ".7z"),//ARAR Archive
    WAV("WAV", ".wav"),//WAVE
    AVI("AVI", ".avi"),//AVI
    RAM("RAM", ".ram"),//Real Audio
    RM("RM", ".rm"),//Real Media
    RMVB("RMVB", ".rmvb"),//Real Media
    MPG("MPG", ".mpg"),//MPEG
    MOV("MOV", ".mov"),//Quicktime
    ASF("ASF", ".asf"),//Windows Media
    ARJ("ARJ", ".arj"),//ARJ Archive
    MID("MID", ".mid"),//MIDI
    MP4("MP4", ".mp4"),//MP4
    MP3("MP3", ".mp3"),//MP3
    FLV("FLV", ".flv"),//FLV
    GZ("GZ", ".gz"),//
    CSS("CSS", ".css"),//CSS
    JS("JS", ".js"),//JS
    VSD("VSD", ".vsd"),//Visio
    WPS("WPS", ".wps,.et,.dps"),//WPS
    TORRENT("TORRENT", ".torrent"),
    JSP("JSP", ".jsp"),//JSP
    JAVA("JAVA", ".java"),//JAVA
    CLASS("CLASS", ".class"),//CLASS
    JAR("JAR", ".jar"),//JAR
    MF("MF", ".mf"),//MF
    EXE("EXE", ".exe"),//EXE
    ELF("ELF", ".elf"),//ELF
    WK1("WK1", ".wk1"),//Lotus 123 v1
    WK3("WK3", ".vk3"),//Lotus 123 v3
    WK4("WK4", ".vk4"),//Lotus 123 v4
    LWP("LWP", ".lwp"),//Lotus WordPro v9
    SLY("SLY", ".sly,.srt,.slt,.sly"),
    UNKNOWN("", "","");

    static final int MAX_PATTERN_LENGTH = 44;

    //文件类型
    private String fileType;
    //文件类型对应的魔数, 留着自定义补充验证类型用
    private String fileMagicCode;
    //文件后缀
    private String fileSuffix;

    private FileMagic(String fileType,String fileSuffix) {
        this.fileType = fileType;
        this.fileSuffix = fileSuffix;
    }

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

}
