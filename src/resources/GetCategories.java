package resources;


import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import Exceptions.SQLWorkException;
import Items.http.response.CategoryItem;
import SQL.SqlFunctions;

@Path("/getCategories/")
public class GetCategories extends BaseResource {

	//	private static final Logger log = LogManager.getLogger(GetCategories.class);

	@GET
	@Produces(MEDIA_TYPE_JSON)
	public ArrayList<CategoryItem> getCategories() throws SQLWorkException {
		//		requestLog(log);

		ArrayList<CategoryItem> objectResponse = SqlFunctions.getCategories();

		//		LOG.responseLog(log, objectResponse);
		return objectResponse;
	}

}
