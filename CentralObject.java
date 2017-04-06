package greeleysmtpserver;

/**
 * @author michaelmaitland
 */
import greeleysmtpserver.server.MultiThreadedServer;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CentralObject {

   public static void main(String[] args) throws UnknownHostException{
       /*Create and run server so it is listening for connections*/
       MultiThreadedServer server = new MultiThreadedServer(4444);
       server.start();
   }

}
