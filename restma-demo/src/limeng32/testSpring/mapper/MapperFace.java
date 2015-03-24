package limeng32.testSpring.mapper;

import java.util.List;
import java.util.Map;

public interface MapperFace<T> {

	public T select(int id);

	public List<T> selectAll(Map<String, Object> map);

	public int count(Map<String, Object> map);

	public void insert(T t);

	public void update(T t);
}
