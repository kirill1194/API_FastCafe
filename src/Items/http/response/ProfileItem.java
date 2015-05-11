package Items.http.response;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ProfileItem {

	public String name;
	public String phone;
	public double sale;

	public ProfileItem() {}

	public ProfileItem(String name, String phone, double sale) {
		this.name = name;
		this.phone = phone;
		this.sale = sale;
		if (this.name == null)
			this.name = "";
	}
}