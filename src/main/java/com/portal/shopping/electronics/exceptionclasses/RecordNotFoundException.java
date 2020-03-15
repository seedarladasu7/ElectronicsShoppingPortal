package com.portal.shopping.electronics.exceptionclasses;

@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException{
	
	public RecordNotFoundException(String exDesc) {
		super(exDesc);
	}

}
