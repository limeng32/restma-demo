package limeng32.testSpring.pojo;

import java.util.Collection;

public abstract class PojoSupport<T extends PojoSupport<T>> implements
		PojoFace<T> {

	abstract public int getId();

	abstract public void setId(int id);

	public void quit(Collection<T> c) {
		for (T i : c) {
			if (this.getId() == i.getId()) {
				c.remove(i);
				break;
			}
		}
	}

	public boolean belongs(Collection<T> c) {
		boolean ret = false;
		for (T i : c) {
			if (this.getId() == i.getId()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public T getById(Collection<T> c) {
		T ret = null;
		for (T i : c) {
			if (this.getId() == i.getId()) {
				ret = i;
				break;
			}
		}
		return ret;
	}
}
