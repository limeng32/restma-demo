package limeng32.testSpring.mapper;

import limeng32.mybatis.plugin.mapper.able.AbleMapperFace;
import limeng32.testSpring.pojo.Book;
import limeng32.testSpring.pojo.BookWriter;

public interface BookMapper extends MapperFace<Book>, AbleMapperFace<Book> {

	public void loadBookWriter(Book book, BookWriter bookWriter);

}
