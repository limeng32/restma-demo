package limeng32.testSpring.page;

import java.util.List;

import limeng32.testSpring.pojo.PojoFace;

public class Page<T extends PojoFace<T>> {

	private int pageNo;

	private int totalPageNum;

	private List<T> pageItems;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public List<T> getPageItems() {
		return pageItems;
	}

	public void setPageItems(List<T> pageItems) {
		this.pageItems = pageItems;
	}

}