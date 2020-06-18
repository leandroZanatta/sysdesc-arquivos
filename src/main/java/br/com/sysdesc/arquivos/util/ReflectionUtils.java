package br.com.sysdesc.arquivos.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import br.com.sysdesc.arquivos.exceptions.FileMapperException;

public class ReflectionUtils {

	private ReflectionUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericType(Class<?> classePai) {

		final ParameterizedType type = (ParameterizedType) classePai.getGenericSuperclass();

		return (Class<T>) type.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericTypeFromList(Type genericType) {

		final ParameterizedType type = (ParameterizedType) genericType;

		return (Class<T>) type.getActualTypeArguments()[0];
	}

	public static <T> void setValue(T parent, Field field, Object value) throws IllegalAccessException {

		field.setAccessible(true);
		field.set(parent, value);
	}

	public static Field getFieldFromClass(Class<?> clazz, String nameField) throws FileMapperException {

		return Arrays.asList(clazz.getDeclaredFields()).stream().filter(field -> field.getName().equals(nameField))
				.findFirst().orElseThrow(() -> new FileMapperException(String
						.format("O Campo %s não foi encontrado na classe %s, Verifique", nameField, clazz.getName())));
	}

	public static <T> Object getValue(Field field, T object) throws IllegalAccessException {
		field.setAccessible(true);
		return field.get(object);
	}

}
