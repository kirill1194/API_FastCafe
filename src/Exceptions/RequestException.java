package Exceptions;

public class RequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMessage;
	
	public RequestException(int code, String message) {
		errorCode = code;
		errorMessage = message;
	}
	@Override
	public String getMessage() {
		return errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
