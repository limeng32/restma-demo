package limeng32.mybatis.plugin.mapper.able;

public interface PojoAble {

	String isable = "isable";

	String isableYes = "1";

	String isableNo = "0";

	String isableIgnore = "-1";

	public String isable();

	public void setAbleCondition(AbleConditionType ableCondition);

	public AbleConditionType getAbleCondition();

}
