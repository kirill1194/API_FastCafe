package Items;



import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

public abstract class BaseItem<T> {
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

	public String ALToJsonArray(ArrayList<T> arr) {
		//здесь костыль для правильного вывода JSONa. Сама либа не может правильно его сделать
		//ну или я не могу заставить ее это делать. хз
		JsonArray jsonArr = new JsonArray();
		Gson gson = new Gson();
		for (T item : arr) {
			jsonArr.add(gson.toJsonTree(item));
		}

		return jsonArr.toString();
	}

}
