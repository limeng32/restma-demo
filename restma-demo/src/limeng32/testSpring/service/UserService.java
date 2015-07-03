package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.UserMapper;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;
import limeng32.testSpring.pojo.condition.Conditionable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceSupport<User> implements UserMapper {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private ArticleService articleService;

	@Override
	public User select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public void update(User t) {
		supportUpdate(mapper, t);
	}

	@Override
	public void insert(User t) {
		supportInsert(mapper, t);
	}

	@Override
	public void loadArticle(User user, ArticleCondition articleCondition) {
		articleCondition.setUser(user);
		user.setArticle(articleService.selectAll(articleCondition));
	}

	// @Override
	// public int count(Conditionable conditionable) {
	// return supportCount(mapper, conditionable);
	// }

	@Override
	public List<User> selectAll(Conditionable condition) {
		return supportSelectAll(mapper, condition);
	}

	@Override
	public void updatePersistent(User t) {
		supportUpdatePersistent(mapper, t);
	}

	@Override
	public void retrieve(User t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(User t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(User t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(User t) {
		return supportCount(mapper, t);
	}
}