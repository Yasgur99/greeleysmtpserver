
package greeleysmtpserver;
/**
 * @author michaelmaitland
 */

public class CentralObject {

   public static void main(String[] args){
       /*Create and run server so it is listening for connections*/
       MultiThreadedServer server = new MultiThreadedServer(4444);
       server.run();
   }

}
