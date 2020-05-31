package com.pecc.dj.exam.exception;

import com.pecc.dj.exam.constant.ErrorDefEnum;

public class ExamException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String errCode;
	private String message;
	public ExamException(String message) {
        super(message);
    }
	public ExamException(String errCode, String message) {
		this.message = message;
		this.errCode = errCode;
	}
	public ExamException(ErrorDefEnum errEnum) {
		this.errCode = errEnum.getCode();
		this.message = errEnum.getMessage();
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
