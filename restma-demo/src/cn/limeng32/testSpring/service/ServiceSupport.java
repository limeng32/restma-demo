package cn.limeng32.testSpring.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.limeng32.testSpring.enums.PojoEnum;
import cn.limeng32.testSpring.mapper.MapperFace;

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
		Type t1 = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		for (PojoEnum key : map.keySet()) {
			Boolean b = false;
			if (((Type[]) key.getClass().getGenericInterfaces()).length > 0) {
				b = t1
						.equals((((ParameterizedType) ((Type[]) key.getClass()
								.getGenericInterfaces())[0])
								.getActualTypeArguments())[0]);
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
