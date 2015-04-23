package Exceptions;

import org.apache.logging.log4j.Logger;

public class NotAcceptAccessTokenException extends BaseException {
	private static final long serialVersionUID = 5278284424122850133L;

	private String accessToken;
	public NotAcceptAccessTokenException(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String getMessage() {
		return "bad access token: " + accessToken;
	}

	@Override
	public int getStatus() {
		return 401;
	}

	@Override
	public void writeToLog(Logger log) {
		log.error("status: " + getStatus() + "; " + "bad access token: " + accessToken + '\n');
	}

}
