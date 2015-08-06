package limeng32.mybatis.plugin.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import limeng32.mybatis.plugin.mapper.able.AbleConditionType;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

/**
 * 解决软删除标志类型AbleCondition的处理器
 * 
 * @author limeng32
 * 
 */
@MappedTypes({ AbleConditionType.class })
public class AbleConditionHandler extends BaseTypeHandler<AbleConditionType>
		implements TypeHandler<AbleConditionType> {

	@Override
	public AbleConditionType getNullableResult(ResultSet arg0, String arg1)
			throws SQLException {
		/* 因为不会将ableCondition的值注入的pojo中，所以这里直接返回null */
		return null;
	}

	@Override
	public AbleConditionType getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		/* 因为不会将ableCondition的值注入的pojo中，所以这里直接返回null */
		return null;
	}

	@Override
	public AbleConditionType getNullableResult(CallableStatement arg0, int arg1)
			throws SQLException {
		/* 因为不会将ableCondition的值注入的pojo中，所以这里直接返回null */
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1,
			AbleConditionType arg2, JdbcType arg3) throws SQLException {
		arg0.setInt(arg1, arg2.value());
	}

}
