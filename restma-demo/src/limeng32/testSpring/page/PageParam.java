package limeng32.testSpring.page;

public class PageParam {

	public PageParam() {

	}

	public PageParam(int _pageNo, int _pageSize) {
		this.pageNo = _pageNo;
		this.pageSize = _pageSize;
	}

	private int pageNo;

	private int pageSize;

	private int maxPageNum;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPageNum() {
		return maxPageNum;
	}

	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}

}
