
package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

/**
 * @author michaelmaitland
 */

public class SMTPQuitCommand implements SMTPCommand {

    @Override
    public SMTPResponse execute(Session session) {
        return new SMTPResponse(Codes.SERVICE_CLOSING_TRANSMISSION, "Bye.");
    }
    
    @Override
    public SMTPParser getCommand() {
        return SMTPParser.QUIT;
    }
    
    @Override 
    public String toString(){
        return "QUIT" + "\r\n";
    }
}
