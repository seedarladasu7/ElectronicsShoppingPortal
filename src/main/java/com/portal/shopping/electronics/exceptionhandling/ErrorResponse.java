package com.portal.shopping.electronics.exceptionhandling;

import java.util.List;

public class ErrorResponse {
	
	private String exceptionType;
	private List<String> details;
	
	public ErrorResponse() {
		
	}
	
	public ErrorResponse(String exceptionType, List<String> details) {
		super();
		this.exceptionType = exceptionType;
		this.details = details;
	}


	public String getExceptionType() {
		return exceptionType;
	}
	
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	public List<String> getDetails() {
		return details;
	}
	
	public void setDetails(List<String> details) {
		this.details = details;
	}
	

}
