package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.BookWriterMapper;
import limeng32.testSpring.pojo.BookWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookWriterService extends ServiceSupport<BookWriter> implements
		BookWriterMapper {

	@Autowired
	private BookWriterMapper mapper;

	@Override
	public BookWriter select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public void insert(BookWriter t) {
		supportInsert(mapper, t);
	}

	@Override
	public void update(BookWriter t) {
		supportUpdate(mapper, t);
	}

	@Override
	public List<BookWriter> selectAll(BookWriter t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public void updatePersistent(BookWriter t) {
		supportUpdatePersistent(mapper, t);
	}

	@Override
	public void retrieve(BookWriter t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(BookWriter t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(BookWriter t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(BookWriter t) {
		return supportCount(mapper, t);
	}
}