package limeng32.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import limeng32.mybatis.enums.PLUGIN;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SqlPlugin implements Interceptor {
	private static String dialect = "";

	private static String selectAllMatcher = "";

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (dialect == null || dialect.equals("")) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		selectAllMatcher = p.getProperty("selectAllMatcher");
		if (selectAllMatcher == null || selectAllMatcher.equals("")) {
			try {
				throw new PropertyException("selectAll property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

	/* 此方法中当SqlSuffix.getShowCount()不为null时，则自动获取totalResult */
	@SuppressWarnings("unchecked")
	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper
					.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper
					.getValueByFieldName(delegate, "mappedStatement");
			if (mappedStatement.getId().matches(selectAllMatcher)) {
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();
				if (parameterObject == null) {
					throw new NullPointerException("parameterObject error");
				} else {
					SqlSuffix sqlSuffix = null;
					if (parameterObject instanceof Map) {
						sqlSuffix = (SqlSuffix) (((Map<String, Object>) parameterObject)
								.get(PLUGIN.sqlSuffix.toString()));
					} else if (parameterObject instanceof SqlSuffix) {
						sqlSuffix = (SqlSuffix) parameterObject;
					} else {
						Field pageField = ReflectHelper.getFieldByFieldName(
								parameterObject, "sqlSuffix");
						if (pageField != null) {
							sqlSuffix = (SqlSuffix) ReflectHelper
									.getValueByFieldName(parameterObject,
											"sqlSuffix");
							ReflectHelper.setValueByFieldName(parameterObject,
									"sqlSuffix", sqlSuffix);
						} else {
							throw new NoSuchFieldException(parameterObject
									.getClass().getName());
						}
					}
					String sql = boundSql.getSql();
					if (sqlSuffix != null) {
						if (sqlSuffix.getShowCount() > 0) {
							Connection connection = (Connection) ivk.getArgs()[0];
							String countSql = "select count(0) from (" + sql
									+ ") myCount";
							PreparedStatement countStmt = connection
									.prepareStatement(countSql);
							BoundSql countBS = new BoundSql(
									mappedStatement.getConfiguration(),
									countSql, boundSql.getParameterMappings(),
									parameterObject);
							setParameters(countStmt, mappedStatement, countBS,
									parameterObject);
							ResultSet rs = countStmt.executeQuery();
							int count = 0;
							if (rs.next()) {
								count = rs.getInt(1);
							}
							rs.close();
							countStmt.close();
							sqlSuffix.setTotalResult(count);
						}
					}
					String pageSql = generatePageSql(sql, sqlSuffix);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
				}
			} else {
			}
		}
		return ivk.proceed();
	}

	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters")
				.object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null
					: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName
							.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value)
									.getValue(
											propertyName.substring(prop
													.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject
								.getValue(propertyName);
					}
					TypeHandler<Object> typeHandler = (TypeHandler<Object>) parameterMapping
							.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter "
										+ propertyName + " of statement "
										+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value,
							parameterMapping.getJdbcType());
				}
			}
		}
	}

	private String generatePageSql(String sql, SqlSuffix suffix) {
		if (suffix != null && (dialect != null || !dialect.equals(""))) {
			StringBuffer pageSql = new StringBuffer();
			if ("mysql".equals(dialect)) {
				pageSql.append(sql);
				if (suffix.getGroupField() != null) {
					pageSql.append(" group by ").append(suffix.getGroupField());
				}
				// if (suffix.getSortField() != null) {
				// pageSql.append(" order by ").append(suffix.getSortField());
				// if (suffix.getOrder() != null) {
				// pageSql.append(" ").append(suffix.getOrder());
				// }
				// }
				if (suffix.getSorterList() != null
						&& suffix.getSorterList().size() > 0) {
					pageSql.append(" order by ");
					for (String[] sorter : suffix.getSorterList()) {
						pageSql.append(sorter[0]).append(" ").append(sorter[1])
								.append(",");
					}
					pageSql.deleteCharAt(pageSql.length() - 1);
				}
				if (suffix.getShowCount() > 0) {
					pageSql.append(" limit " + suffix.getCurrentResult() + ","
							+ suffix.getShowCount());
				}
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

}
