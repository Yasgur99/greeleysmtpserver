package greeleysmtpserver.parser;

import greeleysmtpserver.database.UserDatabase;
import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;

public class SMTPVrfyCommand implements SMTPCommand {

    private String username;

    public SMTPVrfyCommand(String line) {
        parse(line);
    }

    //Example: VRFY username
    private void parse(String line) {
        if (line.length() > 5 && line.charAt(4) == ' ') //VRFY 
            username = line.substring(5);
    }

    @Override
    public SMTPResponse execute(Session session) {
        //TODO: check to make sure username not null
        if (UserDatabase.getInstance().containsUser(username))
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, username
                    + "@" + UserDatabase.getDomain());
        else
            return new SMTPResponse(Codes.USER_NOT_LOCAL, "User not in our database.");//need to figure out correct code if misisng
    }

    @Override
    public SMTPParser getCommand() {
        return SMTPParser.VRFY;
    }

    public String gethostname() {
        return username;
    }
}
