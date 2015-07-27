package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.mapper.annotation.ConditionMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.ConditionType;
import limeng32.mybatis.plugin.mapper.annotation.QueryMapperAnnotation;
import limeng32.testSpring.pojo.Level;

@QueryMapperAnnotation(tableName = "Level")
public class LevelCondition extends Level {

	private static final long serialVersionUID = 1L;

	@ConditionMapperAnnotation(dbFieldName = "name", conditionType = ConditionType.Like)
	private String nameLike;

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

}
