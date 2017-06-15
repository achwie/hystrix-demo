package achwie.hystrixdemo.util.latency;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 * @author 15.06.2017, Achim Wiedemann
 *
 */
public class LatencySimulatorTest {
  private final LatencySimulator latencySimulator = new LatencySimulator();

  @Test
  public void setLatency_whenGivenPositiveLatency_shouldSetValue() {
    latencySimulator.setLatencyMillis(42);

    assertEquals(42, latencySimulator.getLatencyMillis());
  }

  @Test
  public void setLatency_whenGivenZeroLatency_shouldSetValue() {
    latencySimulator.setLatencyMillis(42);
    latencySimulator.setLatencyMillis(0);

    assertEquals(0, latencySimulator.getLatencyMillis());
  }

  @Test
  public void setLatency_whenGivenNegativeLatency_shouldNotSetValue() {
    latencySimulator.setLatencyMillis(-21);

    assertEquals(0, latencySimulator.getLatencyMillis());
  }
}
