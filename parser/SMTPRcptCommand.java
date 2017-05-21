package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public class SMTPRcptCommand implements SMTPCommand {

    private String recipient;
    private boolean containsColon;

    public SMTPRcptCommand(String line) {
        this.containsColon = false;
        parse(line);
    }

    // RCPT TO:<yyyy@example.com>
    private void parse(String line) {
        if (line.matches("(?i)RCPT TO:.*"))
            this.containsColon = true;
        if (line.matches("(?i)RCPT TO:\\s?\\S.*")) 
            recipient = line.substring(8).trim();
    }
    
    public SMTPResponse execute(Session session){
        /*Check to make sure we issued necesarry preceding commands*/
        if (!session.didSayHelo())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "HELO was never issued.");
        else if (!session.didSpecifyMailFrom())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "No MAIL FROM command has been issued.");

        /*Check validity of command*/
        if (!containsColon)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No TO: in RCPT.");
        else if (recipient == null)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No RCPT TO argument found.");
        else if (recipient.contains(" ")) {
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "Unknown RCPT TO paramater \""
                    + recipient.substring(recipient.indexOf(" ")) + "\".");
        } else { //else we are good 
            if (!session.didSpecifyRcptTo()) session.setDidSpecifyRcptTo(true);
            session.addRecipient(recipient);
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, recipient + " Ok.");
        }
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.RCPT_TO;
    }

    public String getRecipient() {
        return recipient;
    }
    
    public void setTo(String recipient){
        this.recipient = recipient;
    }
    
    @Override
    public String toString(){
        return "RCPT TO: " + recipient + "\r\n";
    }
}
