package resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Exceptions.IncorrectOrderException;
import Exceptions.MakeOrderException;
import Exceptions.NotAcceptAccessTokenException;
import Exceptions.SQLWorkException;
import Items.http.request.Order;
import Items.http.response.MakeOrderResponse;
import Items.sql.SqlOrder;
import SQL.SqlFunctions;
import cache.control.CacheMenu;

@Path("/makeOrder/")
public class MakeOrder extends BaseResource{

	private static final Logger log = LogManager.getLogger(MakeOrder.class);
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MEDIA_TYPE_JSON)
	public MakeOrderResponse makeOrder(Order order) throws IncorrectOrderException, NotAcceptAccessTokenException, SQLWorkException {
		if (order == null)
			throw new IncorrectOrderException("syntaxis error");
		order.isNotNull();

		long userID = SqlFunctions.getUserId(order.accessToken);
		double cost = CacheMenu.getCost(order.items);
		SqlOrder sqlOrder = new SqlOrder();
		sqlOrder.cost = cost;
		sqlOrder.finishTime = new Date(order.finishTime);
		log.info("Convert Date: " + sqlOrder.finishTime.toString());
		double sale = SqlFunctions.getProfile(order.accessToken).sale;
		sqlOrder.sale = sale;

		sqlOrder.userId = userID;
		sqlOrder.setJsonOrder(order.items);
		MakeOrderResponse response = new MakeOrderResponse();
		try {
			SqlFunctions.makeOrder(sqlOrder);
		} catch (MakeOrderException e) {
			response.success = false;
			response.errorMessage = e.getMessage();
			return response;
		}
		response.cost = cost;
		response.sale = sale;
		response.success = true;

		return response;
	}
}
