package greeleysmtpserver.parser;

import greeleysmtpserver.database.MessageDatabase;
import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Relay;
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
        else {
            session.setData(this.data.toString());
            MessageDatabase.getInstance().addMessage(session);
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Ok.");
        }
    }

    public void addData(String data, Session session) {
        /*Check to see if signaling end of data transmission*/
        if (data.equals(".")) {
            done = true;
            session.setDoneWritingData(true);
            return;
        }
        /*Set memo header items in session*/
        if(data.matches("(D|d)ate:.*"))
            session.setDate(data.substring(5).trim());
        if (data.matches("(S|s)ubject:.*")) 
            session.setSubject(data.substring(8).trim());
        if(data.matches("(T|t)o:.*"))
            session.setTo(data.substring(3).trim());
        if(data.matches("(C|c){2}:.*"))
            session.setCc(data.substring(3).trim());
        if(data.matches("(F|f)rom:.*"))
            session.setDate(data.substring(5).trim());
        
        this.data.append(data); //add the line regardless of memo or message (data gets transported)
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
    
    public void setData(String data){
        this.data = new StringBuilder().append(data);
    }
    
    @Override 
    public String toString(){
        /*!!!!DO NOT USE THIS TO WRITE TO A SERVER - NEED TO WAIT FOR RESPONSE BEFORE WRITING DATA!!!!*/
        return "DATA\r\n" + data.toString();
    }
}
