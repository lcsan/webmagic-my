package us.codecraft.webmagic.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Define the Leaf for class.<br>
 * 
 * @author longchensan
 *
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Leaf {

    /**
     * is children Model
     * 
     * @return boolean
     */
    boolean isLeaf() default true;
}
