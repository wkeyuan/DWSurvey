package com.key.common.base.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.springframework.beans.factory.annotation.Autowired;

import com.key.common.utils.web.Struts2Utils;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Namespaces({ @Namespace("/") })
public class JcaptchaAction extends ActionSupport {
	
	@Autowired  
    private ImageCaptchaService imageCaptchaService;  
	
	public String execute() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
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
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);  
            jpegEncoder.encode(challenge);
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
	
	
	/*@Autowired
	private GenericManageableCaptchaService captchaService;

	@Override
	public String execute() throws Exception {
		HttpServletRequest request = Struts2Utils.getRequest();
		HttpServletResponse response = Struts2Utils.getResponse();
		byte[] captchaChallengeAsJpeg = null;
		// 输出jpg的字节流
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			// call the ImageCaptchaService getChallenge method
			BufferedImage challenge = (BufferedImage) captchaService
					.getChallengeForID(captchaId, request.getLocale());
			JPEGImageEncoder jpegEncoder = JPEGCodec
					.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);

		} catch (IllegalArgumentException e) {
			response.sendError(response.SC_NOT_FOUND);
			return null;
		} catch (CaptchaServiceException e) {
			response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		System.out.println("resImg..");
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
	
	*//**
	 * 适用ajax异步请求判断验证码对与不对
	 *//*
	public String ajaxCheckCode() throws Exception{
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String code = request.getParameter("code");
		System.out.println("code:"+code);
		String result="true";
		try{
			if (!captchaService.validateResponseForID(request.getSession().getId(),code)){
				//验证码不正确
				result="false";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out=response.getWriter();
		out.write(result);
		System.out.println("result..."+result);
		return null;
	}
*/
}
