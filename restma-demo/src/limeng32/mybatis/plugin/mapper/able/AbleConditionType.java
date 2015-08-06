package limeng32.mybatis.plugin.mapper.able;

public enum AbleConditionType {
	Able(AbleMapperFace.ableToken), Unable(AbleMapperFace.unableToken), Ignore(
			AbleMapperFace.ignoreToken);
	private final int value;

	private AbleConditionType(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + "";
	}

	public int value() {
		return value;
	}
}
