package com.portal.shopping.electronics.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException{
	
	public InvalidUserException(String exDesc) {
		super(exDesc);
	}

}
