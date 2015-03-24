package limeng32.testSpring.mapper;

import java.util.Map;

import limeng32.testSpring.enums.PojoEnum;
import limeng32.testSpring.pojo.User;

public interface UserMapper extends MapperFace<User> {

	public void loadArticle(User user, Map<PojoEnum, Object> map);
}
