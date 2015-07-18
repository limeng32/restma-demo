package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.UserMapper;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.User;

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
	public void loadArticle(User user, Article article) {
		article.setUser(user);
		user.setArticle(articleService.selectAll(article));
	}

	@Override
	public List<User> selectAll(User t) {
		return supportSelectAll(mapper, t);
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