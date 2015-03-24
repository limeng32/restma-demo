package limeng32.mybatis.plugin;

import java.io.Serializable;

public class SqlSuffix implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 结果集中的最大显示数量 */
	private int showCount;

	/* 用来排序的字段，目前只支持单字段排序 */
	private String sortField;

	/* 顺序，正序或逆序 */
	private String order;

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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
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
}
