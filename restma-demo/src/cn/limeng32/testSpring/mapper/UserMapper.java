package cn.limeng32.testSpring.mapper;

import java.util.Map;

import cn.limeng32.testSpring.enums.PojoEnum;
import cn.limeng32.testSpring.mapper.MapperFace;
import cn.limeng32.testSpring.pojo.User;

public interface UserMapper extends MapperFace<User> {

	public void loadArticle(User user, Map<PojoEnum, Object> map);
}
