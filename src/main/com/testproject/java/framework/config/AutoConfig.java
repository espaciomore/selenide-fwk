package main.com.testproject.java.framework.config;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoConfig.
 */
public final class AutoConfig
{
	
	/** The Constant configBasePath. */
	public static final String configBasePath = StringUtils.isEmpty(System.getProperty("configBasePath")) ? "" : System.getProperty("configBasePath");
	
	/** The Constant resourceBasePath. */
	public static final String resourceBasePath = StringUtils.isEmpty(System.getProperty("resourceBasePath")) ? "" : System.getProperty("resourceBasePath");
	
	/** The Constant configFileUri. */
	public static final String configFileUri = configBasePath + "config-properties.json";
	
	/** The data file uri. */
	public static String dataFileUri = configBasePath + "test-data.json";
	
	/** The last file uri. */
	private static String lastFileUri;
	
	/** The last json object. */
	private static JsonElement lastJsonElement;
	
	/**
	 * Gets the.
	 *
	 * @param p_fileUri the p file uri
	 * @return the json object
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	public static JsonElement get(String p_fileUri) throws FileNotFoundException, Exception
	{
		if (p_fileUri.equals(lastFileUri)) {
			return lastJsonElement;
		}
		
		final JsonParser v_parser = new JsonParser();
		final JsonElement v_json = v_parser.parse(new FileReader(p_fileUri));
		
		lastFileUri = p_fileUri;
		lastJsonElement = v_json;
		
		return v_json; 
	}
	
	/**
	 * Gets the.
	 *
	 * @param p_fileUri the p file uri
	 * @param p_locator the p locator
	 * @return the json element
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	public static JsonElement get(String p_fileUri, String p_locator) throws FileNotFoundException, Exception
	{
		JsonElement v_json = get(p_fileUri);

		if(!v_json.isJsonObject()){
		    return v_json;
		}
		
		if(p_locator.isEmpty()){
		    return v_json;	
		}
		
		String[] v_arrayPath = p_locator.split("\\.");
		int v_len = v_arrayPath.length;
		
		for(int i=0; i<v_len; i++) { 
			String key = v_arrayPath[i];
			int index = -1;
			// separate key from array index
			if(key.endsWith("]")){
				index = Integer.parseInt(key.replaceAll("^.*\\[(\\d+)\\]$", "$1"));
				key = key.replaceAll("^(.*)\\[\\d+\\]$", "$1");
			}
			JsonElement this_je = v_json.get(key);	
			// get json element by index for a json array
			if(this_je.isJsonArray() && index >= 0){
				this_je = this_je.getAsJsonArray().get(index);
			}
			
			if(this_je.isJsonArray()){
				return this_je;
			} else if(this_je.isJsonNull()){
				return this_je;
			} else if(this_je.isJsonObject()) {
				v_json = this_je;
				if(i == (v_len - 1)){
				    return this_je;	
				} else {
				    continue;
				}
			} else if(this_je.isJsonPrimitive()){
				return this_je;
			} else {
				System.out.println("Unkown JSON Identifier for '"+p_locator+"' from Json file ("+p_fileUri+")");
				break;
			}
		}
		
		return v_jsonElment;
	}
	
	/**
	 * Read config.
	 *
	 * @param p_locator the p locator
	 * @return the json element
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	public static JsonElement readConfig(String p_locator) throws FileNotFoundException, Exception
	{
		return get(configFileUri, p_locator); 
	}
	
	/**
	 * Read data.
	 *
	 * @param p_locator the p locator
	 * @return the json element
	 * @throws FileNotFoundException the file not found exception
	 * @throws Exception the exception
	 */
	public static JsonElement readData(String p_locator) throws FileNotFoundException, Exception
	{
		return get(dataFileUri, p_locator); 
	}
}


