package net.diaowen.common.base.controller;

import com.octo.captcha.service.image.ImageCaptchaService;
import net.diaowen.common.plugs.httpclient.HttpResult;
import net.diaowen.common.plugs.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
//@RequestMapping("/jcap")
@RequestMapping("/api/dwsurvey/anon/jcap")
public class JcaptchaController {

	@Autowired
    private ImageCaptchaService imageCaptchaService;

    @RequestMapping("/jcaptcha.do")
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // get the session id that will identify the generated captcha.
            // the same id must be used to validate the response, the session id
            // is a good candidate!
            String captchaId = request.getSession().getId();
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, request.getLocale());
            // a jpeg encoder
            /*
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
            */
            jpegOutputStream = new ByteArrayOutputStream();
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
	}

    @RequestMapping("/jcaptcha-check.do")
    @ResponseBody
    public HttpResult checkCode(HttpServletRequest request, @RequestParam String captchaCode) throws Exception {
        try{
            if(StringUtils.isNotEmpty(captchaCode)){
                if (imageCaptchaService.validateResponseForID(request.getSession().getId(), captchaCode)) return HttpResult.SUCCESS();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpResult(HttpStatus.SERVER_30009,captchaCode);
    }

}
