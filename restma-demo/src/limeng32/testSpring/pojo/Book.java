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

@TableMapperAnnotation(tableName = "Book")
public class Book extends PojoSupport<Book> implements Serializable, PojoAble {
	private static final long serialVersionUID = 1L;
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;
	@FieldMapperAnnotation(dbFieldName = "title", jdbcType = JdbcType.VARCHAR)
	private java.lang.String title;

	private java.util.Collection<BookWriter> bookWriter;

	@FieldMapperAnnotation(dbFieldName = "alias", jdbcType = JdbcType.VARCHAR)
	private String origin;

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
			newBookWriter.setBook(this);
		}
	}

	public void removeBookWriter(BookWriter oldBookWriter) {
		if (oldBookWriter == null)
			return;
		if (this.bookWriter != null)
			if (oldBookWriter.belongs(this.bookWriter)) {
				oldBookWriter.quit(this.bookWriter);
				oldBookWriter.setBook((Book) null);
			}
	}

	public void removeAllBookWriter() {
		if (bookWriter != null) {
			BookWriter oldBookWriter;
			for (java.util.Iterator<BookWriter> iter = getIteratorBookWriter(); iter
					.hasNext();) {
				oldBookWriter = (BookWriter) iter.next();
				iter.remove();
				oldBookWriter.setBook((Book) null);
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

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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
