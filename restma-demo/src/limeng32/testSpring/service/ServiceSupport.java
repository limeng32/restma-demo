package limeng32.testSpring.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import limeng32.mybatis.enums.PLUGIN;
import limeng32.mybatis.plugin.SqlSuffix;
import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.annotation.SQLMeta;
import limeng32.testSpring.enums.POJOFace;
import limeng32.testSpring.mapper.MapperFace;
import limeng32.testSpring.pojo.Queryable;

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
			Map<Queryable, Object> map) {
		Map<String, Object> p = new HashMap<String, Object>();
		Type t = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		for (Queryable key : map.keySet()) {
			Boolean b = false;
			if (key.getClass().isAnnotationPresent(Domain.class)) {
				b = t.equals(key.getClass().getAnnotation(Domain.class).value());
				if (b) {
					p.put(key.value(), map.get(key));
				} else {
					p.put(key.tableAndValueByEscape(), map.get(key));
				}
			} else if (key.getClass().isAnnotationPresent(SQLMeta.class)) {
				String enumName = ((Enum<?>) key).name();
				int i = -1;
				try {
					i = key.getClass().getField(enumName)
							.getAnnotation(SQLMeta.class).value();
					System.out.println("--" + i);
				} catch (NoSuchFieldException | SecurityException e) {
				}
				switch (i) {
				case SQLMeta.order:
					SqlSuffix sqlSuffix = new SqlSuffix();
					sqlSuffix.setOrder(key.value());
					sqlSuffix.setSortField(((POJOFace) map.get(key))
							.tableAndValue());
					p.put(PLUGIN.sqlSuffix.toString(), sqlSuffix);
					break;

				case SQLMeta.sorter:
					/* 正要解决多重排序的问题 */
					System.out.println("asd");
					break;

				default:
					break;
				}
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
