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

		if (verb.equals("MAIL FROM")) {
			SMTPMailFromCommand object = new SMTPMailFromCommand; 
			object.parse(line); 
			return (SMTPCommand) object; 

		}

		if (verb.equals("MAIL FROM")) {
			SMTPRcptToCommnand object = new SMTPRcptToCommnand; 
			object.parse(line); 
			return (SMTPCommand) object; 

		}
	}
}
