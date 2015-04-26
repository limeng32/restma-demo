package limeng32.testSpring.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import limeng32.mybatis.plugin.SqlSuffix;
import limeng32.testSpring.enums.ARTICLE;
import limeng32.testSpring.enums.GENDER;
import limeng32.testSpring.enums.USER;
import limeng32.testSpring.enums.sql.SQL;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.Queryable;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/test")
public class testController {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping()
	public ModelAndView get(
			@RequestParam(value = "s", required = false) String s) {

		ModelAndView view = new ModelAndView();
		User u = userService.select(1);
		Article a = articleService.select(1);
		Map<Queryable, Object> pm = new HashMap<Queryable, Object>();
		pm.put(SQL.desc, ARTICLE.id);
		userService.loadArticle(u, pm);
		System.out.println(u.getArticle());
		pm.put(SQL.desc, ARTICLE.title);
		pm.put(SQL.sorter, 1);
		userService.loadArticle(u, pm);
		System.out.println(u.getArticle());
		u.removeArticle(a);
		// u.setSex(GENDER.male.getValue());
		List<GENDER> genderList = new ArrayList<GENDER>();
		for (GENDER gender : GENDER.values()) {
			genderList.add(gender);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		int count = userService.count(map);
		view.addObject("user", u);
		view.addObject("count", count);
		view.addObject("s", s);
		view.addObject("sex", s);
		view.addObject("genders", genderList);
		view.setViewName("test");
		// return "redirect:/test.jsp";
		return view;
	}

	@RequestMapping(value = "/asd", method = RequestMethod.POST)
	public void asd(User user, PrintWriter printWriter,
			HttpServletResponse response) {
		response.setContentType("text/html;");
		User u = userService.select(2);
		// System.out.println(u.getName());
		// return "redirect:/test.jsp";
		printWriter.write(u.getName());
		printWriter.flush();
		printWriter.close();
	}

	@RequestMapping(value = "/asd", method = RequestMethod.GET)
	public void asd2(PrintWriter printWriter, HttpServletResponse response) {
		response.setContentType("text/html;");
		User u = userService.select(2);
		// System.out.println(u.getName());
		// return "redirect:/test.jsp";
		printWriter.write(u.getName());
		printWriter.flush();
		printWriter.close();
	}

	@RequestMapping(value = "/handle41")
	public String handle41(@RequestBody String requestBody, ModelMap map)
			throws UnsupportedEncodingException {
		System.out.println("+++++++++++++++-" + requestBody);
		System.out.println("++++++++++++++++"
				+ URLDecoder.decode(requestBody, "utf-8"));
		User u = userService.select(1);
		map.addAttribute("user", u);
		List<GENDER> genderList = new ArrayList<GENDER>();
		for (GENDER gender : GENDER.values()) {
			genderList.add(gender);
		}
		map.addAttribute("genders", genderList);
		return "test";
	}

	@ResponseBody
	@RequestMapping(value = "/handle42/{imageId}")
	public byte[] handle42(@PathVariable String imageId) throws IOException {
		System.out.println("--" + imageId);
		Resource res = new ClassPathResource("/moeTiger4.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		return fileData;
	}

	@RequestMapping(value = "/handle43")
	public String handle43(HttpEntity<String> httpEntity) {
		// System.out
		// .println(URLDecoder.decode(httpEntity.getBody(), "utf-8"));
		System.out.println("----------------" + httpEntity.getBody());
		return "test";
	}

	@RequestMapping(value = "/handle44/{imageId}")
	public ResponseEntity<byte[]> handle44(@PathVariable String imageId)
			throws Throwable {
		System.out.println("++" + imageId);
		Resource res = new ClassPathResource("/moeTiger4.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(
				fileData, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/handle51")
	public ResponseEntity<User> handle51(@RequestParam int id) {
		User user = userService.select(id);
		userService.loadArticle(user, new HashMap<Queryable, Object>());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/handle52")
	public ResponseEntity<Article> handle52(@RequestParam("id") int id) {
		Article article = articleService.select(id);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@RequestMapping(value = "/handle53")
	public ResponseEntity<Article> handle53(@RequestParam int id) {
		Article article = articleService.select(id);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}

	@RequestMapping(value = "/handle81")
	public String handle81(@RequestParam User user, ModelMap modelMap) {
		modelMap.put("user", user);
		return "test";
	}

	@RequestMapping(value = "/handle102")
	public void handle102(@RequestParam("s") String s) {
		String a = s;
		System.out.println(a);
	}

	@RequestMapping(value = "/userListByFtl")
	public String showUserListByFtl(ModelMap mm) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put(USER.name.value(), "%中文%");
		List<User> userList = userService.selectAll(pm);
		mm.addAttribute(userList);
		return "userListByFtl";
	}

	@RequestMapping(value = "/showUserListByXls")
	public String showUserListByXls(ModelMap mm) {

		return "userListExcel";
	}

	@RequestMapping(value = "/showUserListByPdf")
	public String showUserListByPdf(ModelMap mm) {

		return "userListPdf";
	}

	@RequestMapping(value = "/showArticleXml")
	public String showArticleXml(@RequestParam("id") int id, ModelMap mm) {
		Article a = articleService.select(id);
		mm.addAttribute("_xml", a);
		return "showXml";
	}

	@RequestMapping(value = "/showArticleJson")
	public String showArticleJson(Article article, ModelMap mm) {
		article.setUser(null);
		articleService.update(article);
		Article a = articleService.select(13);
		User u = userService.select(1);
		// u.removeArticle(a);
		Map<Queryable, Object> pm = new HashMap<Queryable, Object>();
		pm.put(ARTICLE.title, "%5%");
		SqlSuffix sqlSuffix = new SqlSuffix();
		sqlSuffix.setSortField(ARTICLE.id.value());
		sqlSuffix.setOrder(SQL.asc.value());
		// pm.put(PLUGIN.sqlSuffix, sqlSuffix);
		userService.loadArticle(u, pm);
		u.addArticle(a);
		Article a1 = (Article) u.getArticle().toArray()[0];
		// a2.setUser(null);
		articleService.update(a1);
		u.setName("zzhang3");
		userService.update(u);
		System.out.println("----------------------------------");
		// Article a13 = articleService.select(13);
		// u.addArticle(a13);
		// mm.addAttribute(a);
		mm.addAttribute("_json", u);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson2")
	public String showArticleJson2(ModelMap mm) {
		Article a = articleService.select(2);
		mm.addAttribute("_json", a);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson3")
	public String showArticleJson3(ModelMap mm) {
		User u = userService.select(1);
		Map<Queryable, Object> pm = new HashMap<Queryable, Object>();
		pm.put(ARTICLE.title, "%5%");
		userService.loadArticle(u, pm);
		Article a = (Article) u.getArticle().toArray()[0];
		Article a1 = articleService.select(13);
		System.out.println("1-" + (a == a1));
		System.out.println("2-" + a.equals(a1));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson4")
	public String showArticleJson4(ModelMap mm) {
		Article a = articleService.select(13);
		Article a1 = articleService.select(14);
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson5")
	public String showArticleJson5(ModelMap mm) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put(ARTICLE.title.value(), "%555%");
		List<Article> l = articleService.selectAll(pm);
		Article a = (Article) l.toArray()[0];
		Article a1 = (Article) l.toArray()[1];
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson6")
	public String showArticleJson6(ModelMap mm) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put(ARTICLE.title.value(), "%555%");
		List<Article> l = articleService.selectAll(pm);
		pm.put(ARTICLE.title.value(), "%55%");
		List<Article> l1 = articleService.selectAll(pm);
		Article a = (Article) l.toArray()[0];
		Article a1 = (Article) l1.toArray()[1];
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson7")
	public String showArticleJson7(ModelMap mm) {
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put(ARTICLE.title.value(), "%555%");
		List<Article> l = articleService.selectAll(pm);
		List<Article> l1 = articleService.selectAll(pm);
		Article a = (Article) l.toArray()[0];
		Article a1 = (Article) l1.toArray()[1];
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson8")
	public String showArticleJson8(ModelMap mm) {
		// Article a = new Article();
		Article a = articleService.select(2);
		a.setUser(null);
		articleService.update(a);
		// User user = new User();
		// user.setId(2);
		// a.setUser(user);
		// user.removeAllArticle();
		mm.addAttribute("_content", a);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson9")
	public String showArticleJson9(ModelMap mm) {
		Article a = new Article();
		a.setTitle("asdasd");
		articleService.insert(a);
		// User user = new User();
		// user.setId(2);
		// a.setUser(user);
		// user.removeAllArticle();
		mm.addAttribute("_json", a);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleMix")
	public String showArticleMix(ModelMap mm, @RequestParam("id") int id) {
		Article a = articleService.select(id);
		// articleService.insert(a);
		// User user = new User();
		// user.setId(2);
		// a.setUser(user);
		// user.removeAllArticle();
		mm.addAttribute("_content", a);
		return "showArticleMix";
	}

	@RequestMapping(value = "/upload")
	public String upload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			file.transferTo(new File("d:/" + file.getOriginalFilename()));
			return "redirect:/success.html";
		} else {
			return "redirect:/fail.html";
		}
	}

	@RequestMapping(value = "/throwException")
	public String throwException() {
		if ("".equals("")) {
			throw new RuntimeException("asd");
		}
		return "redirect:/success.html";
	}

	@RequestMapping(value = "/useEnum2")
	public String useEnum2() {
		Map<Queryable, Object> map = new HashMap<Queryable, Object>();
		map.put(ARTICLE.id, 2);
		// map.put(USER.id, 2);
		// List<Article> list = articleService.selectAllUseEnum(map);
		List<User> list = userService.selectAllUseEnum(map);
		System.out.println("-" + list);
		return null;
	}

	@RequestMapping(value = "/testSelect")
	public String testSelect() {
		Article a = articleService.select(2);
		System.out.println("-" + a);
		return null;
	}

	// @ExceptionHandler(RuntimeException.class)
	// public String handleException(RuntimeException re,
	// HttpServletRequest request) {
	// return "forward:/fail.html";
	// }
}
