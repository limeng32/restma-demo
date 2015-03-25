package limeng32.testSpring.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.enums.PojoEnum;
import limeng32.testSpring.mapper.MapperFace;

public abstract class ServiceSupport<T> implements ServiceFace<T> {

	protected T supportSelect(MapperFace<T> mapper, int id) {
		return mapper.select(id);
	}

	protected int supportCount(MapperFace<T> mapper, Map<String, Object> map) {
		return mapper.count(map);
	}

	protected List<T> supportSelectAll(MapperFace<T> mapper,
			Map<String, Object> map) {
		return mapper.selectAll(map);
	}

	protected List<T> supportSelectAllUseEnum(MapperFace<T> mapper,
			Map<PojoEnum, Object> map) {
		Map<String, Object> p = new HashMap<String, Object>();
		Type t = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		for (PojoEnum key : map.keySet()) {
			Boolean b = false;
			if (key.getClass().isAnnotationPresent(Domain.class)) {
				b = t.equals(key.getClass().getAnnotation(Domain.class).value());
			}
			if (b) {
				p.put(key.value(), map.get(key));
			} else {
				/* 还没有解决分页的问题 */
				p.put(key.tableAndValue(), map.get(key));
			}
		}
		return mapper.selectAll(p);
	}

	protected void supportInsert(MapperFace<T> mapper, T t) {
		mapper.insert(t);
	}

	protected void supportUpdate(MapperFace<T> mapper, T t) {
		mapper.update(t);
	}
}
