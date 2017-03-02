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

	// Parses the SMTP lines into a command
	public SMTPCommand parse(String line) {
		String verb = "";

		for ( int i = 0; i < commands.length; i++ ) {
			if ( commands[i].length() < line.length() && line.startsWith(commands[i]) ) {
				verb = commands[i];
			}
		}

		if ( verb.equals("") ) {
			return null;
		}

		if ( verb.equals("HELO") ) {
			SMTPHelloCommand cmd = new SMTPHelloCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}
	}
}
