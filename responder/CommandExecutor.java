package greeleysmtpserver.responder;

/**
 * @author michaelmaitland
 */
import greeleysmtpserver.parser.*;
import greeleysmtpserver.server.Session;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommandExecutor {

    private Session session;

    public CommandExecutor(Session session) {
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

    private static SMTPResponse executeHelo(SMTPHeloCommand heloCommand) {
        SMTPResponse response = new SMTPResponse();
        /*Handle someone not specifying who they are saying helo to*/
        if (heloCommand.getHostName() == null || heloCommand.getHostName().equals("")
                || heloCommand.getHostName().contains(" ")) {
            response.setCode(Codes.SYNTAX_ERROR_PARAMETERS);
            response.setMessage("Syntax error in command parameters");
        } else {
            response.setCode(Codes.REQUESTED_ACTION_OKAY);
            try {
                String serverIP = InetAddress.getLocalHost().getHostAddress();
                response.setMessage(serverIP);
            } catch (UnknownHostException ex) {
                response.setMessage("I'm not sure of my IP address but");
            }
            response.setMessage(response.getMessage() + " hello there.");
        }
        return response;
    }

    private static SMTPResponse executeMailFrom(SMTPMailFromCommand mailFromCommand) {
        SMTPResponse response = new SMTPResponse();
        //if(mailFromCommand.getFrom()))
        return response;
    }

    private static SMTPResponse executeRcptTo(SMTPRcptCommand rcptToCommand) {
        SMTPResponse response = new SMTPResponse();
        //TODO: implement RCPT TO Response
        return response;
    }

    private static SMTPResponse executeData(SMTPDataCommand dataCommand) {
        SMTPResponse response = new SMTPResponse();
        if (dataCommand.isDone()) {
            //TODO: send the email
        }
            response.setCode(250);
            response.setMessage("Ok.");
            return response;
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
