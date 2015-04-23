package resources;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import Exceptions.SQLWorkException;
import Items.MenuItem;
import cache.control.CacheMenu;

@Path("/getMenu/")
public class GetMenu extends BaseResource{

	//	private static Logger log = LogManager.getLogger(GetMenu.class);


	@GET
	@Path("{par}")
	@Produces(MEDIA_TYPE_JSON)
	public LinkedList<MenuItem> getMenuByCategory(@PathParam("par") String category) throws SQLWorkException {

		//		requestLog(log);

		int categoryInt = Integer.parseInt(category);
		LinkedList<MenuItem> objectResponse = CacheMenu.getMenuByCategory(categoryInt);

		//		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');
		return objectResponse;
		//return arrayListToJsonArrayString(objectResponse);
	}

	@GET
	@Produces(MEDIA_TYPE_JSON)
	public ArrayList<MenuItem> getMenu() throws SQLWorkException {

		//		requestLog(log);

		ArrayList<MenuItem> objectResponse = CacheMenu.getMenu();

		//		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');

		return objectResponse;
	}



}
