package limeng32.mybatis.plugin.mapper.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import limeng32.mybatis.plugin.ReflectHelper;
import limeng32.mybatis.plugin.mapper.able.AbleConditionFlagAnnotation;
import limeng32.mybatis.plugin.mapper.able.AbleConditionType;
import limeng32.mybatis.plugin.mapper.able.AbleFlagAnnotation;
import limeng32.mybatis.plugin.mapper.able.AbleMapperFace;
import limeng32.mybatis.plugin.mapper.able.PojoAble;
import limeng32.mybatis.plugin.mapper.annotation.AbleFieldMapper;
import limeng32.mybatis.plugin.mapper.annotation.ConditionMapper;
import limeng32.mybatis.plugin.mapper.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.ConditionType;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapper;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.Mapperable;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapper;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapperAnnotation;
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
	private static Map<Class<?>, TableMapper> tableMapperCache = new ConcurrentHashMap<Class<?>, TableMapper>(
			128);

	private static Map<Class<?>, QueryMapper> queryMapperCache = new ConcurrentHashMap<Class<?>, QueryMapper>(
			128);

	/**
	 * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
	 * 
	 * @param dtoClass
	 * @return TableMapper
	 */
	private static TableMapper buildTableMapper(Class<?> dtoClass) {

		Map<String, Mapperable> fieldMapperCache = null;
		Field[] fields = null;

		FieldMapperAnnotation fieldMapperAnnotation = null;
		FieldMapper fieldMapper = null;
		TableMapper tableMapper = null;
		tableMapper = tableMapperCache.get(dtoClass);
		if (tableMapper != null) {
			return tableMapper;
		}
		tableMapper = new TableMapper();
		List<String> uniqueKeyList = new ArrayList<String>();
		Annotation[] classAnnotations = dtoClass.getDeclaredAnnotations();
		for (Annotation an : classAnnotations) {
			if (an instanceof TableMapperAnnotation) {
				tableMapper.setTableMapperAnnotation(an);
			}
		}
		fields = dtoClass.getDeclaredFields();
		fieldMapperCache = new HashMap<String, Mapperable>();
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
					fieldMapper.setJdbcType(fieldMapperAnnotation.jdbcType());
					fieldMapper.setUniqueKey(fieldMapperAnnotation
							.isUniqueKey());
					if (fieldMapperAnnotation.isUniqueKey()) {
						uniqueKeyList.add(fieldMapper.getDbFieldName());
					}
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
						TableMapper tm = tableMapperCache.get(field.getType());
						String foreignFieldName = getFieldMapperByDbFieldName(
								tm.getFieldMapperCache(),
								fieldMapperAnnotation.dbAssociationUniqueKey())
								.getFieldName();
						fieldMapper.setForeignFieldName(foreignFieldName);
					}
					fieldMapperCache.put(field.getName(), fieldMapper);
				} else if (an instanceof PersistentFlagAnnotation) {
					tableMapper.getPersistentFlags().add(field.getName());
				} else if (an instanceof AbleConditionFlagAnnotation) {
					AbleFieldMapper afm = new AbleFieldMapper();
					afm.setFieldName(field.getName());
					Field[] ableFlagFields = getFieldsByAnnotation(dtoClass,
							AbleFlagAnnotation.class);
					if (ableFlagFields.length != 1) {
						throw new RuntimeException(
								"Sorry,I refuse to build sql for a object which has more than one AbleFlagAnnotation!");
					}
					afm.setDbFieldName(ableFlagFields[0].getName());
					fieldMapperCache.put(afm.getFieldName(), afm);
					tableMapper.setAbleFlag(afm.getDbFieldName());
				}
			}
		}
		tableMapper.setFieldMapperCache(fieldMapperCache);
		tableMapper.setUniqueKeyNames(uniqueKeyList
				.toArray(new String[uniqueKeyList.size()]));
		tableMapperCache.put(dtoClass, tableMapper);
		return tableMapper;
	}

	private static Field[] getFieldsByAnnotation(Class<?> clazz,
			Class<? extends Annotation> annoClazz) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			for (Annotation an : annotations) {
				if (annoClazz.isAssignableFrom(an.getClass())) {
					list.add(field);
				}
			}
		}
		Field[] ret = new Field[list.size()];
		return list.toArray(ret);
	}

	/* 从newFieldMapperCache中获取已知dbFieldName的FieldMapper */
	private static Mapperable getFieldMapperByDbFieldName(
			Map<String, Mapperable> newFieldMapperCache, String dbFieldName) {
		for (Mapperable mapper : newFieldMapperCache.values()) {
			if (dbFieldName.equals(mapper.getDbFieldName())) {
				return mapper;
			}
		}
		return null;
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
		Field[] fields = null;

		ConditionMapperAnnotation conditionMapperAnnotation = null;
		ConditionMapper conditionMapper = null;
		QueryMapper queryMapper = null;
		queryMapper = queryMapperCache.get(dtoClass);
		if (queryMapper != null) {
			return queryMapper;
		}
		queryMapper = new QueryMapper();
		fields = dtoClass.getDeclaredFields();
		conditionMapperCache = new HashMap<>();
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
					conditionMapper.setDbFieldName(conditionMapperAnnotation
							.dbFieldName());
					conditionMapper.setConditionType(conditionMapperAnnotation
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
									if (!tableMapperCache.containsKey(pojoField
											.getType())) {
										buildTableMapper(pojoField.getType());
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
					conditionMapperCache.put(field.getName(), conditionMapper);
				}
			}
		}
		queryMapper.setConditionMapperCache(conditionMapperCache);
		queryMapperCache.put(dtoClass, queryMapper);
		return queryMapper;
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

	private static void dealConditionLike(StringBuffer whereSql,
			ConditionMapper conditionMapper, ConditionType type,
			String tableName, String fieldNamePrefix) {
		if (tableName != null) {
			whereSql.append(tableName).append(".");
		}
		whereSql.append(conditionMapper.getDbFieldName()).append(" like #{");
		if (fieldNamePrefix != null) {
			whereSql.append(fieldNamePrefix).append(".");
		}
		if (conditionMapper.isForeignKey()) {
			whereSql.append(conditionMapper.getFieldName()).append(".")
					.append(conditionMapper.getForeignFieldName());
		} else {
			whereSql.append(conditionMapper.getFieldName());
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

	private static void dealConditionEqual(Object object,
			StringBuffer whereSql, Mapperable mapper, String tableName,
			String fieldNamePrefix) {
		if (mapper instanceof AbleFieldMapper) {
			if (object == AbleConditionType.Ignore) {
				return;
			}
		}
		if (tableName != null) {
			whereSql.append(tableName).append(".");
		}
		whereSql.append(mapper.getDbFieldName()).append(" = #{");
		if (fieldNamePrefix != null) {
			whereSql.append(fieldNamePrefix).append(".");
		}
		if (mapper.isForeignKey()) {
			whereSql.append(mapper.getFieldName()).append(".")
					.append(mapper.getForeignFieldName());
		} else {
			whereSql.append(mapper.getFieldName());
		}
		if (mapper.getJdbcType() != null) {
			whereSql.append(",").append("jdbcType=")
					.append(mapper.getJdbcType().toString());
		}
		whereSql.append("} and ");
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

		/* 处理AbleFlag问题 */
		if (tableMapper.getAbleFlag() != null) {
			ReflectHelper.setValueByFieldName(object,
					tableMapper.getAbleFlag(), true);
		}

		String tableName = tma.tableName();
		StringBuffer tableSql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();

		tableSql.append("insert into ").append(tableName).append("(");
		valueSql.append("values(");

		boolean allFieldNull = true;
		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null || fieldMapper instanceof AbleFieldMapper) {
				continue;
			}
			allFieldNull = false;
			tableSql.append(fieldMapper.getDbFieldName()).append(",");
			valueSql.append("#{");
			if (fieldMapper.isForeignKey()) {
				valueSql.append(fieldMapper.getFieldName()).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				valueSql.append(fieldMapper.getFieldName());
			}
			valueSql.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString()).append("},");
		}
		if (allFieldNull) {
			throw new RuntimeException("Are you joking? Object "
					+ object.getClass().getName()
					+ "'s all fields are null, how can i build sql for it?!");
		}

		/* 处理AbleFlag问题 */
		if (tableMapper.getAbleFlag() != null) {
			tableSql.append(tableMapper.getAbleFlag()).append(",");
			valueSql.append("#{").append(tableMapper.getAbleFlag())
					.append("},");
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
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();

		StringBuffer tableSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where ");

		tableSql.append("update ").append(tableName).append(" set ");

		boolean allFieldNull = true;

		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null || fieldMapper instanceof AbleFieldMapper) {
				continue;
			}
			allFieldNull = false;
			tableSql.append(fieldMapper.getDbFieldName()).append("=#{");
			if (fieldMapper.isForeignKey()) {
				tableSql.append(fieldMapper.getFieldName()).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				tableSql.append(fieldMapper.getFieldName());
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
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build update sql failed!");
			}
			whereSql.append("=#{").append(fieldMapper.getFieldName())
					.append(",").append("jdbcType=")
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
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();

		StringBuffer tableSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where ");

		tableSql.append("update ").append(tableName).append(" set ");

		boolean allFieldNull = true;

		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			if (fieldMapper instanceof AbleFieldMapper) {
				continue;
			}
			allFieldNull = false;
			tableSql.append(fieldMapper.getDbFieldName()).append("=#{");
			if (fieldMapper.isForeignKey()) {
				tableSql.append(fieldMapper.getFieldName()).append(".")
						.append(fieldMapper.getForeignFieldName());
			} else {
				tableSql.append(fieldMapper.getFieldName());
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
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build updatePersistent sql failed!");
			}
			whereSql.append("=#{").append(fieldMapper.getFieldName())
					.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return tableSql.append(whereSql).toString();
	}

	/**
	 * 由传入的对象生成enable sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildEnableSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		String tableName = ((TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation()).tableName();
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();
		StringBuffer updateSql = new StringBuffer(), whereSql = new StringBuffer();
		updateSql.append("update ").append(tableName).append(" set ")
				.append(tableMapper.getAbleFlag()).append(" = ")
				.append(AbleMapperFace.ableToken);
		whereSql.append(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build enable sql failed!");
			}
			whereSql.append("=#{").append(fieldMapper.getFieldName())
					.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return updateSql.append(whereSql).toString();
	}

	/**
	 * 由传入的对象生成disable sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildDisableSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		String tableName = ((TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation()).tableName();
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();
		StringBuffer updateSql = new StringBuffer(), whereSql = new StringBuffer();
		updateSql.append("update ").append(tableName).append(" set ")
				.append(tableMapper.getAbleFlag()).append(" = ")
				.append(AbleMapperFace.unableToken);
		whereSql.append(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build disable sql failed!");
			}
			whereSql.append("=#{").append(fieldMapper.getFieldName())
					.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return updateSql.append(whereSql).toString();
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
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();

		StringBuffer sql = new StringBuffer();

		sql.append("delete from ").append(tableName).append(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			sql.append(uniqueKeyNames[i]);
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
						+ "' can't be null, build delete sql failed!");
			}
			sql.append("=#{").append(fieldMapper.getFieldName()).append(",")
					.append("jdbcType=")
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
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();

		StringBuffer selectSql = new StringBuffer("select ");

		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			selectSql.append(fieldMapper.getDbFieldName()).append(",");
		}
		for (String persistentFlag : persistentFlags) {
			selectSql.append("true as ").append(persistentFlag).append(",");
		}

		if (selectSql.indexOf(",") > -1) {
			selectSql.delete(selectSql.lastIndexOf(","),
					selectSql.lastIndexOf(",") + 1);
		}

		selectSql.append(" from ").append(tableName);

		StringBuffer whereSql = new StringBuffer(" where ");
		for (int i = 0; i < uniqueKeyNames.length; i++) {
			whereSql.append(uniqueKeyNames[i]);
			Mapperable fieldMapper = getFieldMapperByDbFieldName(
					tableMapper.getFieldMapperCache(), uniqueKeyNames[i]);
			whereSql.append("=#{").append(fieldMapper.getFieldName())
					.append(",").append("jdbcType=")
					.append(fieldMapper.getJdbcType().toString())
					.append("} and ");
		}
		whereSql.delete(whereSql.lastIndexOf("and"),
				whereSql.lastIndexOf("and") + 3);
		return selectSql.append(whereSql).toString();
	}

	/**
	 * 由传入的对象生成query sql语句
	 * 
	 * @param object
	 * @return sql
	 * @throws Exception
	 */
	public static String buildSelectAllSql(Object object) throws Exception {
		if (null == object) {
			throw new RuntimeException(
					"Sorry,I refuse to build sql for a null object!");
		}
		StringBuffer selectSql = new StringBuffer("select ");
		StringBuffer fromSql = new StringBuffer(" from ");
		StringBuffer whereSql = new StringBuffer(" where ");

		dealMapperAnnotationIterationForSelectAll(object, selectSql, fromSql,
				whereSql, null, null, null);

		if (selectSql.indexOf(",") > -1) {
			selectSql.delete(selectSql.lastIndexOf(","),
					selectSql.lastIndexOf(",") + 1);
		}
		if (" where ".equals(whereSql.toString())) {
			whereSql = new StringBuffer();
		}
		if (whereSql.indexOf("and") > -1) {
			whereSql.delete(whereSql.lastIndexOf("and"),
					whereSql.lastIndexOf("and") + 3);
		}
		return selectSql.append(fromSql).append(whereSql).toString();
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

		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		String tableName = ((TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation()).tableName();
		String[] uniqueKeyNames = tableMapper.getUniqueKeyNames();

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select count(").append(tableName).append(".");
		/*
		 * 如果有且只有一个主键，采用select count("主键")的方式；如果无主键或有多个主键（联合主键），采用select
		 * count(*)的方式。
		 */
		if (uniqueKeyNames.length == 1) {
			selectSql.append(uniqueKeyNames[0]);
		} else {
			selectSql.append("*");
		}
		selectSql.append(")");

		StringBuffer fromSql = new StringBuffer(" from ");
		StringBuffer whereSql = new StringBuffer(" where ");

		dealMapperAnnotationIterationForCount(object, fromSql, whereSql, null,
				null, null);

		if (selectSql.indexOf(",") > -1) {
			selectSql.delete(selectSql.lastIndexOf(","),
					selectSql.lastIndexOf(",") + 1);
		}
		if (" where ".equals(whereSql.toString())) {
			whereSql = new StringBuffer();
		}
		if (whereSql.indexOf("and") > -1) {
			whereSql.delete(whereSql.lastIndexOf("and"),
					whereSql.lastIndexOf("and") + 3);
		}

		return selectSql.append(fromSql).append(whereSql).toString();
	}

	private static boolean hasTableMapperAnnotation(Class<?> clazz) {
		Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
		for (Annotation an : classAnnotations) {
			if (an instanceof TableMapperAnnotation) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasQueryMapperAnnotation(Class<?> clazz) {
		Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
		for (Annotation an : classAnnotations) {
			if (an instanceof QueryMapperAnnotation) {
				return true;
			}
		}
		return false;
	}

	private static void dealMapperAnnotationIterationForSelectAll(
			Object object, StringBuffer selectSql, StringBuffer fromSql,
			StringBuffer whereSql, String originTableName,
			Mapperable originFieldMapper, String fieldPerfix) throws Exception {
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		QueryMapper queryMapper = buildQueryMapper(object.getClass(),
				getTableMappedClass(object.getClass()));
		String tableName = ((TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation()).tableName();

		/* 处理Able特性 */
		if (tableMapper.getAbleFlag() != null
				&& ((PojoAble) object).getAbleCondition() == null) {
			((PojoAble) object).setAbleCondition(AbleConditionType.Able);
		}

		/*
		 * 在第一次遍历中，处理好selectSql和fromSql。 如果originFieldMapper为null则可认为是第一次遍历
		 */
		if (originFieldMapper == null) {
			fromSql.append(tableName);
			for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
					.values()) {
				selectSql.append(tableName).append(".")
						.append(fieldMapper.getDbFieldName()).append(",");
			}
		}
		/*
		 * 在非第一次遍历中，处理fieldPerfix和fromSql。
		 * 如果originFieldMapper和originTableName均不为null则可认为是非第一次遍历
		 */
		String temp = null;
		if (originFieldMapper != null && originTableName != null) {
			/* 处理fieldPerfix */
			temp = originFieldMapper.getFieldName();
			if (fieldPerfix != null) {
				temp = fieldPerfix + "." + temp;
			}
			/* 处理fromSql */
			fromSql.append(" left join ").append(tableName).append(" on ")
					.append(originTableName).append(".")
					.append(originFieldMapper.getDbFieldName()).append(" = ")
					.append(tableName).append(".")
					.append(originFieldMapper.getDbAssociationUniqueKey());
		}

		/* 处理fieldMapper中的条件 */
		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				continue;
			}
			/* 此处当value拥有TableMapper或QueryMapper标注时，开始进行迭代 */
			if (hasTableMapperAnnotation(value.getClass())
					|| hasQueryMapperAnnotation(value.getClass())) {
				dealMapperAnnotationIterationForSelectAll(value, selectSql,
						fromSql, whereSql, tableName, fieldMapper, temp);
			} else {
				dealConditionEqual(value, whereSql, fieldMapper, tableName,
						temp);
			}
		}

		/* 处理queryMapper中的条件 */
		for (ConditionMapper conditionMapper : queryMapper
				.getConditionMapperCache().values()) {
			Object value = dtoFieldMap.get(conditionMapper.getFieldName());
			if (value == null) {
				continue;
			}
			switch (conditionMapper.getConditionType()) {
			case Equal:
				dealConditionEqual(value, whereSql, conditionMapper, tableName,
						temp);
				break;
			case Like:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.Like, tableName, temp);
				break;
			case HeadLike:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.HeadLike, tableName, temp);
				break;
			case TailLike:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.TailLike, tableName, temp);
				break;
			default:
				break;
			}
		}
	}

	private static void dealMapperAnnotationIterationForCount(Object object,
			StringBuffer fromSql, StringBuffer whereSql,
			String originTableName, Mapperable originFieldMapper,
			String fieldPerfix) throws Exception {
		Map<?, ?> dtoFieldMap = PropertyUtils.describe(object);
		TableMapper tableMapper = buildTableMapper(getTableMappedClass(object
				.getClass()));
		QueryMapper queryMapper = buildQueryMapper(object.getClass(),
				getTableMappedClass(object.getClass()));
		String tableName = ((TableMapperAnnotation) tableMapper
				.getTableMapperAnnotation()).tableName();

		/* 处理Able特性 */
		if (tableMapper.getAbleFlag() != null
				&& ((PojoAble) object).getAbleCondition() == null) {
			((PojoAble) object).setAbleCondition(AbleConditionType.Able);
		}

		/*
		 * 在第一次遍历中，处理好fromSql。 如果originFieldMapper为null则可认为是第一次遍历
		 */
		if (originFieldMapper == null) {
			fromSql.append(tableName);
		}

		/*
		 * 在非第一次遍历中，处理fieldPerfix和fromSql。
		 * 如果originFieldMapper和originTableName均不为null则可认为是非第一次遍历
		 */
		String temp = null;
		if (originFieldMapper != null && originTableName != null) {
			/* 处理fieldPerfix */
			temp = originFieldMapper.getFieldName();
			if (fieldPerfix != null) {
				temp = fieldPerfix + "." + temp;
			}
			/* 处理fromSql */
			fromSql.append(" left join ").append(tableName).append(" on ")
					.append(originTableName).append(".")
					.append(originFieldMapper.getDbFieldName()).append(" = ")
					.append(tableName).append(".")
					.append(originFieldMapper.getDbAssociationUniqueKey());
		}

		/* 处理fieldMapper中的条件 */
		for (Mapperable fieldMapper : tableMapper.getFieldMapperCache()
				.values()) {
			Object value = dtoFieldMap.get(fieldMapper.getFieldName());
			if (value == null) {
				continue;
			}
			/* 此处当value拥有TableMapper或QueryMapper标注时，开始进行迭代 */
			if (hasTableMapperAnnotation(value.getClass())
					|| hasQueryMapperAnnotation(value.getClass())) {
				dealMapperAnnotationIterationForCount(value, fromSql, whereSql,
						tableName, fieldMapper, temp);
			} else {
				dealConditionEqual(value, whereSql, fieldMapper, tableName,
						temp);
			}
		}

		/* 处理queryMapper中的条件 */
		for (ConditionMapper conditionMapper : queryMapper
				.getConditionMapperCache().values()) {
			Object value = dtoFieldMap.get(conditionMapper.getFieldName());
			if (value == null) {
				continue;
			}
			switch (conditionMapper.getConditionType()) {
			case Equal:
				dealConditionEqual(value, whereSql, conditionMapper, tableName,
						temp);
				break;
			case Like:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.Like, tableName, temp);
				break;
			case HeadLike:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.HeadLike, tableName, temp);
				break;
			case TailLike:
				dealConditionLike(whereSql, conditionMapper,
						ConditionType.TailLike, tableName, temp);
				break;
			default:
				break;
			}
		}
	}
}
