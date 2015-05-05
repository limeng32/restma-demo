package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;
import limeng32.mybatis.plugin.Sortable;
import limeng32.testSpring.pojo.Article;

public class ArticleCondition extends Article implements Conditionable, Isable {

	private static final long serialVersionUID = 1L;

	private Limitable limiter;

	private Sortable sorter;

	private String title;

	private String isable;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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
