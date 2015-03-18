package cn.limeng32.testSpring.enums;

public enum GENDER {
	male("1", "��"), female("0", "Ů");

	private GENDER(String value, String label) {
		this.label = label;
		this.value = value;
	}

	private final String value;

	private final String label;

	public String getValue() {
		return value;
	}

	public String getName() {
		return name();
	}

	public String getLabel() {
		return label;
	}
}
