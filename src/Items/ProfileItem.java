//package Items;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class ProfileItem {
//
//	final private static String NAME = "name";
//	final private static String PHONE = "phone";
//	final private static String SALE = "sale";
//
//	public String name;
//	public String phone;
//	public double sale;
//
//	public ProfileItem(String name, String phone, double sale) {
//		this.name = name;
//		this.phone = phone;
//		this.sale = sale;
//	}
//
//	public ProfileItem (JSONObject json) throws JSONException {
//		this.name = json.getString(NAME);
//		this.phone = json.getString(PHONE);
//		this.sale = json.getDouble(SALE);
//	}
//
//	public JSONObject toJSONObject() {
//		JSONObject result = new JSONObject();
//		try {
//			result.put(NAME, name);
//			result.put(PHONE, phone);
//			result.put(SALE, sale);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//
//}
