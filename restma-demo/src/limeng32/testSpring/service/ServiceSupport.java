package limeng32.testSpring.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import limeng32.mybatis.plugin.ReflectHelper;
import limeng32.testSpring.mapper.MapperFace;
import limeng32.testSpring.pojo.PojoSupport;
import limeng32.testSpring.pojo.condition.Conditionable;

public abstract class ServiceSupport<T extends PojoSupport<T>> implements
		ServiceFace<T> {

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

	protected void supportRetrieve(MapperFace<T> mapper, T t) {
		try {
			ReflectHelper.copyBean(t, mapper.select(t.getId()));
		} catch (SecurityException | NoSuchFieldException
				| IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected void supportRetrieveOnlyNull(MapperFace<T> mapper, T t) {
		try {
			ReflectHelper.copyBeanByNullField(t, mapper.select(t.getId()));
		} catch (SecurityException | NoSuchFieldException
				| IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
