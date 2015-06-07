package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.BookWriterMapper;
import limeng32.testSpring.pojo.BookWriter;
import limeng32.testSpring.pojo.condition.Conditionable;

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
	public List<BookWriter> selectAll(Conditionable conditionable) {
		return mapper.selectAll(conditionable);
	}

	@Override
	public int count(Conditionable conditionable) {
		return mapper.count(conditionable);
	}
}