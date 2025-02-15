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

	public static <T> HttpResult<T> buildResult(HttpStatus httpStatus){
		return new HttpResult<>(httpStatus);
	}

	public static  <T> HttpResult<T> buildResult(HttpStatus httpStatus,T data){
		return new HttpResult<>(httpStatus,data);
	}

	public static <T> HttpResult<T> SUCCESS() {
		return new HttpResult<>(HttpStatus.SUCCESS,null);
	}

	public static <T> HttpResult<T> SUCCESS(T data) {
		return new HttpResult<>(HttpStatus.SUCCESS,data);
	}

	public static <T> HttpResult<T> NOLOGIN() {
		return new HttpResult<>(HttpStatus.NOLOGIN,null);
	}

	public static <T> HttpResult<T> FAILURE() {
		return new HttpResult<>(HttpStatus.FAILURE,null);
	}

	public static <T> HttpResult<T> FAILURE(T data) {
		return new HttpResult<>(HttpStatus.FAILURE,data);
	}

	public static <T> HttpResult<T> FAILURE(String msg,T data) {
		return new HttpResult<>(HttpStatus.FAILURE.getStatusCode(),msg,data);
	}

	public static <T> HttpResult<T> FAILURE_MSG(String msg) {
		return new HttpResult<>(400,msg);
	}

	public static <T> HttpResult<T> EXCEPTION(String msg) {
		return new HttpResult<>(400,msg);
	}

	public static <T> HttpResult<T> EXCEPTION(T data) {
		return new HttpResult<>(HttpStatus.EXCEPTION,data);
	}

	public static boolean isOk(HttpResult httpResult) {
		if(httpResult!=null && httpResult.resultCode==200){
			return true;
		}
		return false;
	}
}
