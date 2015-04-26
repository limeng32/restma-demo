package limeng32.mybatis.plugin;

import java.io.Serializable;
import java.util.List;

public class SqlSuffix implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 结果集中的最大显示数量 */
	private int showCount;

	/* 用来排序的排序器集合 */
	private List<String[]> sorterList;

	/* 结果集中第一条记录在表中所处位置 */
	private int currentResult;

	/* 所有满足查询条件的记录总数，可由此计算出目前所处页数，此数值建议由插件自动获取。 */
	private int totalResult;

	/* 分组 */
	private String groupField;

	public int getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

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

}
