package achwie.hystrixdemo.loadgen.agent;

/**
 * 
 * @author 22.02.2016, Achim Wiedemann
 */
public interface Agent {
  public void run(CallContext context) throws Exception;
}