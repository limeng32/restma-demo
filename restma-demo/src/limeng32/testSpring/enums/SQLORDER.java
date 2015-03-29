package limeng32.testSpring.enums;

import limeng32.testSpring.annotation.SqlDialect;

@SqlDialect(SqlDialect.order)
public enum SQLORDER implements PojoEnum {
	asc("asc"), desc("desc");

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
