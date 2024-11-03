package net.diaowen.common.plugs.httpclient;

/**
 * CheckType
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public class HttpStatus {

	private int statusCode;
	private String message;
//	通用返回
	public static HttpStatus SUCCESS = new HttpStatus(200,"执行成功");//200 全部成功
	public static HttpStatus FAILURE = new HttpStatus(400,"执行失败");//200 执行失败
	public static HttpStatus NOLOGIN = new HttpStatus(401,"未登录");//200 执行失败
	public static HttpStatus SERVER_403 = new HttpStatus(403,"权限不够");//200 要失败
	public static HttpStatus EXCEPTION = new HttpStatus(500,"服务端异常");

//	业务返回

	/**
	 * 登录、注册、注册短信
	 */
	public static HttpStatus SERVER_30001 = new HttpStatus(30001,"账号、手机号或邮箱已存在");
	public static HttpStatus SERVER_30002 = new HttpStatus(30002,"账号不存在");
	public static HttpStatus SERVER_30003 = new HttpStatus(30003,"数据格式不合法");
	public static HttpStatus SERVER_30004 = new HttpStatus(30004,"用户密码错误");
	public static HttpStatus SERVER_30005 = new HttpStatus(30005,"短信验证码超时");
	public static HttpStatus SERVER_30006 = new HttpStatus(30006,"短信验证码不正确");
	public static HttpStatus SERVER_30007 = new HttpStatus(30007,"短信验证码未生成");
	public static HttpStatus SERVER_30008 = new HttpStatus(30008,"60秒内，不可以重复操作");
	public static HttpStatus SERVER_30009 = new HttpStatus(30009,"图形验证码不正确");

	/**
	 * 数据问题
	 */
	public static HttpStatus SERVER_10001 = new HttpStatus(10001,"项目来源不存在");

	private HttpStatus(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
