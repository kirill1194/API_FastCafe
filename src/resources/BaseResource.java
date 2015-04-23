package resources;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.Logger;

import Exceptions.NotContainsParameterException;
import Exceptions.RequestException;
import Services.LOG;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.oreilly.servlet.MultipartRequest;

public class BaseResource {
	final public static  String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON + ";charset=utf-8";

	@Context public HttpServletRequest request;

	public static final String ACCESS_TOKEN = "access_token";
	public static final String USER_ID = "user_ID";
	public static final String TOKEN = "token";
	public static final String PHONE = "phone";
	public final static String CATEGORY = "category";

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

	public void checkParameter(String parameterName, String parameter, Logger log) throws NotContainsParameterException {
		if (parameter == null) {
			log.warn(request.getRequestURI() + " " + LOG.notExistPar(parameterName) + '\n');
			throw new NotContainsParameterException(parameterName);
		}
	}

	public String arrayListToJsonArrayString(ArrayList<?> arr) {
		//здесь костыль для правильного вывода JSONa. Сама либа не может правильно его сделать
		//ну или я не могу заставить ее это делать. хз
		JsonArray jsonArr = new JsonArray();
		Gson gson = new Gson();
		for (int i=0; i<arr.size(); i++) {
			jsonArr.add(gson.toJsonTree(arr.get(i)));
		}

		return jsonArr.toString();
	}

}
