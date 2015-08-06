package limeng32.mybatis.plugin.mapper.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * 描述java对象的数据库映射信息（数据库表的映射、字段的映射）
 * 
 * @author david
 * 
 */
public class TableMapper {

	private Annotation tableMapperAnnotation;

	private Map<String, Mapperable> fieldMapperCache;

	private String[] uniqueKeyNames;

	private Collection<String> persistentFlags;

	private String ableFlag;

	public Annotation getTableMapperAnnotation() {
		return tableMapperAnnotation;
	}

	public void setTableMapperAnnotation(Annotation tableMapperAnnotation) {
		this.tableMapperAnnotation = tableMapperAnnotation;
	}

	public Map<String, Mapperable> getFieldMapperCache() {
		return fieldMapperCache;
	}

	public void setFieldMapperCache(Map<String, Mapperable> fieldMapperCache) {
		this.fieldMapperCache = fieldMapperCache;
	}

	public String[] getUniqueKeyNames() {
		return uniqueKeyNames;
	}

	public void setUniqueKeyNames(String[] uniqueKeyNames) {
		this.uniqueKeyNames = uniqueKeyNames;
	}

	public Collection<String> getPersistentFlags() {
		if (persistentFlags == null)
			persistentFlags = new HashSet<>();
		return persistentFlags;
	}

	public void setPersistentFlags(Collection<String> persistentFlags) {
		this.persistentFlags = persistentFlags;
	}

	public String getAbleFlag() {
		return ableFlag;
	}

	public void setAbleFlag(String ableFlag) {
		this.ableFlag = ableFlag;
	}

}
