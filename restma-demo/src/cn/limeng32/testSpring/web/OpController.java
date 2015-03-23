package cn.limeng32.testSpring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.limeng32.testSpring.service.ArticleService;

@Controller
@RequestMapping(value = "/op")
public class OpController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "op";
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test(ModelMap mm,
			@RequestParam(value = "attempts", required = false) String attempts) {
		String[] array = { "恭喜您，只尝试", attempts, "次就完成了游戏。", "如需与作者联系，请加QQ ",
				"540906853" };
		mm.addAttribute("_content", array);
		return "Mix";
	}
}
