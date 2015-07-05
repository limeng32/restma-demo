package limeng32.testSpring.pojo;

import java.util.Collection;

public interface PojoFace<T> {

	/* 如果自身属于c则返回true，否则返回false */
	boolean belongs(Collection<T> c);

	/* 去掉c中和自身id相同的元素 */
	void quit(Collection<T> c);

	/* 从c中获取和自身id相同的对象并返回 */
	T getById(Collection<T> c);
}