package achwie.hystrixdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 *
 */
public abstract class HystrixRestCommand<T> extends HystrixCommand<T> {
  protected final Logger LOG = LoggerFactory.getLogger(getClass());
  protected final RestOperations restOps;

  protected HystrixRestCommand(HystrixCommandGroupKey group, RestOperations restOps) {
    super(group);
    this.restOps = restOps;
  }
}
