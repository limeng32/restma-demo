package limeng32.testSpring.enums.sql;

import limeng32.testSpring.annotation.SqlDialect;
import limeng32.testSpring.pojo.Queryable;

@SqlDialect(SqlDialect.order)
public enum SQLORDER implements Queryable {

	asc("asc"), desc("desc"), sorter("sorter");

	private SQLORDER(String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}

	@Override
	public String tableAndValue() {
		return null;
	}

	@Override
	public String tableAndValueByEscape() {
		return null;
	}
}
