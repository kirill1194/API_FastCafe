package providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.BaseException;

@Provider
public class ExceptionProviderBaseException implements ExceptionMapper<BaseException> {

	private static final Logger log = LogManager.getLogger(ExceptionProviderBaseException.class);

	@Override
	public Response toResponse(BaseException ex) {
		String errorMessage = "EXCEPTION. ("+ex.getClass().getName() + '\n' +
				"\t message: " + ex.getMessage() + '\n' +
				"\t IN: " + ex.getStackTrace()[0].toString();
		//		for (int i=0; i<5 && i<ex.getStackTrace().length; i++)
		//			errorMessage += ex.getStackTrace()[i].toString() + '\n';
		log.error(errorMessage);
		//ex.writeToLog(log);
		return Response.status(ex.getStatus()).
				entity(ex.getMessage()).
				type(ex.getType()).
				build();
	}

}
