package limeng32.mybatis.plugin.mapper.able;

public interface AbleMapperFace<T> {

	int ableToken = 1;

	int ignoreToken = -1;

	int unableToken = 0;

	public void disable(T t);

	public void enable(T t);

}
