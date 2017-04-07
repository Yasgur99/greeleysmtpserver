
package greeleysmtpserver.parser;
/**
 * @author michaelmaitland
 */

public class SMTPNoopCommand extends SMTPCommand{

    @Override
    public String getCommandName() {
        return "NOOP";
    }

    @Override
    public void parse(String line) {
    }

}
