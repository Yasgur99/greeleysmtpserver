package greeleysmtpserver;

/**
 * @author michaelmaitland
 */

import greeleysmtpserver.database.UserDatabase;
import greeleysmtpserver.server.MultiThreadedServer;

public class Main {

    public static void main(String[] args) {
        /*Output ip addresses as IPv4 if possible*/
        System.setProperty("java.net.preferIPv4Stack", "true");
        /*Set config file for logs*/
        System.setProperty("java.util.logging.config.file",
                "src/greeleysmtpserver/server/logger.properties");
        
        UserDatabase userDB = UserDatabase.getInstance();
        addDummyUsers(userDB);
        
        /*Create and run server so it is listening for connections*/
        MultiThreadedServer server = new MultiThreadedServer(4444);
        server.start();
    }
    
    public static void addDummyUsers(UserDatabase userDB){
        userDB.add("michaelmaitland", "password");
        userDB.add("avisaven", "password1");
        userDB.add("jamiescharf", "lolUWillNeverGuess");
        userDB.add("bjornchen", "madHax3r");
    }
}
