package com.zachary.springboot.blog.pushlian.exception;

/**
 * 
 * @author zachary
 * @desc 业务异常
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 2765439943243341557L;
	private String code;
	private String message;

	public BusinessException() {
	}

	public BusinessException(String msg) {
		super(msg);
		this.message = msg;
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.code = code;
		this.message = msg;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return getClass().getSimpleName() + "[" + this.code + "] " + getMessage();
	}
}
