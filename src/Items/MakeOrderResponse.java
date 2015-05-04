package Items;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MakeOrderResponse {
	public double cost;
	public double sale;
	public boolean success;
	public String errorMessage;

}
