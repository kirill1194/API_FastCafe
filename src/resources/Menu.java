package resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.SQLWorkException;
import Items.MenuItem;
import SQL.SqlFunctions;
import Services.LOG;

import com.google.gson.Gson;
import com.google.gson.JsonArray;


@Path("/getMenu/")
public class Menu extends BaseResource{

	private static Logger log = LogManager.getLogger(Menu.class);

	//	@GET
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public MenuItem test() {
	//		MenuItem item = new MenuItem("one", "two", "tralal", "ololo", "tsgfd");
	//		return item;
	//	}

	@GET
	@Produces(MEDIA_TYPE_JSON)
	public String getMenu() throws SQLWorkException {
		requestLog(log);

		//		response.setCharacterEncoding("UTF-8");
		ArrayList<MenuItem> objectResponse = SqlFunctions.getMenu();
		//здесь костыль для правильного вывода JSONa. Сама либа не может правильно его сделать
		//ну или я не могу заставить ее это делать. хз
		JsonArray jsonArr = new JsonArray();
		Gson gson = new Gson();
		for (MenuItem item : objectResponse) {
			jsonArr.add(gson.toJsonTree(item));
		}

		LOG.responseLog(log, "menu is very large, so I will not write it to a log. Menu Count: " + objectResponse.size() + '\n');


		return jsonArr.toString();
	}

}
