
package greeleysmtpserver.parser;
/**
 * @author michaelmaitland
 */

public class SMTPQuitCommand extends SMTPCommand {

    @Override
    public String getCommandName() {
        return "QUIT";
    }

    @Override
    public void parse(String line) {
    }

}
