package greeleysmtpserver.parser;

import greeleysmtpserver.database.UserDatabase;
import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public class SMTPMailCommand implements SMTPCommand {

    private String from;
    private boolean containsColon;

    public SMTPMailCommand(String line) {
        this.containsColon = false;
        parse(line);
    }

    // MAIL FROM:<yyyy@example.com>
    private void parse(String line) {
        if (line.matches("(?i)MAIL FROM:.*"))
            this.containsColon = true;
        if(line.matches("(?i)MAIL FROM:\\s?\\S[a-zA-Z0-9_\\.]*@"+UserDatabase.getDomain()))
            from = line.substring(10,line.indexOf("@")).trim();
        else if (line.matches("(?i)MAIL FROM:\\s?\\S[a-zA-Z0-9_\\.]*")) 
            from = line.substring(10).trim();
    }

    @Override
    public SMTPResponse execute(Session session) {
        /*Check to make sure we issued necessary preceding commands*/
        if (!session.didSayHelo())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "HELO was never issued.");
        else if (session.didSpecifyMailFrom())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "MAIL already issued.");

        /*Check validity of statement*/
        if (!containsColon)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No FROM: in MAIL.");
        else if (from == null)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No MAIL FROM argument found.");
        else if (from.contains(" ")) {
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "Unknown MAIL FROM paramater \""
                    + from.substring(from
                            .indexOf(" ")) + "\".");
        } else if (UserDatabase.getInstance().containsUser(from)) {
            session.setDidSpecifyMailFrom(true);
            session.setFrom(from);
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Address Ok.");
        } else
            return new SMTPResponse(Codes.REQUESTED_ACTION_NOT_TAKEN_MAILBOX_UNAVALIABLE, "Mailbox not found in our database.");//TODO: make sure this is the correct code
    }

    public boolean getContainsColon() {
        return containsColon;
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.MAIL_FROM;
    }

    public String getFrom() {
        return from;
    }
    
    @Override
    public String toString(){
        return "MAIL FROM: " + from;
    }
}
