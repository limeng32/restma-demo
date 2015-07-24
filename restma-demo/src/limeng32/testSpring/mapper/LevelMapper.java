package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.Level;
import limeng32.testSpring.pojo.Writer;

public interface LevelMapper extends MapperFace<Level> {

	public void loadWriter(Level level, Writer writer);

}
