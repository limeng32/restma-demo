package limeng32.mybatis.plugin.mapper.annotation;

import org.apache.ibatis.type.JdbcType;

public interface Mapperable {

	String getFieldName();

	String getDbFieldName();

	boolean isForeignKey();

	String getForeignFieldName();

	JdbcType getJdbcType();

	String getDbAssociationUniqueKey();

}
