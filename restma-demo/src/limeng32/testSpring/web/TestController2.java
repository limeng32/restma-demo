package limeng32.testSpring.web;

import limeng32.mybatis.plugin.Order;
import limeng32.mybatis.plugin.SortParam;
import limeng32.testSpring.page.Page;
import limeng32.testSpring.page.PageParam;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;
import limeng32.testSpring.pojo.condition.Conditionable;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/testt")
public class TestController2 {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		ArticleCondition articleCon = new ArticleCondition();
		User user = userService.select(1);
		// articleCon.setTitle("%55%");
		articleCon.setLimiter(new PageParam(1, 2));
		articleCon.setSorter(new SortParam(new Order(ArticleCondition.Field.id,
				Conditionable.Sequence.asc)));
		userService.loadArticle(user, articleCon);
		System.out.println("1--" + user.getArticle());
		articleCon.setLimiter(new PageParam(2, 2));
		articleCon.setSorter(new SortParam(new Order(ArticleCondition.Field.id,
				Conditionable.Sequence.asc)));
		userService.loadArticle(user, articleCon);
		Page<Article> page = new Page<>(user.getArticle(),
				articleCon.getLimiter());
		String text = JSON.toJSONString(page);
		System.out.println("2--" + text);
		// System.out.println("--" + articleCon.getLimiter().getTotalCount());
		return "testt";
	}
}
