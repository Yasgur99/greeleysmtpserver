package greeleysmtpserver.parser;

public class SMTPRcptCommand extends SMTPCommand {
	private String from;

	SMTPRcptCommand() {
		extended = false;
	}

	// RCPT TO:<yyyy@example.com>
	public void parse(String line) {
		if ( line.indexOf(":") > -1 ) {
			from = line.substring(line.indexOf(":"), line.length()).trim();
			from = from.substring(1, from.length() - 1);
		}
	}

	public String getCommandName() {
		return "RCPT TO";
	}

	public String getFrom() {
		return from;
	}
}
