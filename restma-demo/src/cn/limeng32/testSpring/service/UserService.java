package cn.limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.limeng32.testSpring.enums.ARTICLE;
import cn.limeng32.testSpring.enums.PojoEnum;
import cn.limeng32.testSpring.enums.USER;
import cn.limeng32.testSpring.mapper.UserMapper;
import cn.limeng32.testSpring.pojo.User;

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

	public List<User> selectAllUseEnum(Map<PojoEnum, Object> map) {
		return supportSelectAllUseEnum(mapper, map);
	}

	public void loadArticle(User user, Map<PojoEnum, Object> map) {
		map.put(ARTICLE.userid, user);
		map.put(USER.id, 100);
		user.setArticle(articleService.selectAllUseEnum(map));
	}
}