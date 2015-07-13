package limeng32.mybatis.plugin.mapper.annotation;

import org.apache.ibatis.type.JdbcType;

/**
 * 条件映射类，用于描述被ConditionMapperAnnotation标注过的对象字段和sql之间的对应关系
 * 
 * @author limeng32
 * 
 */
public class ConditionMapper implements Mapperable {

	/**
	 * Java对象字段名
	 */
	private String fieldName;

	/**
	 * 数据库表字段名
	 */
	private String dbFieldName;

	/**
	 * 数据库字段对应的jdbc类型
	 */
	private JdbcType jdbcType;

	/**
	 * 条件类型
	 */
	private ConditionType conditionType;

	/**
	 * 如果是外键，对应数据库其他表的主键字段的名称。不是外键时为null。
	 */
	private String dbAssociationUniqueKey;

	/**
	 * 此变量是否对应数据库表的外键。默认为否。
	 */
	private boolean isForeignKey;

	/**
	 * 如果此变量对应数据库表的外键，ForeignFieldName表示相关表的主键的Java对象字段名。不是外键时为null。
	 */
	private String ForeignFieldName;

	@Override
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String getDbFieldName() {
		return dbFieldName;
	}

	public void setDbFieldName(String dbFieldName) {
		this.dbFieldName = dbFieldName;
	}

	@Override
	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	@Override
	public String getDbAssociationUniqueKey() {
		return dbAssociationUniqueKey;
	}

	public void setDbAssociationUniqueKey(String dbAssociationUniqueKey) {
		this.dbAssociationUniqueKey = dbAssociationUniqueKey;
	}

	@Override
	public boolean isForeignKey() {
		return isForeignKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	@Override
	public String getForeignFieldName() {
		return ForeignFieldName;
	}

	public void setForeignFieldName(String foreignFieldName) {
		ForeignFieldName = foreignFieldName;
	}

	public ConditionType getConditionType() {
		return conditionType;
	}

	public void setConditionType(ConditionType conditionType) {
		this.conditionType = conditionType;
	}

}
