package filters;




import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.ReaderWriter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class RequestLogger implements ContainerRequestFilter {
	private static final String PREFIX = "\t- ";
	private static final Logger log = LogManager.getLogger(RequestLogger.class);
	@Override
	public ContainerRequest filter(ContainerRequest request) {

		try {
			StringBuilder builder = new StringBuilder("");
			builder.append("new request: \n");
			builder.append(PREFIX + request.getAbsolutePath().toString() + " " + request.getMethod() + '\n');
			if (request.getMediaType() != null)
				builder.append(PREFIX + "Media-Type: " + request.getMediaType().toString() + '\n');

			//form parameters
			Form form = request.getFormParameters();
			if (form.size() != 0)
				builder.append(PREFIX + "form parameters: " + '\n');
			else if (request.getMethod().equals("POST"))
				builder.append(PREFIX + "not exist form parameters" + '\n');
			for (String key : form.keySet()) {
				builder.append('\t' + PREFIX + key + " = " + form.getFirst(key) + '\n');
			}

			//query parameters
			MultivaluedMap<String, String> queryPar = request.getQueryParameters();
			if (queryPar.size() != 0)
				builder.append(PREFIX + "query parameters: \n");
			else if (request.getMethod().equals("GET"))
				builder.append(PREFIX + "not exist query parameters" + '\n');
			for (String key : queryPar.keySet()) {
				builder.append('\t' + PREFIX + key + " = " + queryPar.get(key) + '\n');
			}

			//entity JSON
			if (request.getMediaType()!= null &&
					request.getMediaType().toString().startsWith((MediaType.APPLICATION_JSON))) {
				builder.append(PREFIX + "entity: \n");

				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputStream in = request.getEntityInputStream();
				final StringBuilder b = new StringBuilder();
				try {
					if (in.available() > 0) {
						ReaderWriter.writeTo(in, out);

						byte[] requestEntity = out.toByteArray();
						if (requestEntity.length != 0)
							b.append(new String(requestEntity)).append("\n");

						request.setEntityInputStream(new ByteArrayInputStream(requestEntity));
					}
				} catch (IOException e) {
					log.error(e);
				}
				builder.append('\t' + PREFIX + b.toString() + '\n');
				//				BufferedReader reader = new BufferedReader(new InputStreamReader(request.getEntityInputStream()));
				//				while (reader.ready())
				//					builder.append(reader.readLine());
				//				reader.close();
				//				request.getEntityInputStream().reset();

			}
			if (builder.charAt(builder.length()-1) == '\n')
				builder.deleteCharAt(builder.length()-1);

			log.info(builder.toString());
		} catch (Throwable ex) {
			log.error("Exception in Requestost", ex);
		}
		return request;
	}

}
