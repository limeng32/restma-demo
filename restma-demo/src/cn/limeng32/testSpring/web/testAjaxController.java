package cn.limeng32.testSpring.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.limeng32.testSpring.pojo.Article;
import cn.limeng32.testSpring.pojo.User;
import cn.limeng32.testSpring.service.ArticleService;
import cn.limeng32.testSpring.service.UserService;
import cn.limeng32.testSpring.enums.ARTICLE;
import cn.limeng32.testSpring.enums.USER;

@Controller
@RequestMapping(value = "/testAjax")
public class testAjaxController {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView ret = new ModelAndView();
		ret.setViewName("testAjax");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USER.name.toString(), "%1%");
		int count = userService.count(map);
		ret.addObject("count", count);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put(ARTICLE.title.toString(), "%1%");
		int articleCount = articleService.count(map2);
		ret.addObject("articleCount", articleCount);

		return ret;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public void test(User user, PrintWriter printWriter) {
		userService.insert(user);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(USER.name.toString(), "%1%");
		int count = userService.count(map);
		printWriter.write(count + "");
		printWriter.flush();
		printWriter.close();
	}

	@RequestMapping(value = "/test2")
	public void test2(Article article, PrintWriter printWriter) {
		articleService.insert(article);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ARTICLE.title.toString(), "%1%");
		// int count = articleService.count(map);
		Date count = articleService.select(1).getUpdateTime();
		printWriter.write(count + "");
		printWriter.flush();
		printWriter.close();
	}
}
