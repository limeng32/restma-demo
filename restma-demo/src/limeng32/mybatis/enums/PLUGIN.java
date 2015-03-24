package limeng32.mybatis.enums;

public enum PLUGIN {
	sqlSuffix("sqlSuffix");

	private PLUGIN(String value) {
		this.value = value;
	}

	private String value;

	public String toString() {
		return value;
	}
}
