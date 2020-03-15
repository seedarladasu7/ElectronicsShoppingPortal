package com.portal.shopping.electronics.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException{
	
	public InvalidInputException(String exDesc) {
		super(exDesc);
	}

}
