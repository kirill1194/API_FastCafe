package Items;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import Exceptions.IncorrectOrderException;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
public class Order {

	@SerializedName("access_token")
	public String accessToken;

	//DATE!!!
	@SerializedName("finish_time")
	public Long finishTime;
	public Double cost;
	public Double sale;
	public ArrayList<OrderItem> items;

	public Order(){}

	public boolean isNotNull() throws IncorrectOrderException {
		if (accessToken == null)
			throw new IncorrectOrderException("not contains access_token");
		if (finishTime == null)
			throw new IncorrectOrderException("not contains finish_time");
		if (items == null)
			throw new IncorrectOrderException("not contains items");
		if (items.size() == 0)
			throw new IncorrectOrderException("items dont contain orderItems");
		//		if (cost == null)
		//			throw new IncorrectOrderException("not contains cost");
		//		if (sale == null)
		//			throw new IncorrectOrderException("not contains sale");
		for (OrderItem orderItem : items) {
			if (orderItem.article == null)
				throw new IncorrectOrderException("not contains article", orderItem);
			if (orderItem.count==null)
				throw new IncorrectOrderException("not contains count", orderItem);
			if (orderItem.size == null)
				throw new IncorrectOrderException("not contains size", orderItem);
			if (orderItem.toppingsIds == null)
				throw new IncorrectOrderException("not contauns toppings", orderItem);
			for (Integer toppingId: orderItem.toppingsIds) {
				if (toppingId == null)
					throw new IncorrectOrderException("incorrect toppings id", orderItem);
			}
		}
		return true;
	}

}
