package limeng32.testSpring.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import limeng32.mybatis.enums.PLUGIN;
import limeng32.mybatis.plugin.SqlSuffix;
import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.annotation.SqlDialect;
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
				if (b) {
					p.put(key.value(), map.get(key));
				} else {
					p.put(key.tableAndValueByEscape(), map.get(key));
				}
			} else if (key.getClass().isAnnotationPresent(SqlDialect.class)) {
				SqlSuffix sqlSuffix = new SqlSuffix();
				/* 解决排序的问题 */
				if (SqlDialect.order.equals(key.getClass()
						.getAnnotation(SqlDialect.class).value())) {
					System.out.println("--"
							+ ((PojoEnum) map.get(key)).tableAndValue());
					sqlSuffix.setOrder(key.value());
					sqlSuffix.setSortField(((PojoEnum) map.get(key))
							.tableAndValue());
				}
				/* 解决分页的问题 */

				p.put(PLUGIN.sqlSuffix.toString(), sqlSuffix);
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
