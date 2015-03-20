package cn.limeng32.testSpring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.limeng32.testSpring.pojo.PojoFace;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Domain {
	Class<? extends PojoFace<?>> value();
}
