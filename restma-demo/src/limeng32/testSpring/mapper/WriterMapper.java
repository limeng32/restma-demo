package limeng32.testSpring.mapper;

import java.util.List;

import limeng32.mybatis.plugin.cache.annotation.CacheAnnotation;
import limeng32.mybatis.plugin.cache.annotation.CacheRoleType;
import limeng32.mybatis.plugin.mapper.able.AbleMapperFace;
import limeng32.testSpring.pojo.Association;
import limeng32.testSpring.pojo.BookWriter;
import limeng32.testSpring.pojo.Level;
import limeng32.testSpring.pojo.Writer;

public interface WriterMapper extends MapperFace<Writer>,
		AbleMapperFace<Writer> {

	@Override
	@CacheAnnotation(MappedClass = { Association.class, Level.class }, role = CacheRoleType.Observer)
	public Writer select(int id);

	@Override
	@CacheAnnotation(MappedClass = { Association.class, Level.class }, role = CacheRoleType.Observer)
	public List<Writer> selectAll(Writer t);

	@Override
	public void insert(Writer t);

	@Override
	public void update(Writer t);

	@Override
	public void updatePersistent(Writer t);

	@Override
	public void retrieve(Writer t);

	@Override
	public void retrieveOnlyNull(Writer t);

	@Override
	public void delete(Writer t);

	@Override
	public int count(Writer t);

	public void loadBookWriter(Writer writer, BookWriter bookWriter);

}
