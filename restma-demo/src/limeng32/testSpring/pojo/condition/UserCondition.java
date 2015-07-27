package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.mapper.annotation.QueryMapperAnnotation;
import limeng32.testSpring.pojo.User;

@QueryMapperAnnotation(tableName = "User")
public class UserCondition extends User {

	private static final long serialVersionUID = 1L;

}
