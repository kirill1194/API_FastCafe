package resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;

import Services.LOG;

public class BaseResource {
	final public static  String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON + ";charset=utf-8";

	@Context public HttpServletRequest request;

	public static final String ACCESS_TOKEN = "access_token";

	public void requestLog(Logger log) {
		LOG.requestLog(log, request);
	}

}
