package net.diaowen.common.plugs.httpclient;

public class HttpResult<T> {

	private int resultCode;
	private String resultMsg;
	private T data;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public HttpResult(HttpStatus httpStatus){
		this.resultCode = httpStatus.getStatusCode();
		this.resultMsg = httpStatus.getMessage();
	}

	public HttpResult(HttpStatus httpStatus, T data){
		this.resultCode = httpStatus.getStatusCode();
		this.resultMsg = httpStatus.getMessage();
		if(data!=null){
			this.data = data;
		}
	}

	public HttpResult(int resultCode){
		this.resultCode = resultCode;
	}

	public HttpResult(int resultCode,String resultMsg){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public HttpResult(int resultCode, String resultMsg, T data){
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
		this.data = data;
	}

	public static HttpResult buildResult(HttpStatus httpStatus){
		return new HttpResult(httpStatus);
	}

	public static  <T> HttpResult buildResult(HttpStatus httpStatus,T data){
		return new HttpResult(httpStatus,data);
	}

	public static HttpResult SUCCESS() {
		return new HttpResult(HttpStatus.SUCCESS,null);
	}

	public static <T> HttpResult<T> SUCCESS(T data) {
		return new HttpResult(HttpStatus.SUCCESS,data);
	}

	public static HttpResult NOLOGIN() {
		return new HttpResult(HttpStatus.NOLOGIN,null);
	}

	public static HttpResult FAILURE() {
		return new HttpResult(HttpStatus.FAILURE,null);
	}

	public static <T> HttpResult FAILURE(T data) {
		return new HttpResult(HttpStatus.FAILURE,data);
	}

	public static <T> HttpResult FAILURE(String msg,T data) {
		return new HttpResult(HttpStatus.FAILURE.getStatusCode(),msg,data);
	}

	public static HttpResult FAILURE_MSG(String msg) {
		return new HttpResult(400,msg);
	}

	public static <T> HttpResult EXCEPTION(T data) {
		return new HttpResult(HttpStatus.EXCEPTION,data);
	}

}
