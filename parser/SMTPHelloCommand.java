public class SMTPHelloCommand implements SMTPCommand {
	public void parse(String line) {
		// ...
	}

	public String getCommandName() {
		return "HELO";
	}
}