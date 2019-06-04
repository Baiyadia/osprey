package com.kaiqi.osprey.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TestValidate {

    enum Sex {Man, Woman}

    Sex sex() default Sex.Man;

    String desc() default "";
}
