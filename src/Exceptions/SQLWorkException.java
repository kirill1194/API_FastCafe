package Exceptions;

import java.sql.SQLException;

import org.apache.logging.log4j.Logger;

public class SQLWorkException extends BaseException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String location = null;
	private int sqlErrorCode = 0;
	private String errorMessage = "";

	public SQLWorkException(SQLException e) {

		sqlErrorCode = e.getErrorCode();
		errorMessage = "error code: " + sqlErrorCode + "; " + e.getMessage();
		location = e.getStackTrace()[0].toString();
	}

	public SQLWorkException(int _errorCode) {
		sqlErrorCode = _errorCode;
	}
	@Override
	public String getMessage() {
		return errorMessage;
	}

	public SQLWorkException(int _errorCode, String message) {

		sqlErrorCode = _errorCode;
		errorMessage = message;
	}

	public int getErrorCode() {
		return sqlErrorCode;
	}

	@Override
	public int getStatus() {
		return 500;
	}

	@Override
	public void writeToLog(Logger log) {
		log.error("SqlException: " + getMessage() + '\n');

	}

	public String getLocation() {
		return location;
	}
}
