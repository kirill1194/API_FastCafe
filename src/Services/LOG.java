package Services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class LOG {

	private static Gson printableGson = new GsonBuilder()
	.setPrettyPrinting()
	.create();

	private static String getParameters(HttpServletRequest request) {
		StringBuilder result = new StringBuilder();
		Map<String, String[]> parameters = request.getParameterMap();
		for (String name : parameters.keySet()) {
			result.append('\t' + name + " : ");
			for (String parameter : parameters.get(name))
				result.append(parameter);
			result.append('\n');
		}
		return result.toString();
	}

	public static String notExistPar(String par) {
		return "don't exist parameter \"" + par + "\"";
	}

	public static void requestLog(Logger log, HttpServletRequest request) {
		log.info(request.getRequestURI() + " new request:\n" + getParameters(request));
	}

	public static void responseLog(Logger log, String message) {
		log.info("response:\n" + message);
	}

	public static void responseLog(Logger log, Object ObjectResponse) {
		String logMessage = "response:\n" + printableGson.toJson(ObjectResponse);

		log.info(logMessage.toString());
	}

}
