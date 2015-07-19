package limeng32.mybatis.plugin.mapper.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 描述java对象的数据库映射信息（数据库表的映射、字段的映射）
 * 
 * @author david
 * 
 */
public class TableMapper {

	private Annotation tableMapperAnnotation;

	private Map<String, FieldMapper> fieldMapperCache;

	/* 基于FieldName的新cache */
	private Map<String, FieldMapper> newFieldMapperCache;

	private List<FieldMapper> fieldMapperList;

	private Collection<String> persistentFlags;

	public List<FieldMapper> getFieldMapperList() {
		return fieldMapperList;
	}

	public void setFieldMapperList(List<FieldMapper> fieldMapperList) {
		this.fieldMapperList = fieldMapperList;
	}

	public Annotation getTableMapperAnnotation() {
		return tableMapperAnnotation;
	}

	public void setTableMapperAnnotation(Annotation tableMapperAnnotation) {
		this.tableMapperAnnotation = tableMapperAnnotation;
	}

	public Map<String, FieldMapper> getFieldMapperCache() {
		return fieldMapperCache;
	}

	public void setFieldMapperCache(Map<String, FieldMapper> fieldMapperCache) {
		this.fieldMapperCache = fieldMapperCache;
	}

	public Collection<String> getPersistentFlags() {
		if (persistentFlags == null)
			persistentFlags = new HashSet<>();
		return persistentFlags;
	}

	public void setPersistentFlags(Collection<String> persistentFlags) {
		this.persistentFlags = persistentFlags;
	}

	public Map<String, FieldMapper> getNewFieldMapperCache() {
		return newFieldMapperCache;
	}

	public void setNewFieldMapperCache(
			Map<String, FieldMapper> newFieldMapperCache) {
		this.newFieldMapperCache = newFieldMapperCache;
	}

}
