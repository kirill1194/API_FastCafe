package Items;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseItem {
	private static Gson printableGson = new GsonBuilder()
	.setPrettyPrinting()
	.create();

	private static Gson gson = new Gson();

	public String toJsonString() {
		return gson.toJson(this);
	}

	public String toJsonPrintableString() {
		return printableGson.toJson(this);
	}

}
