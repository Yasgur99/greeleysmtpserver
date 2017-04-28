package greeleysmtpserver.parser;

import greeleysmtpserver.responder.Codes;
import greeleysmtpserver.responder.SMTPResponse;
import greeleysmtpserver.server.Session;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author michaelmaitland
 */
public enum SMTPParser {

    HELO("HELO") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPHeloCommand(line);
        }
        
    },
    MAIL_FROM("MAIL FROM") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPMailCommand(line);
        }
    },
    RCPT_TO("RCPT TO") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPRcptCommand(line);
        }
    },
    DATA("DATA") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPDataCommand();
        }
    },
    RSET("RSET") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPRsetCommand();
        }
    },
    VRFY("VRFY") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPVrfyCommand(line);
        }
    },
    NOOP("NOOP") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPNoopCommand();
        }
    },
    QUIT("QUIT") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPQuitCommand();
        }
    },
    INVALID("") {
        @Override
        public SMTPCommand getCommand(String line) {
            return new SMTPInvalidCommand();
        }
    };

    private String commandName;

    public abstract SMTPCommand getCommand(String line);

    private SMTPParser(String commandName) {
        this.commandName = commandName;
    }

    public static SMTPCommand parse(String line) {
        for (SMTPParser c : values()) {
            if (c.equals(INVALID)) continue;
            if (line.toUpperCase().startsWith(c.commandName))
                return c.getCommand(line);
        }
        return new SMTPInvalidCommand();
    }
}
