package limeng32.testSpring.service;

import java.util.List;
import java.util.Map;

import limeng32.testSpring.enums.PojoEnum;
import limeng32.testSpring.mapper.MapperFace;

public interface ServiceFace<T> extends MapperFace<T> {

	public List<T> selectAllUseEnum(Map<PojoEnum, Object> map);
}
