package org.kt3k.straw;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StrawUtil {

	public static ObjectMapper objectMapper = new ObjectMapper();

	public static String objToJson(Object value) throws JsonGenerationException, JsonMappingException, IOException {
		return StrawUtil.objectMapper.writeValueAsString(value);
	}

	public static Object jsonToObj(String json, Class<?> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawUtil.objectMapper.readValue(json, type);
	}
}
