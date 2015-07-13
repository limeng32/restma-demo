package limeng32.mybatis.plugin.mapper.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import limeng32.mybatis.plugin.ReflectHelper;
import limeng32.mybatis.plugin.mapper.annotation.ConditionMapper;
import limeng32.mybatis.plugin.mapper.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.ConditionType;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapper;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.Mapperable;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapper;
import limeng32.mybatis.plugin.mapper.annotation.TableMapper;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 通过注解生成sql
 * 
 * @author david
 * 
 */
public class SqlBuilder {
	/**
	 * 缓存TableMapper
	 */
	private static HashMap<Class<?>, TableMapper> tableMapperCache = new HashMap<Class<?>, TableMapper>(
			128);

	private static HashMap<Class<?>, QueryMapper> queryMapperCache = new HashMap<Class<?>, QueryMapper>(
			128);

	/**
	 * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
	 * 
	 * @param dtoClass
	 * @return TableMapper
	 */
	private static TableMapper buildTableMapper(Class<?> dtoClass) {

		Map<String, FieldMapper> fieldMapperCache = null;
		List<FieldMapper> fieldMapperList = null;
		Field[] fields = null;

		FieldMapperAnnotation fieldMapperAnnotation = null;
		FieldMapper fieldMapper = null;
		TableMapper tableMapper = null;
		synchronized (tableMapperCache) {
			tableMapper = tableMapperCache.get(dtoClass);
			if (tableMapper != null) {
				return tableMapper;
			}
			tableMapper = new TableMapper();
			Annotation[] classAnnotations = dtoClass.getDeclaredAnnotations();
			for (Annotation an : classAnnotations) {
				if (an instanceof TableMapperAnnotation) {
					tableMapper.setTableMapperAnnotation(an);
				}
			}
			fields = dtoClass.getDeclaredFields();
			fieldMapperCache = new HashMap<String, FieldMapper>();
			fieldMapperList = new ArrayList<FieldMapper>();
			Annotation[] fieldAnnotations = null;
			for (Field field : fields) {
				fieldAnnotations = field.getDeclaredAnnotations();
				if (fieldAnnotations.length == 0) {
					continue;
				}
				for (Annotation an : fieldAnnotations) {
					if (an instanceof FieldMapperAnnotation) {
						fieldMapperAnnotation = (FieldMapperAnnotation) an;
						fieldMapper = new FieldMapper();
						fieldMapper.setFieldName(field.getName());
						fieldMapper.setDbFieldName(fieldMapperAnnotation
								.dbFieldName());
						fieldMapper.setJdbcType(fieldMapperAnnotation
								.jdbcType());
						fieldMapper.setUniqueKey(fieldMapperAnnotation
								.isUniqueKey());
						if ("".equals(fieldMapperAnnotation
								.dbAssociationUniqueKey())) {
						} else {
							fieldMapper
									.setDbAssociationUniqueKey(fieldMapperAnnotation
											.dbAssociationUniqueKey());
							fieldMapper.setForeignKey(true);
						}
						if (fieldMapper.isForeignKey()) {
							if (!tableMapperCache.containsKey(field.getType())) {
								buildTableMapper(field.getType());
							}
							TableMapper tm = tableMapperCache.get(field
									.getType());
							String foreignFieldName = tm
									.getFieldMapperCache()
									.get(fieldMapperAnnotation
											.dbAssociationUniqueKey())
									.getFieldName();
							fieldMapper.setForeignFieldName(foreignFieldName);
						}
						fieldMapperCache.put(
								fieldMapperAnnotation.dbFieldName(),
								fieldMapper);
						fieldMapperList.add(fieldMapper);
					} else if (an instanceof PersistentFlagAnnotation) {
						tableMapper.getPersistentFlags().add(field.getName());
					}
				}
			}
			tableMapper.setFieldMapperCache(fieldMapperCache);
			tableMapper.setFieldMapperList(fieldMapperList);
			tableMapperCache.put(dtoClass, tableMapper);
			return tableMapper;
		}
	}

	/**
	 * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
	 * 
	 * @param dtoClass
	 * @param pojoClass
	 * @return QueryMapper
	 */
	private static QueryMapper buildQueryMapper(Class<?> dtoClass,
			Class<?> pojoClass) {
		Map<String, ConditionMapper> conditionMapperCache = null;
		List<ConditionMapper> conditionMapperList = null;
		Field[] fields = null;

		ConditionMapperAnnotation conditionMapperAnnotation = null;
		ConditionMapper conditionMapper = null;
		QueryMapper queryMapper = null;
		synchronized (queryMapperCache) {
			queryMapper = queryMapperCache.get(dtoClass);
			if (queryMapper != null) {
				return queryMapper;
			}
			queryMapper = new QueryMapper();
			fields = dtoClass.getDeclaredFields();
			conditionMapperCache = new HashMap<>();
			conditionMapperList = new ArrayList<>();
			Annotation[] conditionAnnotations = null;

			for (Field field : fields) {
				conditionAnnotations = field.getDeclaredAnnotations();
				if (conditionAnnotations.length == 0) {
					continue;
				}
				for (Annotation an : conditionAnnotations) {
					if (an instanceof ConditionMapperAnnotation) {
						conditionMapperAnnotation = (ConditionMapperAnnotation) an;
						conditionMapper = new ConditionMapper();
						conditionMapper.setFieldName(field.getName());
						conditionMapper
								.setDbFieldName(conditionMapperAnnotation
										.dbFieldName());
						conditionMapper
								.setConditionType(conditionMapperAnnotation
										.conditionType());
						for (Field pojoField : pojoClass.getDeclaredFields()) {
							for (Annotation oan : pojoField
									.getDeclaredAnnotations()) {
								if (oan instanceof FieldMapperAnnotation
										&& ((FieldMapperAnnotation) oan)
												.dbFieldName().equals(
														conditionMapperAnnotation
																.dbFieldName())) {
									FieldMapperAnnotation fieldMapperAnnotation = (FieldMapperAnnotation) oan;
									conditionMapper
											.setJdbcType(fieldMapperAnnotation
													.jdbcType());
									if ("".equals(fieldMapperAnnotation
											.dbAssociationUniqueKey())) {
									} else {
										conditionMapper
												.setDbAssociationUniqueKey(fieldMapperAnnotation
														.dbAssociationUniqueKey());
										conditionMapper.setForeignKey(true);
									}
									if (conditionMapper.isForeignKey()) {
										if (!tableMapperCache
												.containsKey(pojoField
														.getType())) {
											buildTableMapper(pojoField
													.getType());
										}
										TableMapper tm = tableMapperCache
												.get(pojoField.getType());
										String foreignFieldName = tm
												.getFieldMapperCache()
												.get(fieldMapperAnnotation
														.dbAssociationUniqueKey())
												.getFieldName();
										conditionMapper
												.setForeignFieldName(foreignFieldName);
									}
								}
							}
						}
						conditionMapperCache.put(field.getName(),
								conditionMapper);
						conditionMapperList.add(conditionMapper);
					}
				}
			}
			queryMapper.setConditionMapperCache(conditionMapperCache);
			queryMapper.setConditionMapperList(conditionMapperList);
			queryMapperCache.put(dtoClass, queryMapper);
			return queryMapper;
		}
	}

	/**
	 * 查找类clazz及其所有父类，直到找到一个拥有TableMapperAnnotation注解的类为止，
	 * 然后返回这个拥有TableMapperAnnotation注解的类
	 * 
	 * @param object
	 * @return
	 */
	private static Class<?> getTableMappedClass(Class<?> clazz) {
		Class<?> c = clazz;
		while (!interview(c) && !(c.equals(Object.class))) {
			c = c.getSuperclass();
		}
		if (c.equals(Object.class)) {
			throw new RuntimeException("Class " + clazz.getName()
					+ " and all its parents has no 'TableMapperAnnotation', "
					+ "which has the database table information,"
					+ " I can't build 'TableMapper' for it.");
		}
		return c;
	}

	/**
	 * 判断clazz是否符合条件，即是否存在TableMapperAnnotation类型的标注。如存在返回true，否则返回false。
	 * 
	 * @param clazz
	 * @return
	 */
	private static boolean interview(Class<?> clazz) {
		Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
		if (classAnnotations.length > 0) {
			for (Annotation an : classAnnotations) {
				if (an instanceof TableMapperAnnotation) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 从注解里获取唯一键信息
	 * 
	 * @param tableMapper
	 * @return
	 */
	private static String[] buildUniqueKey(TableMapper tableMapper) {
		List<String> l = new ArrayList<String>();
		for (FieldMapper fm : tableMapper.getFieldMapperList()) {
			if (fm.isUniqueKey()) {
				l.add(fm.getDbFieldName());
			}
		}
		String[] uniqueKeyNames = new String[l.size()];
		l.toArray(uniqueKeyNames);
		return uniqueKeyNames;
	}

	/**
	 * 由传入的对象生成count sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildCountSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		QueryMapper queryMapper = buildQueryMapper(object.getClass(),
				getTableMappedClass(object.getClass()));
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		String[] uniqueKeyNames = buildUniqueKey(tableMapper);

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select count(");
		/*
		 * 如果有且只有一个主键，采用select count("主键")的方式；如果无主键或有多个主键（联合主键），采用select
		 * count(*)的方式。
		 */
		if (uniqueKeyNames.length == 1) {
			selectSql.append(uniqueKeyNames[0]);
		} else {
			selectSql.append("*");
		}
		selectSql.append(") from ").append(tableName);

		StringBuffer whereSql = new StringBuffer(" where ");

		boolean allFieldNull = true;

		// 处理tableMapper中的条件
		for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					dbFieldName);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				continue;
			}
			allFieldNull = false;
			dealConditionEqual(whereSql, fieldMapper, object, value);
		}

		// 处理queryMapper中的条件
		for (String fieldName : queryMapper.getConditionMapperCache().keySet()) {
			ConditionMapper conditionMapper = queryMapper
					.getConditionMapperCache().get(fieldName);
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				continue;
			}
			allFieldNull = false;
			switch (conditionMapper.getConditionType()) {
			case Equal:
				dealConditionEqual(whereSql, conditionMapper, object, value);
				break;
			case Like:
				dealConditionLike(whereSql, conditionMapper, object, value,
						ConditionType.Like);
				break;
			case HeadLike:
				dealConditionLike(whereSql, conditionMapper, object, value,
						ConditionType.HeadLike);
				break;
			case TailLike:
				dealConditionLike(whereSql, conditionMapper, object, value,
						ConditionType.TailLike);
				break;
			default:
				break;
			}
		}

		if (allFieldNull) {
			throw new RuntimeException("Are you joking? Object "
					+ object.getClass().getName()
					+ "'s all fields are null, how can i build sql for it?!");
		}

		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);

		// for (int i = 0; i < uniqueKeyNames.length; i++) {
		// whereSql.append(uniqueKeyNames[i]);
		// FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
		// uniqueKeyNames[i]);
		// String fieldName = fieldMapper.getFieldName();
		// whereSql.append("=#{").append(fieldName).append(",")
		// .append("jdbcType=")
		// .append(fieldMapper.getJdbcType().toString())
		// .append("} and ");
		// }
		// whereSql.delete(whereSql.lastIndexOf("and"),
		// whereSql.lastIndexOf("and") + 3);
		String ret = selectSql.append(whereSql).toString();
		System.out.println("----------------------" + ret);
		return ret;
	}

	private static void dealConditionLike(StringBuffer whereSql,
			ConditionMapper conditionMapper, Object object, Object value,
			ConditionType type) {
		String fieldName = conditionMapper.getFieldName();
		String dbFieldName = conditionMapper.getDbFieldName();
		whereSql.append(dbFieldName).append(" like #{");
		if (conditionMapper.isForeignKey()) {
			whereSql.append(fieldName).append(".")
					.append(conditionMapper.getForeignFieldName());
		} else {
			whereSql.append(fieldName);
		}
		whereSql.append(",").append("jdbcType=")
				.append(conditionMapper.getJdbcType().toString())
				.append(",typeHandler=");
		switch (type) {
		case Like:
			whereSql.append("ConditionLikeHandler");
			break;
		case HeadLike:
			whereSql.append("ConditionHeadLikeHandler");
			break;
		case TailLike:
			whereSql.append("ConditionTailLikeHandler");
			break;
		default:
			throw new RuntimeException(
					"Sorry,I refuse to build sql for an ambiguous condition!");
		}
		whereSql.append("} and ");
	}

	private static void dealConditionEqual(StringBuffer whereSql,
			Mapperable mapper, Object object, Object value) {
		whereSql.append(mapper.getDbFieldName()).append(" = #{");
		if (mapper.isForeignKey()) {
			whereSql.append(mapper.getFieldName()).append(".")
					.append(mapper.getForeignFieldName());
		} else {
			whereSql.append(mapper.getFieldName());
		}
		whereSql.append(",").append("jdbcType=")
				.append(mapper.getJdbcType().toString()).append("} and ");
	}

	/**
	 * 由传入的对象生成insert sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildInsertSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		StringBuffer tableSql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();

		tableSql.append("insert into ").append(tableName).append("(");
		valueSql.append("values(");

		boolean allFieldNull = true;
		for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					dbFieldName);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				continue;
			}
			allFieldNull = false;
			tableSql.append(dbFieldName).append(",");
			valueSql.append("#{");
			if (fieldMapper.isForeignKey()) {
				valueSql.append(fieldName).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				valueSql.append(fieldName);
			}
			valueSql.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString()).append("},");
		}
		if (allFieldNull) {
			throw new RuntimeException("Are you joking? Object "
					+ object.getClass().getName()
					+ "'s all fields are null, how can i build sql for it?!");
		}
		tableSql.delete(tableSql.lastIndexOf(","),
				tableSql.lastIndexOf(",") + 1);
		valueSql.delete(valueSql.lastIndexOf(","),
				valueSql.lastIndexOf(",") + 1);
		return tableSql.append(") ").append(valueSql).append(")").toString();
	}

	/**
	 * 由传入的对象生成update sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildUpdateSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}

		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));

		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		String[] uniqueKeyNames = buildUniqueKey(tableMapper);

		StringBuffer tableSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where ");

		tableSql.append("update ").append(tableName).append(" set ");

		boolean allFieldNull = true;

		for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					dbFieldName);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				continue;
			}
			allFieldNull = false;
			tableSql.append(dbFieldName).append("=#{");
			if (fieldMapper.isForeignKey()) {
				tableSql.append(fieldName).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				tableSql.append(fieldName);
			}
			tableSql.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString());
			tableSql.append("},");
		}
		if (allFieldNull) {
			throw new RuntimeException("Are you joking? Object "
					+ object.getClass().getName()
					+ "'s all fields are null, how can i build sql for it?!");
		}

		tableSql.delete(tableSql.lastIndexOf(","),
				tableSql.lastIndexOf(",") + 1);
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					uniqueKeyNames[i]);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build update sql failed!");
			}
			whereSql.append("=#{").append(fieldName).append(",")
					.append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return tableSql.append(whereSql).toString();
	}

	/**
	 * 由传入的对象生成update持久态对象的 sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildUpdatePersistentSql(Object object)
			throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}

		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		Collection<String> persistentFlags = tableMapper.getPersistentFlags();
		for (String persistentFlag : persistentFlags) {
			if (ReflectHelper.getValueByFieldName(object, persistentFlag) == null) {
				throw new RuntimeException(
						"Sorry,I refuse to updatePersistent a unretrieved pojo!");
			}
		}

		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		String[] uniqueKeyNames = buildUniqueKey(tableMapper);

		StringBuffer tableSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where ");

		tableSql.append("update ").append(tableName).append(" set ");

		boolean allFieldNull = true;

		for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					dbFieldName);
			String fieldName = fieldMapper.getFieldName();
			allFieldNull = false;
			tableSql.append(dbFieldName).append("=#{");
			if (fieldMapper.isForeignKey()) {
				tableSql.append(fieldName).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				tableSql.append(fieldName);
			}
			tableSql.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString()).append("},");
		}
		if (allFieldNull) {
			throw new RuntimeException("Are you joking? Object "
					+ object.getClass().getName()
					+ "'s all fields are null, how can i build sql for it?!");
		}

		tableSql.delete(tableSql.lastIndexOf(","),
				tableSql.lastIndexOf(",") + 1);
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					uniqueKeyNames[i]);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build update sql failed!");
			}
			whereSql.append("=#{").append(fieldName).append(",")
					.append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return tableSql.append(whereSql).toString();
	}

	/**
	 * 由传入的对象生成delete sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildDeleteSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		String[] uniqueKeyNames = buildUniqueKey(tableMapper);

		StringBuffer sql = new StringBuffer();

		sql.append("delete from ").append(tableName).append(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			sql.append(uniqueKeyNames[i]);
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					uniqueKeyNames[i]);
			String fieldName = fieldMapper.getFieldName();
			Object value = dtoFieldMap.get(fieldName);
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build update sql failed!");
			}
			sql.append("=#{").append(fieldName).append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3);
		return sql.toString();
	}

	/**
	 * 由传入的对象生成query sql语句
	 * 
	 * @param clazz
	 * @return sql
	 * @throws Exception
	 */
	public static String buildSelectSql(Class<?> clazz) throws Exception {
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(clazz));
		Collection<String> persistentFlags = tableMapper.getPersistentFlags();
		TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation();
		String tableName = tma.tableName();
		String[] uniqueKeyNames = buildUniqueKey(tableMapper);

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select ");
		for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
			selectSql.append(dbFieldName).append(",");
		}
		for (String persistentFlag : persistentFlags) {
			selectSql.append("true as ").append(persistentFlag).append(",");
		}
		selectSql.delete(selectSql.lastIndexOf(","),
				selectSql.lastIndexOf(",") + 1);
		selectSql.append(" from ").append(tableName);

		StringBuffer whereSql = new StringBuffer(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(
					uniqueKeyNames[i]);
			String fieldName = fieldMapper.getFieldName();
			whereSql.append("=#{").append(fieldName).append(",")
					.append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return selectSql.append(whereSql).toString();
	}
}
