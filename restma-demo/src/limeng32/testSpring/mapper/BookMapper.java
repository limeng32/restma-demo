package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.Book;
import limeng32.testSpring.pojo.condition.BookWriterCondition;

public interface BookMapper extends MapperFace<Book> {

	public void loadBookWriter(Book book,
			BookWriterCondition bookWriterCondition);

}
