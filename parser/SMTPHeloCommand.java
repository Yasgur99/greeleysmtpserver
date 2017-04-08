package greeleysmtpserver.parser;

public class SMTPHeloCommand extends SMTPCommand {

    private String hostname;

    SMTPHeloCommand() {
        this.extended = false;
    }

    // HELO <hostname>
    @Override
    public void parse(String line) {
        if (line.indexOf(" ") > -1) {
            hostname = line.substring(line.indexOf(" ") + 1, line.length()).trim();
        }
    }

    public String getCommandName() {
        return "HELO";
    }

    public String getHostName() {
        return hostname;
    }
}
