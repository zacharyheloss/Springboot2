package com.zachary.springboot.blog.pushlian.util;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zachary.springboot.blog.pushlian.exception.BusinessException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResultCode<T> implements Serializable {
	private static final long serialVersionUID = -4859637902882356019L;
	public static final String CODE_SUCCESS = "0";
	public static final String CODE_FAIL = "-1";
	public static final String BUSS_FAIL = "-2";
	public static final String OTHER_FAIL = "-3";
	public static final String SERVER_STOP = "-6";

	private ResultCode(String code, String errmsg, T retval, boolean isSuccess) {
		this.errcode = code;

		this.success = isSuccess;

		this.errmsg = errmsg;

		this.retval = retval;
	}

	public static <T> ResultCode<T> getFailure(String code, String errmsg) {
		return new ResultCode(StringUtils.isEmpty(code) ? "-1" : code, errmsg, null, false);
	}

	public static <T> ResultCode<T> getFailureReturn(String code, String errmsg, T retval) {
		return new ResultCode(StringUtils.isEmpty(code) ? "-1" : code, errmsg, retval, false);
	}

	public static <T> ResultCode<T> getFailureException(Exception e) {
		return getFailure(null, "处理失败，返回系统异常");
	}

	public static <T> ResultCode<T> getFailureBusiException(BusinessException e) {
		return getFailure(e.getCode(), e.getMessage());
	}

	public static void main(String[] args) {
		getFailureException(new RuntimeException("cdcs"));
	}

	public static <T> ResultCode<T> getSuccess(String code, String errmsg) {
		return new ResultCode(StringUtils.isEmpty(code) ? "0" : code, errmsg, null, true);
	}

	public static <T> ResultCode<T> getSuccessReturn(String code, String errmsg, T retval) {
		return new ResultCode(StringUtils.isEmpty(code) ? "0" : code, errmsg, retval, true);
	}

	public static String gsonToString(Object obj) {
		return gson.toJson(obj);
	}

	public boolean isSuccess() {
		return this.success;
	}

	public Object getRetval() {
		return this.retval;
	}

	public String getErrmsg() {
		return this.errmsg;
	}

	public String getErrcode() {
		return this.errcode;
	}

	public static final ResultCode<String> SUCCESS = new ResultCode("0", "ok", null, true);
	public static final ResultCode<String> FAIL = new ResultCode("-1", "error", null, false);
	private String errcode;
	private String errmsg;
	private T retval;
	private boolean success;
	private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping()
			.setPrettyPrinting().create();
}
