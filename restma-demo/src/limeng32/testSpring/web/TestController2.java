package limeng32.testSpring.web;

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

	// @RequestMapping(method = RequestMethod.GET)
	// public ModelAndView get() {
	// ModelAndView view = new ModelAndView();
	// User u = userService.select(1);
	// // System.out.println(u.getName());
	// view.addObject("user", u);
	// view.setViewName("testt");
	// System.out.println(userService);
	// // return "redirect:/test.jsp";
	// return view;
	// }

	@RequestMapping(method = RequestMethod.GET)
	public String get() {
		return "testt";
	}
}
