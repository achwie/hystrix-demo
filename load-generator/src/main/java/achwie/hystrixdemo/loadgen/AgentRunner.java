package achwie.hystrixdemo.loadgen;

import achwie.hystrixdemo.loadgen.agent.Agent;
import achwie.hystrixdemo.loadgen.agent.CallContext;

/**
 * 
 * @author 22.02.2016, Achim Wiedemann
 */
class AgentRunner implements Runnable {
  private final Agent agent;
  private final CallContext context;

  public AgentRunner(Agent agent, CallContext context) {
    this.agent = agent;
    this.context = context;
  }

  @Override
  public void run() {
    while (true) {
      try {
        agent.run(context);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          Thread.sleep(5000); // Whoops! Give time to recover...
        } catch (InterruptedException ie) {
          Thread.interrupted(); // Reset interrupted flag
        }
      }
    }
  }
}