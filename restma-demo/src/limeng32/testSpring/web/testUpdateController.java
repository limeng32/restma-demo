package limeng32.testSpring.web;

import javax.validation.Valid;

import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/testUpdate")
public class testUpdateController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get() {
		ModelAndView ret = new ModelAndView();
		ret.setViewName("testUpdate");
		Article article = articleService.select(1);
		ret.addObject("article", article);
		return ret;
	}

	@RequestMapping(value = "/update")
	public String update(@Valid User user, BindingResult bindingResult,
			ModelMap modelMap) {
		if (bindingResult.hasErrors()) {
			return "testUpdate";
		}
		userService.insert(user);
		Article article = articleService.select(2);
		modelMap.addAttribute("article", article);
		// modelMap.addAttribute("user", userService.select(1));
		return "testUpdate";
	}
}
