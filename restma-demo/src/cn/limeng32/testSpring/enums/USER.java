package cn.limeng32.testSpring.enums;

import cn.limeng32.testSpring.pojo.User;

public enum USER implements PojoEnum<User> {
	tableName("user"), id("id"), name("name"), address("address"), sex("sex");

	private USER(String value) {
		this.value = value;
	}

	private final String value;

	public String value() {
		return value;
	}

	public String tableAndValue() {
		return tableName.value + "__" + value;
	}
}
