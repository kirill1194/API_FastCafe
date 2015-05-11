package Items.http.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderItem {
	public Long article;
	public Integer[] toppingsIds;
	public Integer size;
	public Integer count;
	public String comment;

	public OrderItem(){}
}

