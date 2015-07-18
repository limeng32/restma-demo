package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.User;

public interface UserMapper extends MapperFace<User> {

	public void loadArticle(User user, Article article);
}
