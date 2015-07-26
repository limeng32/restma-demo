package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.mapper.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.ConditionType;
import limeng32.testSpring.pojo.Book;

public class BookCondition extends Book {

	private static final long serialVersionUID = 1L;

	@ConditionMapperAnnotation(dbFieldName = "title", conditionType = ConditionType.Like)
	private String titleLike;

	public String getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

}
