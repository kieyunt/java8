package com.ky.spring.exception;

public class SpringException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String exceptionMsg;
	
	public SpringException(String exceptionMsg) {
		this.setExceptionMsg(exceptionMsg);
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
}
