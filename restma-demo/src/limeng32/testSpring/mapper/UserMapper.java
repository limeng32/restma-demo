package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;

public interface UserMapper extends MapperFace<User> {

	public void loadArticle(User user, ArticleCondition articleCondition);

	public User select1(User user);
}
