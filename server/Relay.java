package greeleysmtpserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 * @author michaelmaitland
 */
public class Relay {

    private Session session;
    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;

    public Relay(Session session) {
        this.session = session;
    }

    public void relayMail() {

        String data = session.getData();
        String from = session.getFrom();
        List<String> recipients = session.getRecipients();

        for (String recipient : recipients) {
            List<String> mxRecords = new MxRecord(recipient.split("@")[1]).executeLookup();
            if(mxRecords == null) return;
            for (String server : mxRecords) {
                if (tryConversation(server, recipient)) break;
            }
        }
    }

    private boolean tryConversation(String server, String recipient) {
        int[] ports = {25, 465, 587};
        for (int i = 0; i < ports.length; i++) {
            try {
                this.connection = new Socket(server, ports[i]);
                setupStreams();
                this.out.write("HELO " + server);
                this.out.flush();
                this.out.write("MAIL FROM: " + session.getFrom());
                this.out.flush();
                this.out.write("RCPT TO: " + recipient);
                this.out.flush();
                this.out.write("DATA");
                this.out.write(session.getData());
                this.out.flush();
                this.out.write("QUIT");
                this.out.flush();
            } catch (IOException ex) {
            }
        }
        return false;
    }

    private boolean setupStreams() {
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(
                            this.connection.getInputStream()));
            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            this.connection.getOutputStream()));
            this.out.flush();
            return true;
        } catch (IOException ex) {
        } catch (RuntimeException rex) {
        }
        return false;
    }
}
