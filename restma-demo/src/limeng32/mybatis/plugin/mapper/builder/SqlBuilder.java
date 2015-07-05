package limeng32.mybatis.plugin.mapper.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import limeng32.mybatis.plugin.ReflectHelper;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapper;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
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

	/**
	 * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
	 * 
	 * @param dtoClass
	 * @return TableMapper
	 */
	private static TableMapper buildTableMapper(Class<?> dtoClass) {

		HashMap<String, FieldMapper> fieldMapperCache = null;
		ArrayList<FieldMapper> fieldMapperList = null;
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
		boolean ret = false;
		Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
		if (classAnnotations.length > 0) {
			for (Annotation an : classAnnotations) {
				if (an instanceof TableMapperAnnotation) {
					ret = true;
					break;
				}
			}
		}
		return ret;
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
