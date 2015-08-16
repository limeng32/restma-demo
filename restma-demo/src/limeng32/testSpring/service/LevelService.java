package limeng32.testSpring.service;

import java.util.List;

import limeng32.mybatis.plugin.cache.annotation.CacheAnnotation;
import limeng32.mybatis.plugin.cache.annotation.CacheRoleType;
import limeng32.testSpring.mapper.LevelMapper;
import limeng32.testSpring.pojo.Level;
import limeng32.testSpring.pojo.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService extends ServiceSupport<Level> implements LevelMapper {

	@Autowired
	private LevelMapper mapper;

	@Autowired
	private WriterService writerService;

	@Override
	public Level select(int id) {
		return supportSelect(mapper, id);
	}

	@Override
	public List<Level> selectAll(Level t) {
		return supportSelectAll(mapper, t);
	}

	@Override
	public void insert(Level t) {
		supportInsert(mapper, t);
	}

	@Override
	@CacheAnnotation(MappedClass = { Level.class }, role = CacheRoleType.Trigger)
	public void update(Level t) {
		supportUpdate(mapper, t);
	}

	@Override
	@CacheAnnotation(MappedClass = { Level.class }, role = CacheRoleType.Trigger)
	public void updatePersistent(Level t) {
		supportUpdatePersistent(mapper, t);
	}

	@Override
	public void retrieve(Level t) {
		supportRetrieve(mapper, t);
	}

	@Override
	public void retrieveOnlyNull(Level t) {
		supportRetrieveOnlyNull(mapper, t);
	}

	@Override
	public void delete(Level t) {
		supportDelete(mapper, t);
	}

	@Override
	public int count(Level t) {
		return supportCount(mapper, t);
	}

	@Override
	public void loadWriter(Level level, Writer writer) {
		writer.setLevel(level);
		level.setWriter(writerService.selectAll(writer));
	}

}
