public class SMTPHelloCommand extends SMTPCommand {
	private String hostname;

	SMTPHelloCommand() {
		this.extended = false;
	}

	// HELO <hostname>
	public void parse(String line) {
		if ( line.indexOf(" ") > -1 ) {
			hostname = line.substring(line.indexOf("RCPT TO")+9, line.length()-1);
		}
	}

	public String getCommandName() {
		return "RCT TO";
	}

	public String getHostName() {
		return hostname;
	}
}
