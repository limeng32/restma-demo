package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.WriterMapper;
import limeng32.testSpring.pojo.Writer;
import limeng32.testSpring.pojo.condition.BookWriterCondition;

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
	public List<Writer> selectAll(Writer t) {
		return supportSelectAll(mapper, t);
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

	@Override
	public void retrieve(Writer t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(Writer t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(Writer t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(Writer t) {
		return supportCount(mapper, t);
	}
}