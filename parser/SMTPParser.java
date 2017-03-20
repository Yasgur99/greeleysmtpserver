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

		if ( verb.equals("HELO") ) {
			SMTPHelloCommand cmd = new SMTPHelloCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}

		if ( verb.equals("MAIL FROM") ) {

		}

		if ( verb.equals("RCPT TO") ) {

		}

		if ( verb.equals("DATA") ) {
			SMTPDataCommand cmd = new SMTPDataCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}

		if ( verb.equals("RSET") ) {

		}

		if ( verb.equals("VRFY") ) {

		}

		if ( verb.equals("NOOP") ) {

		}

		if ( verb.equals("QUIT") ) {

		}

		return null;
	}
}
