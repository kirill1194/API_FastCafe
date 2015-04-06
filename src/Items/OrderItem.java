//package Items;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class OrderItem {
//	public int article;
//	public int count;
//
//	public OrderItem(JSONObject json) throws JSONException {
//		article = json.getInt("article");
//		count = json.getInt("count");
//	}
//
//	public JSONObject toJSONObject() throws JSONException {
//		JSONObject json = new JSONObject();
//		json.put("article", article);
//		json.put("count", count);
//		return json;
//
//	}
//}
