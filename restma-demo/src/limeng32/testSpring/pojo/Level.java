package limeng32.testSpring.pojo;

import java.io.Serializable;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "Level")
public class Level extends PojoSupport<Level> implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private java.lang.String name;

	private java.util.Collection<Writer> writer;

	public java.util.Collection<Writer> getWriter() {
		if (writer == null)
			writer = new java.util.LinkedHashSet<Writer>();
		return writer;
	}

	@JSONField(serialize = false)
	public java.util.Iterator<Writer> getIteratorWriter() {
		if (writer == null)
			writer = new java.util.HashSet<Writer>();
		return writer.iterator();
	}

	public void setWriter(java.util.Collection<Writer> newWriter) {
		removeAllWriter();
		for (java.util.Iterator<Writer> iter = newWriter.iterator(); iter
				.hasNext();)
			addWriter((Writer) iter.next());
	}

	public void addWriter(Writer newWriter) {
		if (newWriter == null)
			return;
		if (this.writer == null)
			this.writer = new java.util.HashSet<Writer>();
		if (!this.writer.contains(newWriter)) {
			if (newWriter.getById(this.writer) != null) {
				removeWriter(newWriter.getById(this.writer));
			}
			this.writer.add(newWriter);
			newWriter.setLevel(this);
		}
	}

	public void removeWriter(Writer oldWriter) {
		if (oldWriter == null)
			return;
		if (this.writer != null)
			if (oldWriter.belongs(this.writer)) {
				oldWriter.quit(this.writer);
				oldWriter.setLevel((Level) null);
			}
	}

	public void removeAllWriter() {
		if (writer != null) {
			Writer oldWriter;
			for (java.util.Iterator<Writer> iter = getIteratorWriter(); iter
					.hasNext();) {
				oldWriter = (Writer) iter.next();
				iter.remove();
				oldWriter.setLevel((Level) null);
			}
		}
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@PersistentFlagAnnotation
	private String _persistent;
}
