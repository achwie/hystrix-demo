package achwie.hystrixdemo.util.latency;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This MBean implements a latency service to introduce artificial latency into
 * a service.
 * 
 * @author 23.02.2016, Achim Wiedemann
 *
 */
public class LatencySimulator implements LatencySimulatorMBean {
  public static final String OBJECT_NAME = "achwie.hystrixdemo:type=LatencySimulator";
  private volatile int latencyMillis = 0;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLatencyMillis(int latencyMillis) {
    if (latencyMillis >= 0) {
      this.latencyMillis = latencyMillis;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getLatencyMillis() {
    return latencyMillis;
  }

  /**
   * Lets the current thread sleep for the amount of milliseconds specified via
   * {@link #setLatencyMillis(int)}.
   */
  public static void beLatent() {
    try {
      // For sure this could be done more clever with Spring
      final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      final ObjectName name = ObjectName.getInstance(LatencySimulator.OBJECT_NAME);
      final Integer latencyMillis = (Integer) mbs.getAttribute(name, "LatencyMillis");

      if (latencyMillis > 0) {
        try {
          Thread.sleep(latencyMillis);
        } catch (InterruptedException e) {
          Thread.interrupted(); // Reset flag
        }
      }
    } catch (Exception e) {
      System.err.println("ERROR: Could not be latent for a bit. Reason: " + e.getMessage());
    }
  }
}
