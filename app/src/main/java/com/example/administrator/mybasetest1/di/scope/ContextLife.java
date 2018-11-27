package com.example.administrator.mybasetest1.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

/**
 * Created by Administrator on 2018/11/16.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default "";
}
