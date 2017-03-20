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

    }
}
