package greeleysmtpserver.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author michaelmaitland
 */

public class RelayQueue {

    private ExecutorService executor;
    
    public RelayQueue(){
      this.executor = Executors.newFixedThreadPool(50);
    }
    
    public void add(Session session){
        this.executor.submit(new Relay(session));
    }
}
