public class SMTPHelloCommand extends SMTPCommand {
	private String hostname;

	SMTPHelloCommand() {
		this.extended = false;
	}

	// HELO <hostname>
	public void parse(String line) {
		if ( line.indexOf(" ") > -1 ) {
			hostname = line.substring(line.indexOf(" "), line.length());
		}
	}

	public String getCommandName() {
		return "HELO";
	}

	public String getHostName() {
		return hostname;
	}
}
