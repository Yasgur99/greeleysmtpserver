package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SMTPHeloCommand implements SMTPCommand {

    private String hostname;

    public SMTPHeloCommand(String line) {
        parse(line);
    }

    // HELO <hostname>
    private void parse(String line) {
        if(line.matches("(?i)HELO\\s(.)*"))
            hostname = line.substring(line.indexOf(" ") + 1).trim();
    }

    @Override
    public SMTPResponse execute(Session session) {
        /*Handle someone not specifying who they are saying helo to*/
        if (hostname == null || hostname.equals("") || hostname.contains(" "))
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No HELO argument found.");

        /*Send response containing our IP*/
        SMTPResponse response = new SMTPResponse();
        response.setCode(Codes.REQUESTED_ACTION_OKAY);
        try {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            response.setMessage("I'm " + serverIP);
        } catch (UnknownHostException ex) {
            response.setMessage("I'm not sure of my IP address but");
        }
        response.setMessage(response.getMessage() + " hello there.");
        session.setDidSayHelo(true);
        return response;
    }
    
    @Override
    public SMTPParser getCommand() {
        return SMTPParser.HELO;
    }

    public String getHostName() {
        return hostname;
    }
}
