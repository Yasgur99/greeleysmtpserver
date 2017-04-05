package greeleysmtpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

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
    public void call() {
        setupStreams();
        readClientMessage();
        closeConnection();
    }

    private void setupStreams() {
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(
                            this.connection.getInputStream()));
            System.out.println("Input stream is setup");
        } catch (IOException ex) {
            System.out.println("IOException setting up input stream");
        }

        try {
            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            this.connection.getOutputStream()));
            System.out.println("Output stream setup");
            this.out.write("Connection with server has been made");
        } catch (IOException ex) {
            System.out.println("IOException setting up output stream");
        }
    }

    private void readClientMessage() {
        try {
            STMPParser p = new SMTPParser();
            CommandExecutor executor = new CommandExecutor();
            String line = null;

            while (p.parse(line).equals("QUIT") {
                line = in.readLine();
                System.out.println("Client: " + line);
                /*Get parsed SMTP command and execute it*/
                String response = executor.execute(p.parse());
                this.out.write(response);
                if(response.contains("221 ") &&
                        response.contains("closing connection"))break; //Check if we are done
            }
        } catch (IOException ex) {
            System.out.println("IOException reading the clients message");
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
            System.out.println("IOException closing resources");
        }
    }
}
