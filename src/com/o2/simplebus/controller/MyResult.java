package com.o2.simplebus.controller;

public class MyResult {
	
	private int retcode ; 
	private String message ;
	
	public MyResult(int retCode,String message) {
		this.message = message;
		this.retcode = retcode;
	}
	
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public String getMessage() {
		if(message==null) {
			return "";
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
