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
	public String toSql() {
		StringBuffer ret = new StringBuffer();
		if (list != null) {
			ret.append(" order by");
			for (Order order : list) {
				ret.append(order.toSql());
			}
			if (ret.lastIndexOf(",") == ret.length() - 1) {
				ret.deleteCharAt(ret.length() - 1);
			}
		}
		return ret.toString();
	}

}
