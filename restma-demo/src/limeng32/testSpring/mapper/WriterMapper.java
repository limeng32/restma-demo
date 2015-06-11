package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.Writer;
import limeng32.testSpring.pojo.condition.BookWriterCondition;

public interface WriterMapper extends MapperFace<Writer> {

	public void loadBookWriter(Writer writer,
			BookWriterCondition bookWriterCondition);

}
