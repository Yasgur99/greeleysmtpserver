public class SMTPDataCommand extends SMTPCommand {
	private String data;

	SMTPDataCommand() {
		this.extended = false;
	}

	// HELO <hostname>
	public void parse(String line) {
		if ( line.indexOf(" ") > -1 ) {
			data = line.substring(line.indexOf(" "), line.length());
		}
	}

	public String getCommandName() {
		return "DATA";
	}

	public String getData() {
		return data;
	}
}
