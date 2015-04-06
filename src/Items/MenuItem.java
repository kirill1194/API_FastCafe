package Items;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MenuItem {


	public String article;
	public String name;
	public String category;
	public String img;
	public String description;
	public ArrayList<PriceItem> prices;
	public ArrayList<ToppingItem> toppings;

	//
	//	private static final String ARTICLE = "article";
	//	private static final String CATEGORY = "category";
	//	private static final String NAME = "name";
	//	private static final String IMG = "img";
	//	private static final String DESCRIPTION = "description";
	//	private static final String PRICE = "price";
	//	private static final String SIZE = "size";
	//	private static final String TOPPINGS = "toppings";
	//	private static final String ID = "ID";


	//	public MenuItem(JSONObject json) throws JSONException {
	//		article = json.getString(ARTICLE);
	//		category = json.getString(CATEGORY);
	//		name = json.getString(NAME);
	//		img =json.getString(IMG);
	//		description = json.getString(DESCRIPTION);
	//
	//		prices = new ArrayList<MenuItem.PriceItem>();
	//		JSONArray jsonPrices = json.getJSONArray(PRICE);
	//		for (int i=0; i<jsonPrices.length(); i++) {
	//			PriceItem priceItem = new PriceItem();
	//			priceItem.size = jsonPrices.getJSONObject(i).getInt(SIZE);
	//			priceItem.price = jsonPrices.getJSONObject(i).getDouble(PRICE);
	//			prices.add(priceItem);
	//		}
	//
	//		toppings = new ArrayList<ToppingItem>();
	//		JSONArray jsonToppings = json.getJSONArray(TOPPINGS);
	//		for (int i=0; i<jsonToppings.length(); i++) {
	//			ToppingItem toppingItem = new ToppingItem();
	//			toppingItem.ID = jsonToppings.getJSONObject(i).getInt(ID);
	//			toppingItem.name = jsonToppings.getJSONObject(i).getString(NAME);
	//			toppingItem.price = jsonToppings.getJSONObject(i).getDouble(PRICE);
	//			toppings.add(toppingItem);
	//
	//		}
	//	}


	public MenuItem() {}

	public MenuItem(String _article, String _name, String _category, String _img, String _description) {
		article = _article;
		name = _name;
		category = _category;
		img = _img;
		description = _description;
		prices = new ArrayList<PriceItem>();
		toppings = new ArrayList<ToppingItem>();
	}

	@Deprecated
	public MenuItem(String _article, String _name, String _category, String _img, String _description, double _price) {
		article = _article;
		name = _name;
		category = _category;
		img = _img;
		description = _description;
		prices = new ArrayList<PriceItem>();
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
