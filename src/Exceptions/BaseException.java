package Exceptions;

import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;

public abstract class BaseException extends Exception {
	private static final long serialVersionUID = -6853253225255045267L;

	@Override
	public abstract String getMessage();
	public abstract int getStatus();
	public abstract void writeToLog(Logger log);

	public String getType() {
		return MediaType.TEXT_PLAIN;
	}
}
