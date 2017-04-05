package achwie.hystrixdemo.test.tags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tngtech.jgiven.annotation.IsTag;

@IsTag(value = "Payment related", description = "Features that involve the payment gateway")
@Retention(RetentionPolicy.RUNTIME)
public @interface Payment {

}
