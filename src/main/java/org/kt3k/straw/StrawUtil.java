package org.kt3k.straw;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class StrawUtil {

	public static Gson gson = new Gson();

	public static String objToJson(Object value) {
		return gson.toJson(value);
	}

	public static <T> T jsonToObj(String json, Class<T> type) throws JsonSyntaxException {
		return gson.fromJson(json, type);
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
