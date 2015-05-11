package Items.http.response;

import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MenuItem {


	public String article;
	public String name;
	public int category;
	public String img;
	public String description;
	public LinkedList<PriceItem> prices;
	public LinkedList<ToppingItem> toppings;



	public MenuItem() {}

	public MenuItem(String _article, String _name, int _category, String _img, String _description) {
		article = _article;
		name = _name;
		category = _category;
		img = _img;
		description = _description;
		prices = new LinkedList<PriceItem>();
		toppings = new LinkedList<ToppingItem>();
	}

	@Deprecated
	public MenuItem(String _article, String _name, int _category, String _img, String _description, double _price) {
		article = _article;
		name = _name;
		category = _category;
		img = _img;
		description = _description;
		prices = new LinkedList<PriceItem>();
	}

	public void addPrice(int size, double price) {
		PriceItem priceItem = new PriceItem();
		priceItem.price = price;
		priceItem.size = size;
		prices.add(priceItem);
	}

	public void addTopping(int toppingId, String name, double price) {
		ToppingItem toppingItem = new ToppingItem();
		toppingItem.ID = toppingId;
		toppingItem.name = name;
		toppingItem.price = price;
		this.toppings.add(toppingItem);
	}

	//	public JSONObject toJSONObject() throws JSONException {
	//		JSONObject ob = new JSONObject();
	//		ob.put(ARTICLE, article);
	//		ob.put(CATEGORY, category);
	//		ob.put(IMG, img);
	//		ob.put(DESCRIPTION, description);
	//		ob.put(NAME, name);
	//
	//		JSONArray jsonPrices = new JSONArray();
	//		for (PriceItem priceItem : prices) {
	//			JSONObject item = new JSONObject();
	//			item.put(PRICE, priceItem.price);
	//			item.put(SIZE, priceItem.size);
	//			jsonPrices.put(item);
	//		}
	//		ob.put("prices", jsonPrices);
	//
	//		JSONArray jsonToppings = new JSONArray();
	//		for (ToppingItem toppingItem : toppings) {
	//			JSONObject item = new JSONObject();
	//			item.put(ID, toppingItem.ID);
	//			item.put(NAME, toppingItem.name);
	//			item.put(PRICE, toppingItem.price);
	//			jsonToppings.put(item);
	//		}
	//		ob.put(TOPPINGS, jsonToppings);
	//		return ob;
	//	}

	//	@Override
	//	public String toString() {
	//		String s = article + ' ' + name + ' ' + category + ' ' + img + ' ' + description + ' ';
	//		for (PriceItem item : prices) {
	//			s += item.size + " - " + item.price + ' ';
	//		}
	//		return s;
	//	}
	//
	//
	//	@Override
	//	public String toJsonString() {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

}
