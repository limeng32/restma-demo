package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.ArticleMapper;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.condition.Conditionable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ServiceSupport<Article> implements
		ArticleMapper {

	@Autowired
	private ArticleMapper mapper;

	@Override
	public Article select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public void insert(Article t) {
		supportInsert(mapper, t);
	}

	@Override
	public void update(Article t) {
		supportUpdate(mapper, t);
	}

	@Override
	public List<Article> selectAll(Conditionable conditionable) {
		return mapper.selectAll(conditionable);
	}

	@Override
	public int count(Conditionable conditionable) {
		return mapper.count(conditionable);
	}
}