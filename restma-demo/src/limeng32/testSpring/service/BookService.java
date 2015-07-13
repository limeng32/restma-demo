package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.BookMapper;
import limeng32.testSpring.pojo.Book;
import limeng32.testSpring.pojo.condition.BookWriterCondition;

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
	public List<Book> selectAll(Book t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public void loadBookWriter(Book book,
			BookWriterCondition bookWriterCondition) {
		bookWriterCondition.setBook(book);
		book.setBookWriter(bookWriterService.selectAll(bookWriterCondition));
	}

	@Override
	public void updatePersistent(Book t) {
		supportUpdatePersistent(mapper, t);
	}

	@Override
	public void retrieve(Book t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(Book t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(Book t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(Book t) {
		return supportCount(mapper, t);
	}
}