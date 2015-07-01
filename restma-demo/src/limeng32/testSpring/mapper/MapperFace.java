package limeng32.testSpring.mapper;

import java.util.List;

import limeng32.testSpring.pojo.condition.Conditionable;

public interface MapperFace<T> {

	public T select(int id);

	public List<T> selectAll(Conditionable conditionable);

	public int count(Conditionable conditionable);

	public void insert(T t);

	public void update(T t);

	public void updatePersistent(T t);

	public void retrieve(T t);
}
