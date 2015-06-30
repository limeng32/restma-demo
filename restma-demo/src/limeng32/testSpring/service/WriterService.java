package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.WriterMapper;
import limeng32.testSpring.pojo.Writer;
import limeng32.testSpring.pojo.condition.BookWriterCondition;
import limeng32.testSpring.pojo.condition.Conditionable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterService extends ServiceSupport<Writer> implements
		WriterMapper {

	@Autowired
	private WriterMapper mapper;

	@Autowired
	private BookWriterService bookWriterService;

	@Override
	public Writer select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public void insert(Writer t) {
		supportInsert(mapper, t);
	}

	@Override
	public void update(Writer t) {
		supportUpdate(mapper, t);
	}

	@Override
	public List<Writer> selectAll(Conditionable conditionable) {
		return mapper.selectAll(conditionable);
	}

	@Override
	public int count(Conditionable conditionable) {
		return mapper.count(conditionable);
	}

	@Override
	public void loadBookWriter(Writer writer,
			BookWriterCondition bookWriterCondition) {
		bookWriterCondition.setWriter(writer);
		writer.setBookWriter(bookWriterService.selectAll(bookWriterCondition));
	}

	@Override
	public void updatePersistent(Writer t) {
		supportUpdatePersistent(mapper, t);
	}
}