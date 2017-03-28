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
        }else if(command.getCommandName("MAIL FROM")){
            SMTPMailFromCommand mailFromCommand = (SMTPMailFromCommand)command;
        }else if(command.getCommandName("RCPT TO")){
            SMTPRcptCommand rcptToCommand = (SMTPRcptCommand)command;
        }else if(command.getCommandName("DATA")){
            SMTPDataCommand dataCommand = (SMTPDataCommand)command;
        }else if(command.getCommandName("RSET")){
            SMTPResetCommand rsetCommand = (SMTPResetCommand)command;
        }else if(command.getCommandName("VRFY")){
            SMTPVerifyCommand vrfyCommand = (SMTPVerifyCommand)command;
        }else if(command.getCommandName("NOOP")){
            SMTPNoopCommand = noopCommand = (SMTPNoopCommand)command;
        }
    }
}
