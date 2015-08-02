package limeng32.mybatis.plugin.mapper.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
@MappedTypes({ Boolean.class })
public class BooleanHandler extends BaseTypeHandler<Boolean> implements
		TypeHandler<Boolean> {

	@Override
	public Boolean getNullableResult(ResultSet arg0, String arg1)
			throws SQLException {
		switch (arg0.getString(arg1)) {
		case "1":
			return true;
		case "0":
			return false;
		default:
			return null;
		}
	}

	@Override
	public Boolean getNullableResult(ResultSet arg0, int arg1)
			throws SQLException {
		switch (arg0.getString(arg1)) {
		case "1":
			return true;
		case "0":
			return false;
		default:
			return null;
		}
	}

	@Override
	public Boolean getNullableResult(CallableStatement arg0, int arg1)
			throws SQLException {
		switch (arg0.getString(arg1)) {
		case "1":
			return true;
		case "0":
			return false;
		default:
			return null;
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1,
			Boolean arg2, JdbcType arg3) throws SQLException {
		if (arg2) {
			arg0.setString(arg1, "1");
		} else {
			arg0.setString(arg1, "0");
		}
	}

}
