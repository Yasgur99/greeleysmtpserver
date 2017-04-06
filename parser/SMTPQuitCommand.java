
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
