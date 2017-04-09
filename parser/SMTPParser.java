package greeleysmtpserver.parser;

public class SMTPParser {

    private String[] commands = {
        "HELO",
        "MAIL FROM",
        "RCPT TO",
        "DATA",
        "RSET",
        "VRFY",
        "NOOP",
        "QUIT"
    };

    private SMTPDataCommand parsingData = null;

    // Parses the SMTP lines into a command
    public SMTPCommand parse(String line) {
        if (parsingData != null) {
            if (line.equals(".")) {
                SMTPDataCommand tmp = this.parsingData;
                tmp.setDone(true);
                this.parsingData = null;
                return (SMTPCommand) tmp;
            } else {
                this.parsingData.addData(line);
                return this.parsingData;
            }
        }

        String verb = "";

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].length() <= line.length() && line.toUpperCase().startsWith(commands[i])) {
                verb = commands[i];
            }
        }

        if (verb.equals("HELO")) {
            SMTPCommand heloCommand = new SMTPHeloCommand();
            heloCommand.parse(line);
            return heloCommand;
        } else if (verb.equals("MAIL FROM")) {
            SMTPCommand mailFromCommand = new SMTPMailFromCommand();
            mailFromCommand.parse(line);
            return mailFromCommand;
        } else if (verb.equals("RCPT TO")) {
            SMTPCommand rcptToCommand = new SMTPRcptCommand();
            rcptToCommand.parse(line);
            return (SMTPCommand) rcptToCommand;
        } else if (verb.equals("DATA")) {
            this.parsingData = new SMTPDataCommand();
            this.parsingData.setDone(false);
            return this.parsingData;
        } else if (verb.equals("RSET")) {

        } else if (verb.equals("VRFY")) {

        } else if (verb.equals("NOOP")) {

        } else if (verb.equals("QUIT")) {
            return new SMTPQuitCommand();
        } 
            return new SMTPInvalidCommand();
    }
}
