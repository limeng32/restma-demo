package limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import limeng32.testSpring.mapper.UserMapper;
import limeng32.testSpring.pojo.Queryable;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceSupport<User> implements UserMapper {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private ArticleService articleService;

	public User select(int id) {
		return supportSelect(mapper, id);
	}

	public List<User> selectAll(Map<String, Object> map) {
		return supportSelectAll(mapper, map);
	}

	public int count(Map<String, Object> map) {
		return supportCount(mapper, map);
	}

	public void update(User t) {
		supportUpdate(mapper, t);
	}

	public void insert(User t) {
		supportInsert(mapper, t);
	}

	public List<User> selectAllUseEnum(Map<Queryable, Object> map) {
		return supportSelectAllUseEnum(mapper, map);
	}

	public void loadArticle(User user, ArticleCondition articleCondition) {
		articleCondition.setUser(user);
		user.setArticle(articleService.select2(articleCondition));
	}
}