package limeng32.testSpring.pojo;

import java.io.Serializable;

import limeng32.mybatis.plugin.mapper.annotation.FieldMapperAnnotation;
import limeng32.mybatis.plugin.mapper.annotation.TableMapperAnnotation;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.annotation.JSONField;

@TableMapperAnnotation(tableName = "BookWriter")
public class BookWriter extends PojoSupport<BookWriter> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JSONField(serialize = false)
	@FieldMapperAnnotation(dbFieldName = "id", jdbcType = JdbcType.INTEGER, isUniqueKey = true)
	private Integer id;

	@FieldMapperAnnotation(dbFieldName = "bookid", jdbcType = JdbcType.INTEGER)
	private Book book;

	@FieldMapperAnnotation(dbFieldName = "writerid", jdbcType = JdbcType.INTEGER)
	private Writer writer;

	public Book getBook() {
		return book;
	}

	public void setBook(Book newBook) {
		if (this.book == null || !this.book.equals(newBook)) {
			if (this.book != null) {
				Book oldBook = this.book;
				this.book = null;
				oldBook.removeBookWriter(this);
			}
			if (newBook != null) {
				this.book = newBook;
				this.book.addBookWriter(this);
			}
		}
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer newWriter) {
		if (this.writer == null || !this.writer.equals(newWriter)) {
			if (this.writer != null) {
				Writer oldWriter = this.writer;
				this.writer = null;
				oldWriter.removeBookWriter(this);
			}
			if (newWriter != null) {
				this.writer = newWriter;
				this.writer.addBookWriter(this);
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

}
