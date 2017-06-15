package achwie.hystrixdemo.util.latency;

/**
 * 
 * @author 23.02.2016, Achim Wiedemann
 *
 */
public interface LatencySimulatorMBean {
  public void setLatencyMillis(int latencyMillis);

  public int getLatencyMillis();
}
