package limeng32.testSpring.enums;

import limeng32.testSpring.annotation.SqlDialect;

@SqlDialect
public enum SQL {
	asc("asc"), desc("desc"), sorter("sorter");

	private SQL(String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
}
