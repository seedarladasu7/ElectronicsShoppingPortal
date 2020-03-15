package com.portal.shopping.electronics.exceptionclasses;

@SuppressWarnings("serial")
public class RecordInsertionException extends RuntimeException{
	
	public RecordInsertionException(String exDesc) {
		super(exDesc);
	}

}
