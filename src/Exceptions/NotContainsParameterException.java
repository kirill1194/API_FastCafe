package Exceptions;

import org.apache.logging.log4j.Logger;

public class NotContainsParameterException extends BaseException{

	private static final long serialVersionUID = 1L;

	private String parameterName;


	public NotContainsParameterException(String parameterName) {
		this.parameterName = parameterName;
	}

	@Override
	public String getMessage() {
		return "request not contains parameter \"" + parameterName + "\"";
	}

	@Override
	public int getStatus() {
		return 400;
	}

	@Override
	public void writeToLog(Logger log) {
		log.error("status: " + getStatus() + "; " + getMessage() + '\n');

	}


}
