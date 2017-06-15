package achwie.hystrixdemo.util.latency;

/**
 * This MBean interface can hold a latency value which can be used to introduce
 * artificial latency into a service.
 * 
 * @author 23.02.2016, Achim Wiedemann
 *
 */
public interface LatencySimulatorMBean {

  /**
   * Sets the latency in milliseconds.
   * 
   * @param latencyMillis The latency in milliseconds (negative values will be
   *          ignored).
   */
  public void setLatencyMillis(int latencyMillis);

  /**
   * Returns the latency value in milliseconds.
   * 
   * @return The latency value in milliseconds (always returns a value &gt;= 0).
   */
  public int getLatencyMillis();
}
