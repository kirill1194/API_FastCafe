package Exceptions;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public class IncorrectOrderException extends BaseException {

	private static final long serialVersionUID = 1L;

	private String message;

	private static final Gson gson = new Gson();
	private static final int status = 400;
	public IncorrectOrderException(String message, Object obj) {
		this.message = message + " in object: \n\t " + gson.toJson(obj);

	}

	public IncorrectOrderException(String message) {
		this.message = message;
	}

	@Override
	public void writeToLog(Logger log) {
		String logMessage = "";
		logMessage += "Error. Http status " + status + '\n';
		logMessage += "Error in order: " + message + '\n';
		log.error(logMessage);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getStatus() {
		return status;
	}
}
