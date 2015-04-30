package limeng32.testSpring.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import limeng32.mybatis.plugin.SqlSuffix;
import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.annotation.SQLMeta;
import limeng32.testSpring.mapper.MapperFace;
import limeng32.testSpring.page.PageParam;
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
		/* 不要新建p，仍然使用map进行传值 */
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

				case SQLMeta.sorter:
					arrangeSorter(p, map.get(key));
					break;

				case SQLMeta.limit:
					arrangeLimiter(p, map.get(key));
					break;

				default:
					break;
				}
			}
		}
		return mapper.selectAll(p);
	}

	private void arrangeSorter(Map<String, Object> p, Object list) {
		SqlSuffix sqlSuffix;
		if (p.containsKey(SqlSuffix.KEY)) {
			sqlSuffix = (SqlSuffix) p.get(SqlSuffix.KEY);
		} else {
			sqlSuffix = new SqlSuffix();
		}
		if (sqlSuffix.getSorterList() == null) {
			sqlSuffix.setSorterList(new LinkedList<String[]>());
		}
		@SuppressWarnings("unchecked")
		List<Queryable[]> l = (List<Queryable[]>) list;
		for (Queryable[] q : l) {
			System.out.println("---" + q[0] + "," + q[1]);
			String[] oneSorter = new String[] { q[0].tableAndValue(),
					q[1].value() };
			sqlSuffix.getSorterList().add(oneSorter);
		}
		p.put(SqlSuffix.KEY, sqlSuffix);
	}

	private void arrangeLimiter(Map<String, Object> p, Object limiter) {
		PageParam pageParam = (PageParam) limiter;
		SqlSuffix sqlSuffix;
		if (p.containsKey(SqlSuffix.KEY)) {
			sqlSuffix = (SqlSuffix) p.get(SqlSuffix.KEY);
		} else {
			sqlSuffix = new SqlSuffix();
		}
		sqlSuffix.setLimiter(pageParam);
		p.put(SqlSuffix.KEY, sqlSuffix);
	}

	protected void supportInsert(MapperFace<T> mapper, T t) {
		mapper.insert(t);
	}

	protected void supportUpdate(MapperFace<T> mapper, T t) {
		mapper.update(t);
	}
}
