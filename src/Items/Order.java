package Items;

import java.util.Date;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {

	@XmlAttribute(name="access_token")
	public String accessToken;

	@XmlAttribute(name="creation_time")
	public Date creationTime;

	@XmlAttribute(name="finish_time")
	public Date finishTime;

	public LinkedList<OrderItem> items;

	public Order(){}


}
