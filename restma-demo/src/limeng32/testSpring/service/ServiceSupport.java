package limeng32.testSpring.service;

import java.util.List;

import limeng32.testSpring.mapper.MapperFace;
import limeng32.testSpring.pojo.condition.Conditionable;

public abstract class ServiceSupport<T> implements ServiceFace<T> {

	protected T supportSelect(MapperFace<T> mapper, int id) {
		return mapper.select(id);
	}

	protected int supportCount(MapperFace<T> mapper, Conditionable conditionable) {
		return mapper.count(conditionable);
	}

	protected List<T> supportSelectAll(MapperFace<T> mapper,
			Conditionable conditionable) {
		return mapper.selectAll(conditionable);
	}

	protected void supportInsert(MapperFace<T> mapper, T t) {
		mapper.insert(t);
	}

	protected void supportUpdate(MapperFace<T> mapper, T t) {
		mapper.update(t);
	}

	protected void supportUpdatePersistent(MapperFace<T> mapper, T t) {
		mapper.updatePersistent(t);
	}
}
