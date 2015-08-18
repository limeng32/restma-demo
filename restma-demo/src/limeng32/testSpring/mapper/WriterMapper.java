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
	@CacheAnnotation(MappedClass = { Writer.class }, role = CacheRoleType.Trigger)
	public void update(Writer t);

	@Override
	@CacheAnnotation(MappedClass = { Writer.class }, role = CacheRoleType.Trigger)
	public void updatePersistent(Writer t);

	@Override
	public void retrieve(Writer t);

	@Override
	public void retrieveOnlyNull(Writer t);

	@Override
	@CacheAnnotation(MappedClass = { Writer.class }, role = CacheRoleType.Trigger)
	public void delete(Writer t);

	@Override
	@CacheAnnotation(MappedClass = { Association.class, Level.class }, role = CacheRoleType.Observer)
	public int count(Writer t);

	@Override
	@CacheAnnotation(MappedClass = { Writer.class }, role = CacheRoleType.Trigger)
	public void disable(Writer t);

	@Override
	@CacheAnnotation(MappedClass = { Writer.class }, role = CacheRoleType.Trigger)
	public void enable(Writer t);

	public void loadBookWriter(Writer writer, BookWriter bookWriter);

}
