package limeng32.mybatis.plugin.mapper.annotation;

import java.util.List;
import java.util.Map;

/**
 * 描述PojoCondition对象的数据库映射信息
 * 
 * @author limeng32
 * 
 */
public class QueryMapper {

	private List<ConditionMapper> conditionMapperList;

	private Map<String, ConditionMapper> conditionMapperCache;

	public List<ConditionMapper> getConditionMapperList() {
		return conditionMapperList;
	}

	public void setConditionMapperList(List<ConditionMapper> conditionMapperList) {
		this.conditionMapperList = conditionMapperList;
	}

	public Map<String, ConditionMapper> getConditionMapperCache() {
		return conditionMapperCache;
	}

	public void setConditionMapperCache(
			Map<String, ConditionMapper> conditionMapperCache) {
		this.conditionMapperCache = conditionMapperCache;
	}

}
