package ny.gelato.extessera.injection;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * Created by jord.goldberg on 2/27/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ForView {
}