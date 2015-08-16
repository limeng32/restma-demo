package limeng32.mybatis.plugin.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述mapper方法之间的对应关系的注解
 * 
 * @author limeng32
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface CacheAnnotation {
	/**
	 * 
	 * 目标方法在缓存中对应的角色，分为trigger和observer。
	 */
	CacheRoleType role();

	/**
	 * 
	 * 目标方法对应的pojo类的数组，如果是trigger取第一个类，如果是observer取所有类。
	 */
	Class<?>[] MappedClass();

}
