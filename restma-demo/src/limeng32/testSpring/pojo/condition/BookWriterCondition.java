package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;
import limeng32.mybatis.plugin.Sortable;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapperAnnotation;
import limeng32.testSpring.pojo.BookWriter;
import limeng32.testSpring.pojo.Queryable;

@QueryMapperAnnotation(tableName = "BookWriter")
public class BookWriterCondition extends BookWriter implements Conditionable,
		Isable {

	private static final long serialVersionUID = 1L;

	public enum Field implements Queryable {
		tableName("BookWriter"), id("id"), bookid("bookid"), writerid(
				"writerid");

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
