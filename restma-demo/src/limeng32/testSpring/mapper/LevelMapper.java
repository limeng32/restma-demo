package limeng32.testSpring.mapper;

import java.util.List;

import limeng32.mybatis.plugin.cache.annotation.CacheAnnotation;
import limeng32.mybatis.plugin.cache.annotation.CacheRoleType;
import limeng32.testSpring.pojo.Level;
import limeng32.testSpring.pojo.Writer;

public interface LevelMapper extends MapperFace<Level> {

	@Override
	public Level select(int id);

	@Override
	public List<Level> selectAll(Level t);

	@Override
	public void insert(Level t);

	@Override
	@CacheAnnotation(MappedClass = { Level.class }, role = CacheRoleType.Trigger)
	public void update(Level t);

	@Override
	@CacheAnnotation(MappedClass = { Level.class }, role = CacheRoleType.Trigger)
	public void updatePersistent(Level t);

	@Override
	public void retrieve(Level t);

	@Override
	public void retrieveOnlyNull(Level t);

	@Override
	public void delete(Level t);

	@Override
	public int count(Level t);

	public void loadWriter(Level level, Writer writer);

}
