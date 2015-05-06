package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;
import limeng32.mybatis.plugin.Sortable;
import limeng32.testSpring.pojo.Article;
import limeng32.testSpring.pojo.Queryable;

public class ArticleCondition extends Article implements Conditionable, Isable {

	private static final long serialVersionUID = 1L;

	public enum Field implements Queryable {
		tableName("article"), id("id"), userid("userid"), title("title"), content(
				"content");

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

	private String isable;

	@Override
	public Limitable getLimiter() {
		return limiter;
	}

	@Override
	public void setLimiter(Limitable limiter) {
		this.limiter = limiter;
	}

	@Override
	public String getIsable() {
		return isable;
	}

	@Override
	public void setIsable(String isable) {
		this.isable = isable;
	}

	@Override
	public Sortable getSorter() {
		return sorter;
	}

	@Override
	public void setSorter(Sortable sorter) {
		this.sorter = sorter;
	}

}
