package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.BookMapper;
import limeng32.testSpring.pojo.Book;
import limeng32.testSpring.pojo.condition.BookWriterCondition;
import limeng32.testSpring.pojo.condition.Conditionable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService extends ServiceSupport<Book> implements BookMapper {

	@Autowired
	private BookMapper mapper;

	@Autowired
	private BookWriterService bookWriterService;

	@Override
	public Book select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public void insert(Book t) {
		supportInsert(mapper, t);
	}

	@Override
	public void update(Book t) {
		supportUpdate(mapper, t);
	}

	@Override
	public List<Book> selectAll(Conditionable conditionable) {
		return mapper.selectAll(conditionable);
	}

	@Override
	public int count(Conditionable conditionable) {
		return mapper.count(conditionable);
	}

	@Override
	public void loadBookWriter(Book book,
			BookWriterCondition bookWriterCondition) {
		bookWriterCondition.setBook(book);
		book.setBookWriter(bookWriterService.selectAll(bookWriterCondition));
	}
}