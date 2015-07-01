package limeng32.testSpring.pojo;

import java.io.Serializable;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "Book")
public class Book extends PojoSupport<Book> implements Serializable {
	private static final long serialVersionUID = 1L;
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private int id;
	@FieldMapperAnnotation(dbFieldName = "title", jdbcType = JdbcType.VARCHAR)
	private java.lang.String title;

	private java.util.Collection<BookWriter> bookWriter;

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
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

}
