package greeleysmtpserver.parser;

import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public interface SMTPCommand {
    public SMTPParser getCommand();
    public SMTPResponse execute(Session session);
}