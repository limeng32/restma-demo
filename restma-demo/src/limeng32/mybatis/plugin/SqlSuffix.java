package limeng32.mybatis.plugin;

import java.io.Serializable;
import java.util.List;

public class SqlSuffix implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String KEY = "__sqlSuffix";

	/* 用来排序的排序器集合 */
	private List<String[]> sorterList;

	private Limitable limiter;

	/* 分组 */
	private String groupField;

	public String getGroupField() {
		return groupField;
	}

	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}

	public List<String[]> getSorterList() {
		return sorterList;
	}

	public void setSorterList(List<String[]> sorterList) {
		this.sorterList = sorterList;
	}

	public Limitable getLimiter() {
		return limiter;
	}

	public void setLimiter(Limitable limiter) {
		this.limiter = limiter;
	}

}
