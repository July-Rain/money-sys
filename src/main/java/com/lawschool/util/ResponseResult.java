package com.lawschool.util;

import java.io.Serializable;

public class ResponseResult implements Serializable{

	//自定义响应状态码
	private String responseCode;
	
	//响应的信息
	private String message;
	
	//响应的对象信息
	private Object returnObject;

	public ResponseResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
}
