package sevenpixel.taxes.utils;

import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

	private Gson gson;

	public GsonHelper() {
		GsonBuilder builder = new GsonBuilder();
		this.gson = builder.setPrettyPrinting().create();
	}

	public <T> T convertJsontoToObject(Reader json, Type typeToken) {
		return gson.fromJson(json, typeToken);
	}
	
	public <T> String toJson(T toBeConvertd, Type type) { 
		return gson.toJson(toBeConvertd, type);
	}
}
