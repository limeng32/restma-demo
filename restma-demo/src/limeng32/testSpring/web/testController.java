package limeng32.testSpring.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import limeng32.mybatis.plugin.Order;
import limeng32.mybatis.plugin.SortParam;
import limeng32.testSpring.enums.GENDER;
import limeng32.testSpring.page.Page;
import limeng32.testSpring.page.PageParam;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.Association;
import limeng32.testSpring.pojo.Book;
import limeng32.testSpring.pojo.BookWriter;
import limeng32.testSpring.pojo.Level;
import limeng32.testSpring.pojo.User;
import limeng32.testSpring.pojo.Writer;
import limeng32.testSpring.pojo.condition.ArticleCondition;
import limeng32.testSpring.pojo.condition.AssociationCondition;
import limeng32.testSpring.pojo.condition.BookWriterCondition;
import limeng32.testSpring.pojo.condition.Conditionable;
import limeng32.testSpring.pojo.condition.LevelCondition;
import limeng32.testSpring.pojo.condition.WriterCondition;
import limeng32.testSpring.service.ArticleService;
import limeng32.testSpring.service.AssociationService;
import limeng32.testSpring.service.BookService;
import limeng32.testSpring.service.BookWriterService;
import limeng32.testSpring.service.UserService;
import limeng32.testSpring.service.WriterService;

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

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/test")
public class testController {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private AssociationService associationService;

	@Autowired
	private BookService bookService;

	@Autowired
	private BookWriterService bookWriterService;

	@Autowired
	private WriterService writerService;

	@RequestMapping()
	public ModelAndView get(
			@RequestParam(value = "s", required = false) String s) {

		ModelAndView view = new ModelAndView();
		User u = userService.select(1);
		// u.setSex(GENDER.male.getValue());
		List<GENDER> genderList = new ArrayList<GENDER>();
		for (GENDER gender : GENDER.values()) {
			genderList.add(gender);
		}

		int count = 0;
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
	public void asd2(@RequestParam String callback, PrintWriter printWriter,
			HttpServletResponse response) {
		response.setContentType("text/html;");
		User u = userService.select(2);
		// System.out.println(u.getName());
		// return "redirect:/test.jsp";
		String methodStr = callback + "(" + JSON.toJSONString(u) + ")";
		printWriter.write(methodStr);
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
		userService.loadArticle(user, new ArticleCondition());
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
		mm.addAttribute(new LinkedList<User>());
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
		// SqlSuffix sqlSuffix = new SqlSuffix();
		// sqlSuffix.setSortField(ARTICLE.id.value());
		// sqlSuffix.setOrder(SQL.asc.value());
		// pm.put(PLUGIN.sqlSuffix, sqlSuffix);
		// userService.loadArticle(u, pm);
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
		ArticleCondition articleCon = new ArticleCondition();
		articleCon.setTitle("%5%");
		userService.loadArticle(u, articleCon);
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
		ArticleCondition ac = new ArticleCondition();
		ac.setTitle("%555%");
		List<Article> l = articleService.selectAll(ac);
		Article a = (Article) l.toArray()[0];
		Article a1 = (Article) l.toArray()[1];
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson6")
	public String showArticleJson6(ModelMap mm) {
		ArticleCondition ac = new ArticleCondition();
		ac.setTitle("%555%");
		List<Article> l = articleService.selectAll(ac);
		ac.setTitle("%55%");
		List<Article> l1 = articleService.selectAll(ac);
		Article a = (Article) l.toArray()[0];
		Article a1 = (Article) l1.toArray()[1];
		System.out.println("1-" + (a.getUser() == a1.getUser()));
		System.out.println("2-" + (a.getUser()).equals((a1.getUser())));
		mm.addAttribute("_json", a1);
		return "showJson";
	}

	@RequestMapping(value = "/showArticleJson7")
	public String showArticleJson7(ModelMap mm) {
		ArticleCondition ac = new ArticleCondition();
		ac.setTitle("%555%");
		List<Article> l = articleService.selectAll(ac);
		List<Article> l1 = articleService.selectAll(ac);
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
	public String showArticleMix(ModelMap mm, @RequestParam("id") String id) {
		User user = userService.select(Integer.valueOf(id));
		ArticleCondition articleCon = new ArticleCondition();
		articleCon.setLimiter(new PageParam(1, 2));
		articleCon.setSorter(new SortParam(new Order(ArticleCondition.Field.id,
				Conditionable.Sequence.desc)));
		userService.loadArticle(user, articleCon);
		Page<Article> page = new Page<>(user.getArticle(),
				articleCon.getLimiter());

		// articleService.insert(a);
		// User user = new User();
		// user.setId(2);
		// a.setUser(user);
		// user.removeAllArticle();
		mm.addAttribute("_content", page);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle2Mix")
	public String showArticle2Mix(ModelMap mm) {
		User u = new User();
		u.setId(3);
		u.setName("姓名");
		Article a = new Article();
		a.setId(2);
		a.setTitle("标题");
		u.addArticle(a);
		mm.addAttribute("_content", u);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle3Mix")
	public String showArticle3Mix(ModelMap mm) {
		User u = new User();
		u.setId(3);
		u.setName("姓名");
		Article a = new Article();
		a.setId(2);
		a.setTitle("标题");
		a.setUser(u);
		mm.addAttribute("_content", a);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle4Mix")
	public String showArticle4Mix(ModelMap mm) {
		User u = new User();
		u.setId(3);
		u.setName("姓名");
		Article a = new Article();
		a.setId(2);
		a.setTitle("标题");
		a.setUser(u);
		Article otherA = new Article();
		otherA.setId(5);
		otherA.setTitle("另一篇");
		u.addArticle(otherA);
		mm.addAttribute("_content", a);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle5Mix")
	public String showArticle5Mix(ModelMap mm) {
		User u = new User();
		u.setId(3);
		u.setName("姓名");
		Article a = new Article();
		a.setId(2);
		a.setTitle("标题");
		a.setUser(u);
		Article otherA = new Article();
		otherA.setId(5);
		otherA.setTitle("另一篇");
		u.addArticle(otherA);
		mm.addAttribute("_content", otherA);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle6Mix")
	public String showArticle6Mix(ModelMap mm) {
		User u = new User();
		u.setId(3);
		u.setName("姓名");
		Article a = new Article();
		a.setId(2);
		a.setTitle("标题");
		a.setUser(u);
		Article otherA = new Article();
		otherA.setId(5);
		otherA.setTitle("另一篇");
		u.addArticle(otherA);
		mm.addAttribute("_content", u);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle7Mix")
	public String showArticle7Mix(ModelMap mm,
			@RequestParam(required = false) int pageNo) {
		// BookWriter bw = bookWriterService.select(4);
		// Book b = bookService.select(1);
		Writer w = writerService.select(1);
		BookWriterCondition bwc = new BookWriterCondition();
		bwc.setBook(new Book());
		bwc.setLimiter(new PageParam(pageNo, 1));
		// bwc.getBook().setTitle("3%");
		writerService.loadBookWriter(w, bwc);

		// Writer w = new Writer();
		// w.setName("%四%");
		// bwc.setWriter(w);
		// bookService.loadBookWriter(b, bwc);
		// User u = userService.select(1);
		// ArticleCondition articleCon = new ArticleCondition();
		// articleCon.setLimiter(new PageParam(1, 2));
		// userService.loadArticle(u, articleCon);
		Page<BookWriter> page = new Page<>(w.getBookWriter(), bwc.getLimiter());
		mm.addAttribute("_content", page);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle8Mix")
	public String showArticle8Mix(ModelMap mm) {
		// BookWriter bw = bookWriterService.select(4);
		// Book b = bookService.select(1);
		Writer w = writerService.select(1);
		BookWriterCondition bwc = new BookWriterCondition();
		bwc.setBook(new Book());
		bwc.setLimiter(new PageParam(1, 2));
		// bwc.getBook().setTitle("3%");
		writerService.loadBookWriter(w, bwc);

		// Writer w = new Writer();
		// w.setName("%四%");
		// bwc.setWriter(w);
		// bookService.loadBookWriter(b, bwc);
		// User u = userService.select(1);
		// ArticleCondition articleCon = new ArticleCondition();
		// articleCon.setLimiter(new PageParam(1, 2));
		// userService.loadArticle(u, articleCon);
		Page<BookWriter> page = new Page<>(w.getBookWriter(), bwc.getLimiter());
		mm.addAttribute("_content", page);
		return "showArticleMix";
	}

	public String testSelectAll(ModelMap mm) {
		// Writer writer = writerService.select(2);
		// writerService.update(writer);
		BookWriter bwc = new BookWriter();
		bwc.setBook(new Book());
		bwc.getBook().setTitle("4的诗歌");
		bwc.setWriter(new Writer());
		bwc.getWriter().setAssociation(new Association());
		bwc.getWriter().getAssociation().setName("新作协7");
		bwc.getWriter().setLevel(new Level());
		bwc.getWriter().getLevel().setName("级别二");
		List<BookWriter> bwList = bookWriterService.selectAll(bwc);
		// writer.getAssociation().setName("新作协7");
		// associationService.update(writer.getAssociation());
		// Writer writer2 = writerService.select(2);
		mm.addAttribute("_content", bwList);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle9Mix")
	public String testSelectAllUseCondition(ModelMap mm) {
		// BookWriter bwc = new BookWriter();
		// bwc.setWriter(new WriterCondition());
		WriterCondition wc = new WriterCondition();
		wc.setNameLike("李");
		wc.setAssociation(new AssociationCondition());
		wc.setLevel(new LevelCondition());
		((AssociationCondition) wc.getAssociation()).setNameLike("7");
		((LevelCondition) wc.getLevel()).setNameLike("级别");
		// bwc.setBook(new Book());
		// bwc.getBook().setTitle("3的故事");
		List<Writer> ret = writerService.selectAll(wc);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	public String testMybatis(ModelMap mm) {
		// User u = userService.select(3);
		Article ac = new Article();
		ac.setTitle("标题");
		List<Article> ret = articleService.selectAll(ac);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	public String testUpdate(ModelMap mm) {
		Article ret = articleService.select(268);
		ret.setTitle("ddd");
		ret.setUser(userService.select(1));
		articleService.update(ret);
		ret = articleService.select(268);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	public String testUpdatePersistent(ModelMap mm) {
		Article ret = articleService.select(268);
		ret.setUser(null);
		articleService.updatePersistent(ret);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	public String testDelete(ModelMap mm) {
		Article ret = articleService.select(264);
		articleService.delete(ret);
		return "showArticleMix";
	}

	public String testSelect(ModelMap mm) {
		Article ret = articleService.select(265);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle92Mix")
	public String showArticle92Mix(ModelMap mm) {
		Article a = articleService.select(172);
		// Article a2 = new Article();
		// a2.setId(268);
		// a2.setTitle("~");
		// a.getUser().addArticle(a2);
		User u = userService.select(1);
		u.addArticle(a);
		articleService.update(a);
		mm.addAttribute("_content", a);
		return "showArticleMix";
	}

	public String testSelect93(ModelMap mm) {
		BookWriter bw = bookWriterService.select(6);
		mm.addAttribute("_content", bw);
		return "showArticleMix";
	}

	public String testInsert(ModelMap mm) {
		Article ret = new Article();
		ret.setTitle(new Date().toString());
		articleService.insert(ret);
		return "showArticleMix";
	}

	public String testCount(ModelMap mm) {
		ArticleCondition ac = new ArticleCondition();
		ac.setTitleHeadLike("标");
		ac.setContent("test_content_3");
		int ret = articleService.count(ac);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	public String testCountUseCondition(ModelMap mm) {
		BookWriter bwc = new BookWriter();
		bwc.setWriter(new WriterCondition());
		WriterCondition wc = (WriterCondition) bwc.getWriter();
		wc.setNameLike("李");
		wc.setAssociation(new AssociationCondition());
		wc.setLevel(new LevelCondition());
		((AssociationCondition) wc.getAssociation()).setNameLike("推理");
		((LevelCondition) wc.getLevel()).setNameLike("级别");
		bwc.setBook(new Book());
		bwc.getBook().setTitle("4的诗歌");
		int ret = bookWriterService.count(bwc);
		mm.addAttribute("_content", ret);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle94Mix")
	public String showArticle94Mix(ModelMap mm) {
		ArticleCondition a = new ArticleCondition();
		// Article a1 = articleService.select(268);
		// Article a = new Article();
		// a.setTitle("naa");
		// int j = articleService.count(a);
		a.setTitle("%");
		// a.setTitleLike("1");
		// a.setTitleHeadLike("1");
		// a.setTitleTailLike("1");
		// a.setUser(new User());
		// a.getUser().setId(1);
		int i = articleService.count(a);
		mm.addAttribute("_content", i);
		return "showArticleMix";
	}

	@RequestMapping(value = "/showArticle10Mix")
	public String showArticle10Mix(ModelMap mm) {

		User nu = new User();
		nu.setName("新人");
		userService.insert(nu);

		Article na3 = new Article();
		na3.setTitle("naa3");
		na3.setUser(new User());
		articleService.insert(na3);

		Article na2 = new Article();
		na2.setTitle("naa2");
		na2.setUser(nu);
		articleService.insert(na2);
		// Article a = articleService.select(1);

		// User condition = new User();
		// condition.setName("zzhang3");
		// condition.setId(1);
		Article na = new Article();
		na.setTitle("naa");
		// na.setUser(new User());
		// na.getUser().setId(2);
		articleService.insert(na);

		mm.addAttribute("_content", na2);
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
		List<User> list = null;
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
