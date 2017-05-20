package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public class SMTPRsetCommand implements SMTPCommand {

    private String hostname;

    //TODO: check to see if we should return 250 if contains params
    @Override
    public SMTPResponse execute(Session session) {
        synchronized (session) {
            session.reset();
        }
        return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Ok.");
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.RSET;
    }
    
    @Override
    public String toString(){
        return "RSET";
    }
}
