package cn.limeng32.testSpring.enums;

import cn.limeng32.testSpring.pojo.Article;

public enum ARTICLE implements PojoEnum<Article> {
	tableName("article"), id("id"), userid("user"), title("title"), content(
			"content");

	private final String value;

	private ARTICLE(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public String tableAndValue() {
		return tableName.value + "__" + value;
	}
}
