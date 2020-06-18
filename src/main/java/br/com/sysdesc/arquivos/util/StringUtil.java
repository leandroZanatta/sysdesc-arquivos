package br.com.sysdesc.arquivos.util;

public class StringUtil {

	private StringUtil() {
	}

	public static String paddingRight(String data, String key, int size) {

		if (data.length() > size) {
			return data.substring(0, size);
		}

		while (data.length() < size) {
			data = data.concat(key);
		}

		return data;
	}

	public static String paddingLeft(String data, String key, int size) {

		if (data.length() > size) {
			return data.substring(0, size);
		}

		while (data.length() > size) {
			data = key.concat(data);
		}

		return data;
	}
}
