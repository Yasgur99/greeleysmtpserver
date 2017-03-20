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
		if ( parsingData != null ) {
			if ( line.equals(".") ) {
				SMTPDataCommand tmp = this.parsingData;
				tmp.setDone(true);
				this.parsingData = null;
				return (SMTPCommand) tmp;
			}
			else {
				this.parsingData.addData(line);
				return this.parsingData;
			}
		}

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
			SMTPMailFromCommand cmd = new SMTPMailFromCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}

		if ( verb.equals("RCPT TO") ) {
			SMTPRcptCommand cmd = new SMTPRcptCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}

		if ( verb.equals("DATA") ) {
			this.parsingData = new SMTPDataCommand();
			this.parsingData.setDone(false);
			return this.parsingData;
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
