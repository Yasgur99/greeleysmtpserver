package greeleysmtpserver.parser;

public class SMTPRsetCommand extends SMTPCommand {

    private String hostname;

    SMTPRsetCommand() {
    }

    public void parse(String line) {
    }

    public String getCommandName() {
        return "RSET";
    }
}
