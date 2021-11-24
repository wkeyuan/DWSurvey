package net.diaowen.common.plugs.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

/**
 * Created by keyuan on 2018/9/8.
 */
public class ZxingUtil {

    public static BufferedImage qRCodeCommon(String content, String imgType, int size){
        int imgSize = 67 + 12 * (size - 1);
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try{
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, imgSize, imgSize, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        }catch (WriterException e){
            e.printStackTrace();
        }
        return null;
    }


}
