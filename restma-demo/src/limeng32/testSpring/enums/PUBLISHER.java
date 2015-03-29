package limeng32.testSpring.enums;

import limeng32.testSpring.annotation.Domain;
import limeng32.testSpring.pojo.Publisher;

@Domain(Publisher.class)
public enum PUBLISHER implements PojoEnum {

	tableName("publisher"), id("id"), userid("userid"), name("name");

	private PUBLISHER(String value) {
		this.value = value;
	}

	private final String value;

	public String pojo() {
		return null;
	}

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
