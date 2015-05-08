package limeng32.testSpring.web;

import limeng32.mybatis.plugin.Order;
import limeng32.mybatis.plugin.SortParam;
import limeng32.testSpring.page.PageParam;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;
import limeng32.testSpring.pojo.condition.Conditionable;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		articleCon.setTitle("%55%");
		articleCon.setLimiter(new PageParam(1, 2));
		articleCon.setSorter(new SortParam(new Order(ArticleCondition.Field.id,
				Conditionable.Sequence.asc)));
		System.out.println("1--" + articleCon.getSorter().toString());
		userService.loadArticle(user, articleCon);
		System.out.println("1--" + user.getArticle());
		articleCon.setSorter(new SortParam(new Order(ArticleCondition.Field.id,
				Conditionable.Sequence.desc)));
		System.out.println("2--" + articleCon.getSorter().toString());
		userService.loadArticle(user, articleCon);
		System.out.println("2--" + user.getArticle());
		// System.out.println("--" + articleCon.getLimiter().getTotalCount());
		return "testt";
	}
}
