package limeng32.testSpring.web;

import java.io.PrintWriter;
import java.util.Date;

import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.condition.ArticleCondition;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		ret.addObject("count", 0);

		ArticleCondition ac = new ArticleCondition();
		ac.setTitle("%1%");
		int articleCount = articleService.count(ac);
		ret.addObject("articleCount", articleCount);

		return ret;
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public void test(User user, PrintWriter printWriter) {
		userService.insert(user);
		int count = 0;
		printWriter.write(count + "");
		printWriter.flush();
		printWriter.close();
	}

	@RequestMapping(value = "/test2")
	public void test2(Article article, PrintWriter printWriter) {
		articleService.insert(article);
		Date count = articleService.select(1).getUpdateTime();
		printWriter.write(count + "");
		printWriter.flush();
		printWriter.close();
	}
}
