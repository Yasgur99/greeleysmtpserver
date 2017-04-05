package greeleysmtpserver.parser;

public class SMTPRsetCommand extends SMTPCommand {
	private String hostname;
        private boolean extended;

	SMTPRsetCommand() {
		this.extended = false;
	}


	public void parse(String line) {
	} 


	public String getCommandName() {
		return "RSET";
	}
}