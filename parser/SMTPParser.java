public class SMTPParser {
	private SMTPCommand parse(String line) {
		String verb = line;

		if ( verb.indexOf(" ") > -1 ) {
			verb = verb.substring(0, verb.indexOf(" "));
		}

		if ( verb.equals("HELO") ) {
			SMTPHelloCommand cmd = new SMTPHelloCommand();
			cmd.parse(line);
			return (SMTPCommand) cmd;
		}
	}
}
