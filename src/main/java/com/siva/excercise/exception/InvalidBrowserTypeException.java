package com.siva.excercise.exception;

@SuppressWarnings("serial")
public class InvalidBrowserTypeException extends Exception {

	public InvalidBrowserTypeException() {
	}

	public InvalidBrowserTypeException(String message) {
		super(message);
	}

	public InvalidBrowserTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidBrowserTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidBrowserTypeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
