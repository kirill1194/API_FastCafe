package Exceptions;

import java.sql.SQLException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SQLWorkException extends WebApplicationException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int sqlErrorCode = 0;
	private String errorMessage = "";

	public SQLWorkException(SQLException e) {
		super(Response.status(500).entity("error code: " + e.getErrorCode() + "; " + e.getMessage()).
				type(MediaType.TEXT_PLAIN).build());
		sqlErrorCode = e.getErrorCode();
		errorMessage = "error code: " + sqlErrorCode + "; " + e.getMessage();

	}

	public SQLWorkException(int _errorCode) {
		super(Response.status(500).entity("error code: " + _errorCode + ";").
				type(MediaType.TEXT_PLAIN).build());
		sqlErrorCode = _errorCode;
	}
	@Override
	public String getMessage() {
		return errorMessage;
	}

	public SQLWorkException(int _errorCode, String message) {
		super(Response.status(500).entity("error code: " + _errorCode + "; " + message).
				type(MediaType.TEXT_PLAIN).build());
		sqlErrorCode = _errorCode;
		errorMessage = message;
	}

	public int getErrorCode() {
		return sqlErrorCode;
	}
}
