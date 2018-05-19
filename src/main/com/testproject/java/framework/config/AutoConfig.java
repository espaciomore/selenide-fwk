package main.com.testproject.java.framework.config;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public final class AutoConfig
{
	public static JsonElement get(String path) throws JsonIOException, JsonSyntaxException, FileNotFoundException
	{
		final JsonParser parser = new JsonParser();
		final JsonElement obj = parser.parse(new FileReader("local\\config\\config.properties"));
		final JsonObject json = obj.getAsJsonObject();
		
		String[] arrayPath = path.split("\\.");
		int len = arrayPath.length;
		if(len > 1) {
			JsonObject jo = json.get(arrayPath[0]).getAsJsonObject();
			for(int i=1; i<len; i++) { 
				if (i == len - 1) {
					return jo.get(arrayPath[i]);
				} else {
					jo = jo.get(arrayPath[i]).getAsJsonObject();
				}
			}
		}
		
		return json.get(path); 
	}
}
