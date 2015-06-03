package limeng32.testSpring.testPojo;

import limeng32.testSpring.pojo.PojoSupport;

import com.alibaba.fastjson.annotation.JSONField;

public class Writer extends PojoSupport<Writer> {
	public int id;
	public java.lang.String name;

	public java.util.Collection<BookWriter> bookWriter;

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
		if (!newBookWriter.belongs(this.bookWriter)) {
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

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

}
