package cn.limeng32.testSpring.enums;

import cn.limeng32.testSpring.annotation.Domain;
import cn.limeng32.testSpring.pojo.User;

@Domain(User.class)
public enum USER implements PojoEnum {
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
