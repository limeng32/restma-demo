package limeng32.testSpring.enums;

import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.pojo.User;

@Domain(User.class)
public enum USER implements POJOFace {
	tableName("user"), id("id"), name("name"), address("address"), sex("sex");

	private USER(String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}

	public String tableAndValue() {
		return tableName.value + dot + value;
	}

	public String tableAndValueByEscape() {
		return tableName.value + "__" + value;
	}
}
