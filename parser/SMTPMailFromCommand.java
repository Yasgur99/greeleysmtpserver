package greeleysmtpserver.parser;

public class SMTPMailFromCommand extends SMTPCommand {

    private String from;
    private boolean containsColon;

    SMTPMailFromCommand() {
        this.containsColon = false;
    }

    // MAIL FROM:<yyyy@example.com>
    @Override
    public void parse(String line) {
        if (line.length() >= 10) { //mail from:
            if (line.charAt(9) == ':') {
                this.containsColon = true;
                if (line.length() >= 11) {
                    from = line.substring(10).trim();
                }
            }
        }
    }

    public boolean getContainsColon() {
        return containsColon;
    }

    @Override
    public String getCommandName() {
        return "MAIL FROM";
    }

    public String getFrom() {
        return from;
    }
}
