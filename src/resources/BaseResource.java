package resources;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;

import Exceptions.NotContainsParameterException;
import Exceptions.RequestException;
import Services.LOG;

import com.oreilly.servlet.MultipartRequest;

public class BaseResource {
	final public static  String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON + ";charset=utf-8";

	@Context public HttpServletRequest request;

	public static final String ACCESS_TOKEN = "access_token";
	public static final String USER_ID = "user_ID";
	public static final String TOKEN = "token";
	public static final String PHONE = "phone";

	public void requestLog(Logger log) {
		LOG.requestLog(log, request);
	}

	private String getParameter(String parameter) throws RequestException{
		String result = request.getParameter(parameter);
		if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
			try {
				MultipartRequest t = null;
				try {
					t = new MultipartRequest(request, "/home/kirill/logs/fastCafe_logs");
				} catch (IOException e) {
					t = new MultipartRequest(request, "/Users/Kirill/logs/fastCafe");
				}

				result = t.getParameter(parameter);
			} catch (IOException e) {
				return null;
			}
		}
		return result;
	}

	public void checkParameter(String parameterName, Logger log) throws NotContainsParameterException {
		if (getParameter(parameterName) == null) {
			log.warn(request.getRequestURI() + " " + LOG.notExistPar(parameterName) + '\n');
			throw new NotContainsParameterException(parameterName);
		}
	}

}
