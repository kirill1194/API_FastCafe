package providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;

public class ExceptionProviderSQLWorkException implements ExceptionMapper<SQLWorkException> {

	private static final Logger log = LogManager.getLogger(ExceptionProviderBaseException.class);

	@Override
	public Response toResponse(SQLWorkException ex) {
		log.error("EXCEPTION. ("+ex.getClass().getName() + '\n' +
				"\t message: " + ex.getMessage() + '\n' +
				"\t IN: " + ex.getLocation());
		ex.writeToLog(log);
		return Response.status(ex.getStatus()).
				entity(ex.getMessage()).
				type(ex.getType()).
				build();
	}


}
