package limeng32.testSpring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLMeta {

	public static final int subdata = -1;

	public static final int order = 0;

	public static final int sorter = 1;

	public static final int limit = 2;

	int value();
}
