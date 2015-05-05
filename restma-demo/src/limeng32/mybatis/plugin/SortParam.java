package limeng32.mybatis.plugin;

import java.util.LinkedList;
import java.util.List;

public class SortParam implements Sortable {

	public SortParam(Order... orders) {
		list = new LinkedList<>();
		for (Order order : orders) {
			list.add(order);
		}
	}

	List<Order> list;

	@Override
	public void addOrder() {

	}

	@Override
	public String toString() {
		return list.size() + "";
	}

}
