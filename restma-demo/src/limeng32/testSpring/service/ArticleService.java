package limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import limeng32.testSpring.mapper.ArticleMapper;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.Queryable;
import limeng32.testSpring.pojo.condition.Conditionable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ServiceSupport<Article> implements
		ArticleMapper {

	@Autowired
	private ArticleMapper mapper;

	public List<Article> selectAllByEnum(Map<String, Object> map) {
		return mapper.selectAllByEnum(map);
	}

	public Article select(int id) {
		return supportSelect(mapper, id);
	}

	public int count(Map<String, Object> map) {
		return supportCount(mapper, map);
	}

	public void insert(Article t) {
		supportInsert(mapper, t);
	}

	public void update(Article t) {
		supportUpdate(mapper, t);
	}

	public List<Article> selectAllUseEnum(Map<Queryable, Object> map) {
		return supportSelectAllUseEnum(mapper, map);
	}

	public List<Article> selectAll(Map<String, Object> map) {
		return supportSelectAll(mapper, map);
	}

	@Override
	public List<Article> select2(Conditionable conditionable) {
		return mapper.select2(conditionable);
	}
}