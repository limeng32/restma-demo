package limeng32.testSpring.pojo;

import java.io.Serializable;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "Writer")
public class Writer extends PojoSupport<Writer> implements Serializable {
	private static final long serialVersionUID = 1L;
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;
	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private java.lang.String name;

	private java.util.Collection<BookWriter> bookWriter;

	@FieldMapperAnnotation(dbFieldName = "assoId", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Association association;

	public java.util.Collection<BookWriter> getBookWriter() {
		if (bookWriter == null)
			bookWriter = new java.util.LinkedHashSet<BookWriter>();
		return bookWriter;
	}

	@JSONField(serialize = false)
	public java.util.Iterator<BookWriter> getIteratorBookWriter() {
		if (bookWriter == null)
			bookWriter = new java.util.LinkedHashSet<BookWriter>();
		return bookWriter.iterator();
	}

	public void setBookWriter(java.util.Collection<BookWriter> newBookWriter) {
		removeAllBookWriter();
		for (java.util.Iterator<BookWriter> iter = newBookWriter.iterator(); iter
				.hasNext();)
			addBookWriter((BookWriter) iter.next());
	}

	public void addBookWriter(BookWriter newBookWriter) {
		if (newBookWriter == null)
			return;
		if (this.bookWriter == null)
			this.bookWriter = new java.util.LinkedHashSet<BookWriter>();
		if (!this.bookWriter.contains(newBookWriter)) {
			if (newBookWriter.getById(this.bookWriter) != null) {
				removeBookWriter(newBookWriter.getById(this.bookWriter));
			}
			this.bookWriter.add(newBookWriter);
			newBookWriter.setWriter(this);
		}
	}

	public void removeBookWriter(BookWriter oldBookWriter) {
		if (oldBookWriter == null)
			return;
		if (this.bookWriter != null)
			if (oldBookWriter.belongs(this.bookWriter)) {
				oldBookWriter.quit(this.bookWriter);
				oldBookWriter.setWriter((Writer) null);
			}
	}

	public void removeAllBookWriter() {
		if (bookWriter != null) {
			BookWriter oldBookWriter;
			for (java.util.Iterator<BookWriter> iter = getIteratorBookWriter(); iter
					.hasNext();) {
				oldBookWriter = (BookWriter) iter.next();
				iter.remove();
				oldBookWriter.setWriter((Writer) null);
			}
		}
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association newAssociation) {
		if (this.association == null
				|| !this.association.equals(newAssociation)) {
			if (this.association != null) {
				Association oldAssociation = this.association;
				this.association = null;
				oldAssociation.removeWriter(this);
			}
			if (newAssociation != null) {
				this.association = newAssociation;
				this.association.addWriter(this);
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
