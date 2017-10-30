package us.codecraft.webmagic.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Define the Flag for field.<br>
 * 
 * @author longchensan
 *
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Flag {

    /**
     * is save this field to Object
     * 
     * @return boolean
     */
    boolean isSave() default true;

    /**
     * is putextra to request
     * 
     * @return boolean
     */
    boolean isTransmit() default false;

    /**
     * is addtagrequest to page
     * 
     * @return boolean
     */
    boolean isAddTagUrl() default false;
}
