package resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Items.CategoryItem;
import SQL.SqlFunctions;
import Services.LOG;

@Path("/getCategories/")
public class GetCategories extends BaseResource {

	private static final Logger log = LogManager.getLogger(GetCategories.class);

	@GET
	@Produces(MEDIA_TYPE_JSON)
	public ArrayList<CategoryItem> getCategories() {
		requestLog(log);

		ArrayList<CategoryItem> objectResponse = SqlFunctions.getCategories();

		LOG.responseLog(log, objectResponse);
		return objectResponse;
	}

}
