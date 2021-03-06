package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;
import limeng32.mybatis.plugin.Sortable;
import limeng32.mybatis.plugin.mapper.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.ConditionType;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapperAnnotation;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.Queryable;

@QueryMapperAnnotation(tableName = "Article")
public class ArticleCondition extends Article implements Conditionable {

	private static final long serialVersionUID = 1L;

	public static final String field_tableName = "article";

	public static final String field_id = "id";

	public static final String field_userid = "userid";

	public static final String field_title = "title";

	public static final String field_content = "content";

	public enum Field implements Queryable {
		tableName(field_tableName), id(field_id), userid(field_userid), title(
				field_title), content(field_content);

		private final String value;

		private Field(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

		@Override
		public String getTableName() {
			return tableName.value;
		}
	}

	private Limitable limiter;

	private Sortable sorter;

	@Override
	public Limitable getLimiter() {
		return limiter;
	}

	@Override
	public void setLimiter(Limitable limiter) {
		this.limiter = limiter;
	}

	@Override
	public Sortable getSorter() {
		return sorter;
	}

	@Override
	public void setSorter(Sortable sorter) {
		this.sorter = sorter;
	}

	@ConditionMapperAnnotation(dbFieldName = ArticleCondition.field_title, conditionType = ConditionType.Like)
	private String titleLike;

	@ConditionMapperAnnotation(dbFieldName = ArticleCondition.field_title, conditionType = ConditionType.HeadLike)
	private String titleHeadLike;

	@ConditionMapperAnnotation(dbFieldName = ArticleCondition.field_title, conditionType = ConditionType.TailLike)
	private String titleTailLike;

	public String getTitleLike() {
		return titleLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public String getTitleHeadLike() {
		return titleHeadLike;
	}

	public void setTitleHeadLike(String titleHeadLike) {
		this.titleHeadLike = titleHeadLike;
	}

	public String getTitleTailLike() {
		return titleTailLike;
	}

	public void setTitleTailLike(String titleTailLike) {
		this.titleTailLike = titleTailLike;
	}

}
