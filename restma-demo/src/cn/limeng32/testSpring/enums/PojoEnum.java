package cn.limeng32.testSpring.enums;

public interface PojoEnum<T> {

	String isable = "isable";

	String isableYes = "1";

	String isableNo = "0";

	String isableIgnore = "-1";

	public String value();

	public String tableAndValue();

}
