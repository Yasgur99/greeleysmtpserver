package greeleysmtpserver.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;
import greeleysmtpserver.parser.*;
import greeleysmtpserver.responder.*;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yasgur99
 */
public class ClientHandler implements Callable<Void> {

    private final static Logger requestLogger = Logger.getLogger("requests");
    private final static Logger errorLogger = Logger.getLogger("errors");

    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;
    private Session session;
    private boolean connected;
    private SMTPParser parser;
    private SMTPCommandExecutor commandExecutor;

    public ClientHandler(Socket connection) {
        this.session = new Session();
        this.parser = new SMTPParser();
        this.commandExecutor = new SMTPCommandExecutor(this.session);
        this.connection = connection;
        this.connected = true;
        requestLogger.info("Connection opened with " + connection.getInetAddress().getHostAddress());
    }

    @Override
    public Void call() {
        setupStreams();
        //TODO: check to make sure streams are setup

        /*Continously get instructions from client while connection is open*/
        while (connected) {
            SMTPResponse response = readClientMessage(); //get input and parse it
            if (response == null) break; //returns null if bad input (such as client disconnected)
            writeResponse(response);//send our resposne to client
            if (response.getCode() == 221) { //check to see if client asked to quit
                this.connected = false;
                break;
            }
        }
        closeConnection();
        return null;
    }

    private boolean setupStreams() {
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(
                            this.connection.getInputStream()));
            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            this.connection.getOutputStream()));
            this.out.write("Connection with server has been made\n");
            this.out.flush();
            return true;
        } catch (IOException ex) {
            this.connected = false;
        } catch (RuntimeException rex) {
            errorLogger.log(Level.SEVERE, "Error opening streams" + rex.getLocalizedMessage(), rex);
        }
        return false;
    }

    private SMTPResponse readClientMessage() {
        String line;
        try {
            line = in.readLine(); //try and read line from server
            if (line != null) {
                line = line.trim();
                return commandExecutor.execute(parser.parse(line));
            }
        } catch (IOException ex) { //error reading
            this.connected = false;
        } catch (RuntimeException rex) { //we read in nothing
            errorLogger.log(Level.SEVERE, "Unexpected error reading client message" + rex.getLocalizedMessage(), rex);
            this.connected = false;
        }
        return null;

    }

    private void writeResponse(SMTPResponse response) {
        if(session.isWritingData()) return; //dont respond if we are geting data from client still
        try {
            this.out.write(response.toString() + "\n");
            this.out.flush();
        } catch (RuntimeException rex) {
            errorLogger.log(Level.SEVERE, "Error writing response" + rex.getLocalizedMessage(), rex);
        }
    }

    private void closeConnection() {
        try {
            this.in.close();
            this.out.close();
            this.connection.close();
        } catch (IOException ex) {
        } catch (RuntimeException rex) {
            errorLogger.log(Level.SEVERE, "Error reading message" + rex.getLocalizedMessage(), rex);
        }
        this.connected = false;
        requestLogger.info("Connection closed with " + connection.getInetAddress().getHostAddress());
    }
}
