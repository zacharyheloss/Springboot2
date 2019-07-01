package com.zachary.springboot.blog.pushlian.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author zhang
 * @desc http操作
 *
 */
public class HttpResult {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, String> responseHeader = new LinkedHashMap();
	private boolean success = true;
	private Object response;
	private String msg = "操作成功!";
	private Integer status = Integer.valueOf(200);

	public HttpResult(boolean success, Object response, String msg) {
		this.success = success;
		this.response = response;
		if (StringUtils.isNotBlank(msg)) {
			this.msg = msg;
		}
	}

	public HttpResult(boolean success, Object response, String msg, Integer status) {
		this.success = success;
		this.response = response;
		if (StringUtils.isNotBlank(msg)) {
			this.msg = msg;
		}
		if (status != null) {
			this.status = status;
		}
	}

	public Map<String, String> getResponseHeader() {
		return this.responseHeader;
	}

	public void setResponseHeader(Map<String, String> responseHeader) {
		this.responseHeader = responseHeader;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getResponse() {
		return this.response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
