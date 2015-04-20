package resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;
import Items.MenuItem;
import SQL.SqlFunctions;
import Services.LOG;

@Path("/getMenu/")
public class GetMenu extends BaseResource{

	private static Logger log = LogManager.getLogger(GetMenu.class);


	@GET
	@Path("{par}")
	@Produces(MEDIA_TYPE_JSON)
	public ArrayList<MenuItem> getMenuByCategory(@PathParam("par") String category) {

		requestLog(log);

		int categoryInt = Integer.parseInt(category);
		ArrayList<MenuItem> objectResponse = SqlFunctions.getMenuByCategory(categoryInt);

		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');
		return objectResponse;
		//return arrayListToJsonArrayString(objectResponse);
	}

	@GET
	@Produces(MEDIA_TYPE_JSON)
	public ArrayList<MenuItem> getMenu() throws SQLWorkException {

		requestLog(log);

		ArrayList<MenuItem> objectResponse = SqlFunctions.getMenu();

		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');

		return objectResponse;
	}



}
