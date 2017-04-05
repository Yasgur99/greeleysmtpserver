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
            if (commands[i].length() < line.length() && line.startsWith(commands[i])) {
                verb = commands[i];
            }
        }

        if (verb.equals("HELO")) {
            SMTPHeloCommand cmd = new SMTPHeloCommand();
            cmd.parse(line);
            return (SMTPCommand) cmd;
        }

        if (verb.equals("MAIL FROM")) {
            SMTPMailFromCommand object = new SMTPMailFromCommand();
            object.parse(line);
            return (SMTPCommand) object;

        }

        if (verb.equals("MAIL FROM")) {
            SMTPRcptCommand object = new SMTPRcptCommand();
            object.parse(line);
            return (SMTPCommand) object;

        }

        if (verb.equals("DATA")) {
            this.parsingData = new SMTPDataCommand();
            this.parsingData.setDone(false);
            return this.parsingData;
        }

        if (verb.equals("RSET")) {

        }

        if (verb.equals("VRFY")) {

        }

        if (verb.equals("NOOP")) {

        }

        if (verb.equals("QUIT")) {

        }
        return null;
    }
}
