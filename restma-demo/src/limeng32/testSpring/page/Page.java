package limeng32.testSpring.page;

import java.util.Collection;

import limeng32.mybatis.plugin.Limitable;

public class Page<T> {

	private int pageNo;

	private int maxPageNum;

	private Collection<T> pageItems;

	public Page(Collection<T> items, Limitable limitable) {
		pageItems = items;
		pageNo = limitable.getPageNo();
		maxPageNum = limitable.getMaxPageNum();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getMaxPageNum() {
		return maxPageNum;
	}

	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}

	public Collection<T> getPageItems() {
		return pageItems;
	}

	public void setPageItems(Collection<T> pageItems) {
		this.pageItems = pageItems;
	}

}