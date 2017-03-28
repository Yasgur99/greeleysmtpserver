public class SMTPMailFromCommand extends SMTPCommand {
	private String from;

	SMTPMailFromCommand() {
		this.extended = false;
	}

	// MAIL FROM:<yyyy@example.com>
	public void parse(String line) {
		if ( line.indexOf(":") > -1 ) {
			from = line.substring(line.indexOf(":"), line.length()).trim();
			from = from.substring(1, from.length() - 1);
		}
	}

	public String getCommandName() {
		return "MAIL FROM";
	}

	public String getFrom() {
		return from;
	}
}
