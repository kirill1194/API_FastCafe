package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.NotContainsParameterException;
import Exceptions.RequestException;
import Exceptions.SQLWorkException;
import Items.SqignInResponse;
import SQL.SqlFunctions;
import Services.TwitterVerification;

@Path("/signIn/")
public class SingIn extends BaseResource {
	private static final Logger log = LogManager.getLogger(SingIn.class);



	@POST
	@Produces(MEDIA_TYPE_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public SqignInResponse signInFormUrlencoded(
			@FormParam(USER_ID) String userID,
			@FormParam(TOKEN) String token,
			@FormParam(PHONE) String phone
			) throws SQLWorkException, NotContainsParameterException {
		return signInBase(userID, token, phone);
	}


	//	@POST
	//	@Produces(MEDIA_TYPE_JSON)
	//	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//	public String signInFormData(
	//			@FormDataParam(USER_ID) String userID,
	//			@FormDataParam(TOKEN) String token,
	//			@FormDataParam(PHONE) String phone
	//			) {
	//		return signInBase(userID, token, phone);
	//	}


	public SqignInResponse signInBase(String userIDString, String token, String phone) throws NotContainsParameterException, SQLWorkException {

		checkParameter(PHONE, phone, log);
		checkParameter(TOKEN, token, log);
		checkParameter(USER_ID, userIDString, log);

		long userID = Long.parseLong(userIDString);

		boolean verify = false;
		try {
			verify = TwitterVerification.verify(token, userID);
		}
		catch (StringIndexOutOfBoundsException e) {
			log.warn("can't parse token.");
			throw new RequestException("can't parse token: \"" + token + "\"");
		}

		if (verify == false)
			log.info("Twitter verification don't accepted.");

		SqignInResponse accessTokenObj = new SqignInResponse();
		if (verify) {
			String accessToken = SqlFunctions.signIn(userID, phone);
			accessTokenObj.token = accessToken;
		} else {
			accessTokenObj.token = "0";
		}
		return accessTokenObj;
	}
}
