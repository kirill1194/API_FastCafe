package Items;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderItem {
	public long article;
	public int[] toppingsIds;
	public int size;
	public int count;

	public OrderItem(){}
}

