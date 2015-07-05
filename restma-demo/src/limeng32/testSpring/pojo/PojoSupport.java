package limeng32.testSpring.pojo;

import java.util.Collection;

public abstract class PojoSupport<T extends PojoSupport<T>> implements
		PojoFace<T> {

	abstract public Integer getId();

	abstract public void setId(Integer id);

	public void quit(Collection<T> c) {
		for (T i : c) {
			if (this.getId() == i.getId()) {
				c.remove(i);
				return;
			}
		}
	}

	public boolean belongs(Collection<T> c) {
		for (T i : c) {
			if (this.getId() == i.getId()) {
				return true;
			}
		}
		return false;
	}

	public T getById(Collection<T> c) {
		for (T i : c) {
			if (this.getId() == i.getId()) {
				return i;
			}
		}
		return null;
	}
}
