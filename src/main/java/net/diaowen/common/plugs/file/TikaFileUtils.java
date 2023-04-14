package net.diaowen.common.plugs.file;

import com.alibaba.fastjson.JSON;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TikaFileUtils {

    public static String mimeType(byte[] bytes, String suffix) {
        try {
            Tika tika = new Tika();
            String mimeTypeStr = tika.detect(bytes,suffix);
            return getMimeType(mimeTypeStr, suffix);
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String mimeType(InputStream inputStream, String suffix) {
        try {
            Tika tika = new Tika();
            String mimeTypeStr = tika.detect(inputStream,suffix);
            return getMimeType(mimeTypeStr, suffix);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMimeType(String mimeTypeStr, String suffix) throws MimeTypeException {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mimeTypes.forName(mimeTypeStr);
        if(mimeType.getExtensions().stream().anyMatch(ext -> ext.equals(suffix))){
            return suffix;
        }
        return null;
    }

    public static String getMimeType(InputStream inputStream, String suffix) {
        try {
            Tika tika = new Tika();
            String mimeTypeStr = tika.detect(inputStream,suffix);
            return getMimeType(mimeTypeStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MimeTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMimeType(String mimeTypeStr) throws MimeTypeException {
        MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mimeTypes.forName(mimeTypeStr);
        List<String> list = mimeType.getExtensions();
        return JSON.toJSONString(list);
    }

}
