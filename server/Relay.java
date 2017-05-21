package greeleysmtpserver.server;

import greeleysmtpserver.database.UserDatabase;
import greeleysmtpserver.parser.*;
import greeleysmtpserver.responder.Codes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 * @author michaelmaitland
 */
public class Relay {

    private final int[] PORTS = {25, 465, 587};
    private final static Logger requestLogger = Logger.getLogger("requests");
    private final static Logger errorLogger = Logger.getLogger("errors");

    private Session session;
    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;

    public Relay(Session session) {
        this.session = session;
    }

    private boolean setupStreams(int portsIndex, String server) {
        try {
            this.connection = new Socket(server, PORTS[portsIndex]);
            this.in = new BufferedReader(
                    new InputStreamReader(
                            this.connection.getInputStream()));
            this.out = new PrintWriter(
                    new OutputStreamWriter(
                            this.connection.getOutputStream()));
            this.out.flush();
            requestLogger.info("Connection opened with relay " + connection.getInetAddress().getHostAddress());
            return true;
        } catch (IOException ex) {
        } catch (RuntimeException rex) {
            errorLogger.log(Level.SEVERE, "Error opening streams" + rex.getLocalizedMessage(), rex);
        }
        return false;
    }

    public void relayMail() {

        String data = session.getData();
        String from = session.getFrom();
        List<String> recipients = session.getRecipients();

        for (String recipient : recipients) {
            try {
               // if(recipient.contains("@")) need to take care of local relay
                List<String> mxRecords = new MxRecord(recipient.split("@")[1]).lookupMailHosts();
                if (mxRecords == null) return;
                for (String server : mxRecords) {
                    if (tryConversation(server, recipient)) break;
                }
            } catch (NamingException ex) {
            }
        }
    }

    private boolean tryConversation(String server, String recipient) {
        for (int i = 0; i < 1; i++) {
            setupStreams(i, server);
            List<SMTPCommand> commands = new ArrayList<>();
            /*Say HELO*/
            SMTPHeloCommand helo = new SMTPHeloCommand("");
            helo.setHostName(UserDatabase.getDomain());
            commands.add(helo);
            /*Issue MAIL FROM:*/
            SMTPMailCommand mail = new SMTPMailCommand("");
            mail.setFrom(session.getFrom());
            commands.add(mail);
            /*Issue RCPT TO:*/
            SMTPRcptCommand rcpt = new SMTPRcptCommand("");
            rcpt.setTo(recipient);
            commands.add(rcpt);
            /*Issue DATA*/
            SMTPDataCommand data = new SMTPDataCommand();
            data.setData(session.getData());
            commands.add(data);
            /*Disconnect from server*/
            commands.add(new SMTPQuitCommand());
            boolean result = executeCommands(commands);
            //closeConnection();
            if (result == true) {
                requestLogger.info("Mail relayed to " + connection.getInetAddress().getHostAddress());
                return true;
            }
        }
        return false;
    }

    private boolean executeCommands(List<SMTPCommand> commands) {
        getResponse();
        for (SMTPCommand command : commands) {
            /*Execute DATA*/
            if (command.getCommand() == SMTPParser.DATA) {
                //Wrie initial DATA and see if we are good to start sending data
                this.out.write(command.getCommand().name() + "\r\n");
                this.out.flush();
                int response = getResponse();
                if (response == Codes.START_MAIL_INPUT){ // response was good so we can write message
                    this.out.write(session.getData() + "\r\n.\r\n");
                    this.out.flush();
                }
                else
                    return false;

                response = getResponse();
                if (response == Codes.REQUESTED_ACTION_OKAY) return true;//data sent
                else return false;
            }
            /*execute all other commands because they are one liners*/
            this.out.write(command.toString());
            this.out.flush();
            int response = getResponse();
            if (response != Codes.REQUESTED_ACTION_OKAY) return false; //check to make sure command was OK
        }
        return false; //never reached
    }

    private int getResponse() {
        try {
            String response = in.readLine().trim();
            if (response != null)
                if (response.matches("\\d{3} .*"))
                    return Integer.parseInt(response.substring(0, 3));
        } catch (IOException ex) {
        }
        return -1;
    }

    private void closeConnection() {
        try {
            this.in.close();
            this.out.close();
            this.connection.close();
        } catch (IOException ex) {
        } catch (RuntimeException rex) {
            errorLogger.log(Level.SEVERE, "Error closing connection" + rex.getLocalizedMessage(), rex);
        }
        requestLogger.info("Connection closed with relay " + connection.getInetAddress().getHostAddress());
    }
}
