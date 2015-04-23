package providers;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class ExceptionProviderWebApplicationException  implements ExceptionMapper<WebApplicationException>{

	private static final Logger log = LogManager.getLogger(ExceptionProviderWebApplicationException.class);

	@Override
	public Response toResponse(WebApplicationException ex) {
		Response response = ex.getResponse();

		//log.error("error: response status " + response.getStatus() + '\n');
		return response;

	}

}
