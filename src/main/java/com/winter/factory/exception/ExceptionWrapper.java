package com.winter.factory.exception;

public class ExceptionWrapper {
	
	public RuntimeException wrappedException(Exception ex) {
		RuntimeException exceptionToThrow = new RuntimeException(ex.getMessage());
		exceptionToThrow.initCause(ex);
		ex.printStackTrace();
		return exceptionToThrow;
	}

}
