
package greeleysmtpserver.parser;
/**
 * @author michaelmaitland
 */

public class SMTPInvalidCommand extends SMTPCommand {
    
    public SMTPInvalidCommand(){
        this.extended = false;
    }

    @Override
    public String getCommandName() {
        return "Invalid Command";
    }

    @Override
    public void parse(String line) {
    }
}
