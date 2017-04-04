package achwie.hystrixdemo.test.tags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tngtech.jgiven.annotation.IsTag;

@IsTag(value = "Critical", description = "A feature that is critical for the user")
@Retention(RetentionPolicy.RUNTIME)
public @interface Critical {

}
