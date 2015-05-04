package filters;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class ResponseLogger implements ContainerResponseFilter {

	private static final String PREFIX = "\t- ";
	private static final Logger log = LogManager.getLogger(ResponseLogger.class);
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		try {
			StringBuilder builder = new StringBuilder("response: " + '\n');
			builder.append(PREFIX + request.getAbsolutePath().toString() + " " + request.getMethod() + '\n');
			//status
			builder.append(PREFIX + "HTTP status: " + response.getStatus() + '\n');
			//Content-Type
			builder.append(PREFIX + "Content=type: " + response.getMediaType() + '\n');
			//entity
			Object objEntity = response.getEntity();
			if (objEntity instanceof Collection<?>) {
				Collection<?> col = (Collection<?>) objEntity;
				builder.append(PREFIX + "response: " + response.getEntityType().toString() + "; size: " + col.size());
			} else {
				String entity = gson.toJson(response.getEntity());
				builder.append(PREFIX + "response: " + entity);
			}

			if (builder.charAt(builder.length()-1) == '\n')
				builder.deleteCharAt(builder.length()-1);

			log.info(builder.toString());
		} catch (Throwable ex) {
			log.error("Exception in ResponseLog", ex);
		}
		return response;
	}

}
