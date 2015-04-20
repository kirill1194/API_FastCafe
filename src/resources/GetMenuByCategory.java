package resources;

import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Items.MenuItem;
import SQL.SqlFunctions;
import Services.LOG;

@Path("/getMenuByCategory/")
public class GetMenuByCategory extends BaseResource {

	private static Logger log = LogManager.getLogger(GetMenuByCategory.class);

	@GET
	@Consumes(MEDIA_TYPE_JSON)
	public LinkedList<MenuItem> getMenuByCategory(@QueryParam(CATEGORY) int category) {
		requestLog(log);

		LinkedList<MenuItem> objectResponse = SqlFunctions.getMenuByCategory(category);

		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');

		return objectResponse;
	}
}
