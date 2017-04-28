
package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

/**
 * @author michaelmaitland
 */

public class SMTPNoopCommand implements SMTPCommand{

    @Override
    public SMTPResponse execute(Session session) {
        return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Ok.");
    }
    
    @Override
    public SMTPParser getCommand() {
        return SMTPParser.NOOP;
    }
}
