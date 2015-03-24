package limeng32.testSpring.pojo;

import java.util.Collection;

public interface PojoFace<T> {

	boolean belongs(Collection<T> c);

	void quit(Collection<T> c);
}