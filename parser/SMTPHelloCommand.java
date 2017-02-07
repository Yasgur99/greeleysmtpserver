public class SMTPHelloCommand extends SMTPCommand {
	private String hostname;

	public void parse(String line) {
		hostname = line.substring(line.indexOf(" "), line.length());
	}

	public String getCommandName() {
		return "HELO";
	}
}
