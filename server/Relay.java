package greeleysmtpserver.server;

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
import javax.naming.NamingException;

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

    public void relayMail() {

        String data = session.getData();
        String from = session.getFrom();
        List<String> recipients = session.getRecipients();

        for (String recipient : recipients) {
            try {
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
        int[] ports = {25, 465, 587};
        for (int i = 0; i < ports.length; i++) {
            try {
                this.connection = new Socket(server, ports[i]);
                setupStreams();
                List<SMTPCommand> commands = new ArrayList<>();
                /*Say HELO*/
                commands.add(new SMTPHeloCommand(recipient));
                /*Issue MAIL FROM:*/
                commands.add(new SMTPMailCommand(session.getFrom()));
                /*Issue RCPT TO:*/
                commands.add(new SMTPRcptCommand(recipient));
                /*Issue DATA*/
                SMTPDataCommand data = new SMTPDataCommand();
                data.setData(session.getData());
                commands.add(data);
                /*Disconnect from server*/
                commands.add(new SMTPQuitCommand());
                boolean result = executeCommands(commands);
                closeConnection();
                return result;
            } catch (IOException ex) {
            }
        }
        return false; 
    }

    private boolean executeCommands(List<SMTPCommand> commands) {
        for (SMTPCommand command : commands) {
            /*Execute DATA*/
            if (command.getCommand() == SMTPParser.DATA) {
                //Wrie initial DATA and see if we are good to start sending data
                this.out.write(command.getCommand().name());
                this.out.flush();
                int response = getResponse();
                if (response == Codes.START_MAIL_INPUT) // response was good so we can write message
                    this.out.write(session.getData());
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
            if(response != Codes.REQUESTED_ACTION_OKAY) return false; //check to make sure command was OK
        }
        return false; //never reached
    }

    private int getResponse() {
        try {
            String response = this.in.readLine().trim();
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
        }
    }
}
