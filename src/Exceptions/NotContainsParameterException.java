package Exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotContainsParameterException extends WebApplicationException{

	private static final long serialVersionUID = 1L;

	private String parameterName;

	public NotContainsParameterException(String parameterName) {
		super(Response.status(400).entity("request not contains parameter \"" + parameterName + "\"").
				type(MediaType.TEXT_PLAIN).build());
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return parameterName;
	}


}
