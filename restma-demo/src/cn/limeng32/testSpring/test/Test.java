package cn.limeng32.testSpring.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.limeng32.mybatis.enums.PLUGIN;
import cn.limeng32.mybatis.plugin.SqlSuffix;
import cn.limeng32.testSpring.SpringContextHolder;
import cn.limeng32.testSpring.enums.ARTICLE;
import cn.limeng32.testSpring.enums.PojoEnum;
import cn.limeng32.testSpring.enums.SQL;
import cn.limeng32.testSpring.enums.USER;
import cn.limeng32.testSpring.mapper.UserMapper;
import cn.limeng32.testSpring.pojo.Article;
import cn.limeng32.testSpring.pojo.User;
import cn.limeng32.testSpring.service.ArticleService;
import cn.limeng32.testSpring.service.User2Service;
import cn.limeng32.testSpring.service.UserService;

public class Test {
	public User select() {
		UserService userService = (UserService) SpringContextHolder
				.getBean("userService");
		User u2 = userService.select(1);
		return u2;
	}

	public User select2() {
		UserMapper userMapper = (UserMapper) SpringContextHolder
				.getBean("userMapper");
		User u = userMapper.select(1);
		u.setName(u.getName() + "1");
		u.setAddress(null);
		userMapper.update(u);
		u = userMapper.select(1);
		System.out.println("asd");
		return u;
	}

	public String select3() {
		String ret = "";

		UserService userService = (UserService) SpringContextHolder
				.getBean("userService");
		SqlSuffix sqlSuffix = new SqlSuffix();
		sqlSuffix.setSortField(USER.id.value());
		sqlSuffix.setOrder(SQL.desc.value());
		sqlSuffix.setShowCount(2);

		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(USER.name.toString(), "%уе%");
		// paraMap.put(USER.isable, USER.isableIgnore);

		paraMap.put(PLUGIN.sqlSuffix.toString(), sqlSuffix);
		List<User> list = userService.selectAll(paraMap);
		for (User u : list)
			ret += u.getName() + ";";
		return ret;
	}

	public User select4() {
		User2Service user2Service = (User2Service) SpringContextHolder
				.getBean("user2Service");
		User u2 = user2Service.select(1);
		return u2;
	}

	public void select5() {
		UserService userService = (UserService) SpringContextHolder
				.getBean("userService");
		User u1 = new User();
		u1.setName("aaa");
		User u2 = new User();
		u2.setName("bbb");
		userService.insert(u1);
		userService.insert(u2);
	}

	public void select6() {
		User2Service user2Service = (User2Service) SpringContextHolder
				.getBean("user2Service");
		user2Service.insert(new User());
	}

	public String select7() {
		ArticleService service = (ArticleService) SpringContextHolder
				.getBean("articleService");
		Article a = service.select(1);
		User u = a.getUser();
		return u.getName();
	}

	public String select8() {
		String ret = "";
		UserService service = (UserService) SpringContextHolder
				.getBean("userService");
		User user = service.select(1);
		Map<PojoEnum, Object> map = new HashMap<PojoEnum, Object>();
		map.put(ARTICLE.title, "%╠Й%");
		service.loadArticle(user, map);
		for (Article a : user.getArticle()) {
			ret += a.getUser().getName() + ":" + a.getTitle() + ";";
		}
		return ret;
	}

	public void testCache() {
		// UserService service = (UserService) SpringContextHolder
		// .getBean("userService");
	}

	public void testController() {
	}
}
