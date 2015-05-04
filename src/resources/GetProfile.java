package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.NotAcceptAccessTokenException;
import Exceptions.NotContainsParameterException;
import Exceptions.SQLWorkException;
import Items.ProfileItem;
import SQL.SqlFunctions;

@Path("/getProfile/")
public class GetProfile extends BaseResource {

	private static final Logger log = LogManager.getLogger(GetProfile.class);


	@GET
	@Produces(MEDIA_TYPE_JSON)
	public ProfileItem getProfile(@QueryParam(ACCESS_TOKEN) String accessToken) throws SQLWorkException, NotContainsParameterException, NotAcceptAccessTokenException {
		checkParameter(ACCESS_TOKEN, log);
		//		requestLog(log);

		ProfileItem profile = SqlFunctions.getProfile(accessToken);

		//		LOG.responseLog(log, profile);

		return profile;
	}

}
