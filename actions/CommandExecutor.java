package greeleysmtpserver;

/**
 * @author michaelmaitland
 */

import greeleysmtpserver.parser.SMTPCommand;

public class CommandExecutor {

    private SMTPCommand command;

    public CommandExecutor(SMTPCommand command){
        this.command = command;
    }

    public String execute(){
        if(command.getCommandName().equals("HELO")){
            SMTPHelloCommand heloCommand = (SMTPHelloCommand)command;
            //TODO: Verify Hostname
        }else if(command.getCommandName("MAIL FROM")){
            SMTPMailFromCommand mailFromCommand = (SMTPMailFromCommand)command;
            //TODO: email sender, timestamp, relay server
        }else if(command.getCommandName("RCPT TO")){
            SMTPRcptCommand rcptToCommand = (SMTPRcptCommand)command;
            //TODO: email reciever
        }else if(command.getCommandName("DATA")){
            SMTPDataCommand dataCommand = (SMTPDataCommand)command;
            //TODO: Message Contents
        }else if(command.getCommandName("RSET")){
            SMTPResetCommand rsetCommand = (SMTPResetCommand)command;
            //TODO: Clear state information so we dont store the same info (from above)
        }else if(command.getCommandName("VRFY")){
            SMTPVerifyCommand vrfyCommand = (SMTPVerifyCommand)command;
            //TODO: return an error
        }else if(command.getCommandName("NOOP")){
            SMTPNoopCommand = noopCommand = (SMTPNoopCommand)command;
            //WHY DOES THIS COMMAND EXIST
        }
    }
}
