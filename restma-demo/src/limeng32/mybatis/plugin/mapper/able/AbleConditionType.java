package limeng32.mybatis.plugin.mapper.able;

public enum AbleConditionType {
	Able("1"), Unable("0"), Igore("-1");
	private final String value;

	private AbleConditionType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String value() {
		return value;
	}
}
