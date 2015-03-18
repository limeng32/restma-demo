package cn.limeng32.testSpring.mapper;

import java.util.List;
import java.util.Map;

import cn.limeng32.testSpring.mapper.MapperFace;
import cn.limeng32.testSpring.pojo.Article;

public interface ArticleMapper extends MapperFace<Article> {

	public List<Article> selectAllByEnum(Map<String, Object> map);

}
