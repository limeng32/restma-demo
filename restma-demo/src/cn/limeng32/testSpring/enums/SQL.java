package cn.limeng32.testSpring.enums;

public enum SQL {
	asc("asc"), desc("desc");

	private SQL(String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}
}
