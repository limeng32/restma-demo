package limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import limeng32.testSpring.mapper.MapperFace;
import limeng32.testSpring.pojo.Queryable;

public interface ServiceFace<T> extends MapperFace<T> {

	public List<T> selectAllUseEnum(Map<Queryable, Object> map);
}
