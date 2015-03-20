package cn.limeng32.testSpring.enums;

import cn.limeng32.testSpring.annotation.Domain;

@Domain(cn.limeng32.testSpring.pojo.Publisher.class)
public enum PUBLISHER implements PojoEnum {

	tableName("publish"), id("id"), userid("userid") {
		@Override
		public String pojo() {
			return "user";
		}
	},
	name("name");

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
		return tableName.value + "__" + value;
	}
}
