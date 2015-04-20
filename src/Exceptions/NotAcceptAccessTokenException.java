package Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotAcceptAccessTokenException extends WebApplicationException {
	public NotAcceptAccessTokenException() {
		super(Response.status(404).entity("not accept access_token").
				type(MediaType.TEXT_PLAIN).build());
	}

}
