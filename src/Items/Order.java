package Items;

import java.util.Date;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	public long id;
	public String secret;
	public double price; //include sale
	public double sale;
	public Date creationTime;
	public Date finishTime;
	public LinkedList<OdreItem> items;



}
