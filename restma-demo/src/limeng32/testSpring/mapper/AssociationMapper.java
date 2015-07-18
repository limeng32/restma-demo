package limeng32.testSpring.mapper;

import limeng32.testSpring.pojo.Association;
import limeng32.testSpring.pojo.Writer;

public interface AssociationMapper extends MapperFace<Association> {

	public void loadWriter(Association association, Writer writer);

}
