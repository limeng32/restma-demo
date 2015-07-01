package limeng32.mybatis.plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class ReflectHelper {
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}

	/**
	 * 
	 * @param dest
	 * @param source
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyBeanByField(Object dest, Object source)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method[] destMethods = dest.getClass().getDeclaredMethods();
		Method[] sourceMethods = source.getClass().getDeclaredMethods();
		HashMap<String, Method> destMethodMap = new HashMap<>();
		HashMap<String, Method> sourceMethodMap = new HashMap<>();

		if (null != sourceMethods && null != destMethods) {
			for (Method m : sourceMethods) {
				sourceMethodMap.put(m.getName(), m);
			}
			for (Method m : destMethods) {
				destMethodMap.put(m.getName(), m);
			}
		} else {
			return;
		}
		for (Field field : source.getClass().getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())
					|| Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			String getMethodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			String setMethodName = "set"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			if (destMethodMap.get(setMethodName) == null
					|| sourceMethodMap.get(getMethodName) == null) {
				setValueByFieldName(dest, field.getName(),
						getValueByFieldName(source, field.getName()));
			} else {
				Method destMethod = (Method) destMethodMap.get(setMethodName);
				Method sourceMethod = (Method) sourceMethodMap
						.get(getMethodName);
				destMethod.invoke(dest, sourceMethod.invoke(source));
			}
		}
	}
}
