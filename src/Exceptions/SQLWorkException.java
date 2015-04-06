package Exceptions;

import java.sql.SQLException;

public class SQLWorkException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode = 0;
	private String errorMessage = "";

	public SQLWorkException(SQLException e) {
		errorCode = e.getErrorCode();
		errorMessage = "error code: " + errorCode + "; " + e.getMessage();
	}

	public SQLWorkException(int _errorCode) {
		errorCode = _errorCode;
	}
	@Override
	public String getMessage() {
		return errorMessage;
	}

	public SQLWorkException(int _errorCode, String message) {
		errorCode = _errorCode;
		errorMessage = message;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
