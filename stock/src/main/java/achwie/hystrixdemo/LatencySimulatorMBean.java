package achwie.hystrixdemo;

/**
 * 
 * @author 23.02.2016, Achim Wiedemann
 *
 */
public interface LatencySimulatorMBean {
  public void setLatency(int latencyMillis);

  public int getLatency();
}
