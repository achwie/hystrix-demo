package achwie.hystrixdemo;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import achwie.hystrixdemo.util.latency.LatencySimulator;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
@SpringBootApplication
public class StockStarter {
  public static void main(String[] args) throws Exception {
    // Register MBean
    final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    final ObjectName name = ObjectName.getInstance(LatencySimulator.OBJECT_NAME);
    final LatencySimulator mbean = new LatencySimulator();
    mbs.registerMBean(mbean, name);

    SpringApplication.run(StockStarter.class, args);
  }
}
