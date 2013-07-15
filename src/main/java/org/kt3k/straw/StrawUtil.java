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

	public static <T> T jsonToObj(String json, Class<T> type) throws JsonParseException, JsonMappingException, IOException {
		return StrawUtil.objectMapper.readValue(json, type);
	}

	public static <T> String join(T[] array, String sep) {
		Integer len = array.length;

		if (len == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(array[0].toString());

		for (int i = 1; i < len; i++) {
			sb.append(sep);
			sb.append(array[i].toString());
		}

		return sb.toString();
	}
}
