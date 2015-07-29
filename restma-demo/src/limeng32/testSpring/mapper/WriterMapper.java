package limeng32.testSpring.mapper;

import limeng32.mybatis.plugin.mapper.able.AbleMapperFace;
import limeng32.testSpring.pojo.BookWriter;
import limeng32.testSpring.pojo.Writer;

public interface WriterMapper extends MapperFace<Writer>,
		AbleMapperFace<Writer> {

	public void loadBookWriter(Writer writer, BookWriter bookWriter);

}
