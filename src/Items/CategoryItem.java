package Items;

import twitter4j.JSONException;
import twitter4j.JSONObject;


public class CategoryItem {
	public int id;
	public String name;

	private static final String NAME = "name";
	private static final String ID = "id";

	public CategoryItem(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public CategoryItem(JSONObject json) throws JSONException {
		this.name = json.getString(NAME);
		this.id = json.getInt(ID);
	}
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(NAME, name);
			jsonObject.put(ID, id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}
}
