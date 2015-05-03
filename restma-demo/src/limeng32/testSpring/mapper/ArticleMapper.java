package limeng32.testSpring.mapper;

import java.util.List;
import java.util.Map;

import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.condition.Conditionable;

public interface ArticleMapper extends MapperFace<Article> {

	public List<Article> selectAllByEnum(Map<String, Object> map);

	public List<Article> select2(Conditionable conditionable);

}
