package greeleysmtpserver.parser;

public class SMTPRcptCommand extends SMTPCommand {

    private String recipient;
    private boolean containsColon;

    SMTPRcptCommand() {
        this.containsColon = false;
    }

    // RCPT TO:<yyyy@example.com>
    @Override
    public void parse(String line) {
        if (line.length() >= 8) { //RCPT TO:
            if (line.charAt(7) == ':') {
                this.containsColon = true;
                if (line.length() >= 9) {
                    recipient = line.substring(8).trim();
                }
            }
        }
    }
    
    public boolean getContainsColon(){
        return containsColon;
    }

    @Override
    public String getCommandName() {
        return "RCPT TO";
    }

    public String getRecipient() {
        return recipient;
    }
}
