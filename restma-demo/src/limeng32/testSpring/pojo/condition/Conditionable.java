package limeng32.testSpring.pojo.condition;

import limeng32.mybatis.plugin.Limitable;

public interface Conditionable {

	public Limitable getLimiter();

	public void setLimiter(Limitable limiter);

}
