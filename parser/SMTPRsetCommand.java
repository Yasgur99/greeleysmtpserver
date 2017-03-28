
public class SMTPResetCommand extends SMTPCommand {
	private String hostname;

	SMTPHelloCommand() {
		this.extended = false;
	}


	public void parse(String line) {
	} 


	public String getCommandName() {
		return "RSET";
	}
}