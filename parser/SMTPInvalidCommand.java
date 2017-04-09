
package greeleysmtpserver.parser;
/**
 * @author michaelmaitland
 */

public class SMTPInvalidCommand extends SMTPCommand {
    
    public SMTPInvalidCommand(){
    }

    @Override
    public String getCommandName() {
        return "Invalid Command";
    }

    @Override
    public void parse(String line) {
    }
}
