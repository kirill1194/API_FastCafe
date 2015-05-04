package Items;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

public class SqlOrder {
	public double cost;
	public double sale;
	public long userId;
	public Date finishTime;
	public String jsonOrder;

	public SqlOrder(){}
	public void setJsonOrder(ArrayList<OrderItem> items) {
		final Gson gson = new Gson();
		jsonOrder = gson.toJson(items);
	}
}
