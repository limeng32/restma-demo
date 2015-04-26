package limeng32.testSpring.enums.sql;

import limeng32.testSpring.annotation.SQLMeta;
import limeng32.testSpring.pojo.Queryable;

@SQLMeta(SQLMeta.subdata)
public enum SQL implements Queryable {

	@SQLMeta(SQLMeta.order)
	asc("asc"), @SQLMeta(SQLMeta.order)
	desc("desc"), @SQLMeta(SQLMeta.sorter)
	sorter("sorter"), @SQLMeta(SQLMeta.limit)
	limit("sorter");

	private SQL(String value) {
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
