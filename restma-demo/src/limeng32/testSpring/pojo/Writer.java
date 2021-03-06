package limeng32.testSpring.pojo;

import java.io.Serializable;

import limeng32.mybatis.plugin.mapper.able.AbleConditionFlagAnnotation;
import limeng32.mybatis.plugin.mapper.able.AbleConditionType;
import limeng32.mybatis.plugin.mapper.able.AbleFlagAnnotation;
import limeng32.mybatis.plugin.mapper.able.PojoAble;
import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.PersistentFlagAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "Writer")
public class Writer extends PojoSupport<Writer> implements Serializable,
		PojoAble {
	private static final long serialVersionUID = 1L;
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;
	@FieldMapperAnnotation(dbFieldName = "name", jdbcType = JdbcType.VARCHAR)
	private java.lang.String name;

	private java.util.Collection<BookWriter> bookWriter;

	@FieldMapperAnnotation(dbFieldName = "assoId", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Association association;

	@FieldMapperAnnotation(dbFieldName = "levelId", jdbcType = JdbcType.INTEGER, dbAssociationUniqueKey = "id")
	private Level level;

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

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level newLevel) {
		if (this.level == null || !this.level.equals(newLevel)) {
			if (this.level != null) {
				Level oldLevel = this.level;
				this.level = null;
				oldLevel.removeWriter(this);
			}
			if (newLevel != null) {
				this.level = newLevel;
				this.level.addWriter(this);
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

	@Override
	public boolean isable() {
		return isable;
	}

	@AbleFlagAnnotation
	private boolean isable;

	@Override
	public void setAbleCondition(AbleConditionType ableCondition) {
		this.ableCondition = ableCondition;
	}

	@JSONField(serialize = false)
	@Override
	public AbleConditionType getAbleCondition() {
		return ableCondition;
	}

	@AbleConditionFlagAnnotation
	private AbleConditionType ableCondition;

}
