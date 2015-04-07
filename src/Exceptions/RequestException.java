package Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RequestException extends WebApplicationException{

	private static final long serialVersionUID = 1L;

	private String errorMessage;

	public RequestException(String message) {
		super(Response.status(400).entity(message).
				type(MediaType.TEXT_PLAIN).build());
		errorMessage = message;
	}
	@Override
	public String getMessage() {
		return errorMessage;
	}
}
