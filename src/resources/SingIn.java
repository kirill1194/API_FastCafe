package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;
import SQL.SqlFunctions;
import Services.LOG;
import Services.TwitterVerification;

import com.google.gson.JsonObject;
import com.sun.jersey.multipart.FormDataParam;

@Path("/signIn/")
public class SingIn extends BaseResource {
	private static final Logger log = LogManager.getLogger(SingIn.class);

	@POST
	@Produces(MEDIA_TYPE_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String signIn(
			@FormDataParam("user_ID") long userID,
			@FormDataParam("token") String token,
			@FormDataParam("phone") String phone
			) throws SQLWorkException {
		boolean verify = false;
		try {
			verify = TwitterVerification.verify(token, userID);
		}
		catch (StringIndexOutOfBoundsException e) {
			log.info("can't parse token.");
		}

		if (verify == false)
			log.info("Twitter verification don't accepted.");

		JsonObject JSONResponse = new JsonObject();
		if (verify) {
			String accessToken = SqlFunctions.signIn(userID, phone);
			JSONResponse.addProperty(ACCESS_TOKEN, accessToken);
		} else {
			JSONResponse.addProperty(ACCESS_TOKEN, "0");
		}
		LOG.responseLog(log, JSONResponse);
		return JSONResponse.toString();
	}
}
