package Exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionProcessing {

	private static Logger log = LogManager.getLogger(ExceptionProcessing.class);

	public static void processingSQLWorkException(SQLWorkException e) throws RequestException {
		log.error("sql code: " + e.getErrorCode(), e);
		if (e.getErrorCode() == 401) {
			throw new RequestException(401, "");
		}
		throw new RequestException(500, "sql error: " + e.getMessage());
	}
}
