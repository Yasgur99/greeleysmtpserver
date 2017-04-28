package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

/**
 * @author michaelmaitland
 */

public class SMTPInvalidCommand implements SMTPCommand {

    @Override
    public SMTPResponse execute(Session session) {
        return new SMTPResponse(Codes.SYNTAX_ERROR_COMMAND_UNRECOGNIZED, "Invalid Command.");
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.INVALID;
    }
}
