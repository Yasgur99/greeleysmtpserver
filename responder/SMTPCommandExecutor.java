package greeleysmtpserver.responder;

/**
 * @author michaelmaitland
 */
import greeleysmtpserver.parser.*;
import greeleysmtpserver.server.Session;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SMTPCommandExecutor {

    private Session session;

    public SMTPCommandExecutor(Session session) {
        this.session = session;
    }

    public SMTPResponse execute(SMTPCommand command) {
        if (command.getCommandName().equals("HELO")) {
            SMTPHeloCommand heloCommand = (SMTPHeloCommand) command;
            return executeHelo(heloCommand);
        } else if (command.getCommandName().equals("MAIL FROM")) {
            SMTPMailFromCommand mailFromCommand = (SMTPMailFromCommand) command;
            return executeMailFrom(mailFromCommand);
        } else if (command.getCommandName().equals("RCPT TO")) {
            SMTPRcptCommand rcptToCommand = (SMTPRcptCommand) command;
            return executeRcptTo(rcptToCommand);
        } else if (command.getCommandName().equals("DATA")) {
            SMTPDataCommand dataCommand = (SMTPDataCommand) command;
            return executeData(dataCommand);
        } else if (command.getCommandName().equals("RSET")) {
            SMTPRsetCommand rsetCommand = (SMTPRsetCommand) command;
            return executeRset(rsetCommand);
        } else if (command.getCommandName().equals("VRFY")) {
            SMTPVrfyCommand vrfyCommand = (SMTPVrfyCommand) command;
            return executeVrfy(vrfyCommand);
        } else if (command.getCommandName().equals("NOOP")) {
            SMTPNoopCommand noopCommand = (SMTPNoopCommand) command;
            return executeNoop(noopCommand);
        } else if (command.getCommandName().equals("QUIT")) {
            SMTPQuitCommand quitCommand = (SMTPQuitCommand) command;
            return executeQuit(quitCommand);
        } else {
            SMTPInvalidCommand invalidCommand = (SMTPInvalidCommand) command;
            return executeInvalid(invalidCommand);
        }
    }

    private SMTPResponse executeHelo(SMTPHeloCommand heloCommand) {
        /*Handle someone not specifying who they are saying helo to*/
        if (heloCommand.getHostName() == null || heloCommand.getHostName().equals("")
                || heloCommand.getHostName().contains(" ")) {
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No HELO argument found.");
        } else {
            SMTPResponse response = new SMTPResponse();
            response.setCode(Codes.REQUESTED_ACTION_OKAY);
            try {
                String serverIP = InetAddress.getLocalHost().getHostAddress();
                response.setMessage(serverIP);
            } catch (UnknownHostException ex) {
                response.setMessage("I'm not sure of my IP address but");
            }
            response.setMessage(response.getMessage() + " hello there.");
            session.setDidSayHelo(true);
            return response;
        }
    }

    private SMTPResponse executeMailFrom(SMTPMailFromCommand mailFromCommand) {
        /*Check to make sure we issued necessary preceding commands*/
        if (!session.didSayHelo())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "HELO was never issued.");
        else if (session.didSpecifyMailFrom())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "MAIL already issued.");

        /*Check validity of statement*/
        if (!mailFromCommand.getContainsColon())
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No FROM: in MAIL.");
        else if (mailFromCommand.getFrom() == null)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No MAIL FROM argument found.");
        else if (mailFromCommand.getFrom().contains(" ")) {
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "Unknown MAIL FROM paramater \""
                    + mailFromCommand.getFrom().substring(mailFromCommand.getFrom()
                            .indexOf(" ")) + "\".");
        } //TODO: else if (isInDatabase(mailFromCommand.getFrom())) {
        else if (true) {
            session.setDidSpecifyMailFrom(true);
            session.setFrom(mailFromCommand.getFrom());
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Address Ok.");
        } else
            return new SMTPResponse(Codes.REQUESTED_ACTION_NOT_TAKEN_MAILBOX_UNAVALIABLE, "Mailbox not found in our database.");//TODO: make sure this is the correct code
    }

    private SMTPResponse executeRcptTo(SMTPRcptCommand rcptToCommand) {
        /*Check to make sure we issued necesarry preceding commands*/
        if (!session.didSayHelo())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "HELO was never issued.");
        else if (!session.didSpecifyMailFrom())
            return new SMTPResponse(Codes.BAD_SEQUENCE, "No MAIL FROM command has been issued.");

        /*Check validity of command*/
        if (!rcptToCommand.getContainsColon())
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No TO: in RCPT.");
        else if (rcptToCommand.getRecipient() == null)
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "No RCPT TO argument found.");
        else if (rcptToCommand.getRecipient().contains(" ")) {
            return new SMTPResponse(Codes.SYNTAX_ERROR_PARAMETERS, "Unknown RCPT TO paramater \""
                    + rcptToCommand.getRecipient().substring(rcptToCommand.getRecipient()
                            .indexOf(" ")) + "\".");
        } else { //else we are good 
            if (!session.didSpecifyRcptTo()) session.setdidSpecifyRcptTo(true);
            session.addRecipient(rcptToCommand.getRecipient());
            return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, rcptToCommand.getRecipient() + " Ok.");
        }
    }

    private SMTPResponse executeData(SMTPDataCommand dataCommand) {
        if (dataCommand.isDone()) {
            //TODO: send the email
        }
        return new SMTPResponse(Codes.REQUESTED_ACTION_OKAY, "Ok.");
    }

    private static SMTPResponse executeRset(SMTPRsetCommand rsetCommand) {
        SMTPResponse response = new SMTPResponse();
        //TODO: implement RSET response
        return response;
    }

    private static SMTPResponse executeVrfy(SMTPVrfyCommand vrfyCommand) {
        SMTPResponse response = new SMTPResponse();
        //TODO: implement VRFY response
        return response;
    }

    private static SMTPResponse executeNoop(SMTPNoopCommand noopCommand) {
        return new SMTPResponse(250, "Ok.");
    }

    private static SMTPResponse executeQuit(SMTPQuitCommand quitCommand) {
        return new SMTPResponse(221, "Bye.");
    }

    private static SMTPResponse executeInvalid(SMTPInvalidCommand invalidCommand) {
        return new SMTPResponse(500, "Invalid Command.");
    }
}
