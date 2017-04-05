package greeleysmtpserver.responder;

/**
 * @author michaelmaitland
 */

import greeleysmtpserver.parser.*;

public class CommandExecutor {


    public static SMTPResponse execute(SMTPCommand command){
        if(command.getCommandName().equals("HELO")){
            SMTPHeloCommand heloCommand = (SMTPHeloCommand)command;
            //TODO: Verify Hostname
        }else if(command.getCommandName().equals("MAIL FROM")){
            SMTPMailFromCommand mailFromCommand = (SMTPMailFromCommand)command;
            //TODO: email sender, timestamp, relay server
        }else if(command.getCommandName().equals("RCPT TO")){
            SMTPRcptCommand rcptToCommand = (SMTPRcptCommand)command;
            //TODO: email reciever
        }else if(command.getCommandName().equals("DATA")){
            SMTPDataCommand dataCommand = (SMTPDataCommand)command;
            //TODO: Message Contents
        }else if(command.getCommandName().equals("RSET")){
            SMTPRsetCommand rsetCommand = (SMTPRsetCommand)command;
            //TODO: Clear state information so we dont store the same info (from above)
        }else if(command.getCommandName().equals("VRFY")){
            SMTPVrfyCommand vrfyCommand = (SMTPVrfyCommand)command;
            //TODO: return an error
        }else if(command.getCommandName().equals("NOOP")){
            SMTPNoopCommand noopCommand = (SMTPNoopCommand)command;
            //WHY DOES THIS COMMAND EXIST
        }
        return null;
    }
}
