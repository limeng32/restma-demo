package cn.limeng32.mybatis.plugin;

import java.io.Serializable;

public class SqlSuffix implements Serializable {

	private static final long serialVersionUID = 1L;

	/* ������е������ʾ���� */
	private int showCount;

	/* ����������ֶΣ�Ŀǰֻ֧�ֵ��ֶ����� */
	private String sortField;

	/* ˳����������� */
	private String order;

	/* ������е�һ����¼�ڱ�������λ�� */
	private int currentResult;

	/* ���������ѯ�����ļ�¼���������ɴ˼����Ŀǰ����ҳ��������ֵ�����ɲ���Զ���ȡ�� */
	private int totalResult;

	/* ���� */
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
