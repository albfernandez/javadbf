package com.linuxense.javadbf;

public class DBFFieldNotFoundException extends DBFException {

	private static final long serialVersionUID = -6346458838529022854L;

	public DBFFieldNotFoundException(String message) {
		super(message);
	}

	public DBFFieldNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBFFieldNotFoundException(Throwable cause) {
		super(cause);
	}

}
