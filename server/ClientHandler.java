package greeleysmtpserver.server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;
import greeleysmtpserver.parser.*;
import greeleysmtpserver.responder.*;

/**
 * @author yasgur99
 */
public class ClientHandler implements Callable<Void> {

    private Socket connection;
    private Boolean running;
    private Reader in;
    private Writer out;

    public ClientHandler(Socket connection, Boolean running) {
        this.connection = connection;
        this.running = running;
    }

    @Override
    public Void call() {
        setupStreams();
        /*Continously get instructions from client*/
        while (true) {
            SMTPResponse response = readClientMessage(); //get input and parse it
            writeResponse(response); //send our resposne to client
            if (response.getCode() == 221) break; //Check if we are done talking to client
        }
        closeConnection();
        return null;
    }

    private void setupStreams() {
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(
                            this.connection.getInputStream()));
            System.out.println("Input stream is setup");
        } catch (IOException ex) {
            System.err.println("IOException setting up input stream");
        }

        try {
            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            this.connection.getOutputStream()));
            System.out.println("Output stream setup");
            this.out.write("Connection with server has been made");
        } catch (IOException ex) {
            System.err.println("IOException setting up output stream");
        }
    }

    private SMTPResponse readClientMessage() {
        SMTPParser parser = new SMTPParser();
        while (true) {
            /*Read line in from client*/
            StringBuilder line = new StringBuilder();
            try {
                int c;
                while ((c = in.read()) != -1)
                    line.append((char) c);
            } catch (IOException ex) {
                System.err.println("IOException while reading the clients mesasge");
            }

            /*Handle line*/
            System.out.println("Client: " + line);
            /*Get parsed SMTP command and execute it*/
            return CommandExecutor.execute(parser.parse(line.toString()));

        }
    }

    private void writeResponse(SMTPResponse response) {
        try {
            this.out.write(response.toString());
            this.out.flush();
        }catch(IOException ex){
            System.err.printf("IOException while sending response to client");
        }
    }

    private void closeConnection() {
        try {
            this.in.close();
            this.out.write("Closing connection");
            this.out.flush();
            this.out.close();
            this.connection.close();
        } catch (IOException ex) {
            System.err.println("IOException closing resources");
        }
    }
}
