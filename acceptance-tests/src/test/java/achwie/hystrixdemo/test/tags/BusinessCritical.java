package achwie.hystrixdemo.test.tags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tngtech.jgiven.annotation.IsTag;

@IsTag(value = "Business critical", description = "Features that are critical for the user to perform orders")
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessCritical {

}
