package greeleysmtpserver.parser;

import greeleysmtpserver.database.MessageDatabase;
import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public class SMTPDataCommand implements SMTPCommand {

    private StringBuilder data;
    private boolean done;

    public SMTPDataCommand() {
        this.data = new StringBuilder();
    }

    @Override
    public SMTPResponse execute(Session session) {
        if (!done)
            return new SMTPResponse(Codes.START_MAIL_INPUT, "Enter mail, end with a single \".\".");
        else{
            //session.writeToDatabse();
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Ok.");
        }
    }

    public void addData(String data, Session session) {
        if (data.matches("\\.|\\.\\s*|\\s\\.|\\s*\\.\\s*")) {
            done = true;
            session.setDoneWritingData(true);
            return;
        }
        if (data.matches("(S|s)ubject:.*")) {
            session.setSubject(data.substring(8));
            return;
        }
        this.data.append(data);
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.DATA;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public String getData() {
        return data.toString();
    }
}
