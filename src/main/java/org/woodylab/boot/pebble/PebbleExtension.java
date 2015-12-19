package org.woodylab.boot.pebble;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-17 上午1:41.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface PebbleExtension {
}
