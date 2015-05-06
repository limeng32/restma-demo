package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;
import limeng32.mybatis.plugin.Sortable;

public interface Conditionable {

	public Limitable getLimiter();

	public void setLimiter(Limitable limiter);

	public Sortable getSorter();

	public void setSorter(Sortable sorter);

	public String dot = ".";

	public enum Sequence {
		asc, desc
	}

}
