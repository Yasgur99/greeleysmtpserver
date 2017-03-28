
// NOTE: this code taken from the HELO commmand - make sure this works.
// HOW DO THIS??? makes the server to reset all its state tables and buffers


public class SMTPHelloCommand extends SMTPCommand {

	private String hostname;

	SMTPMailFromCommand() {
		this.extended = false;
	}

	// MAIL FROM <hostname>
	public void parse(String line) {
		if ( line.indexOf(" ") > -1 ) {
			hostname = line.substring(line.indexOf("MAIL FROM") + 11, line.length()-1);
		}
	}

	public String getCommandName() {
		return "MAIL FROM";
	}

	public String getHostName() {
		return hostname;
	}

	//In this below function, I am trying to reply: "250 OK" if the email is accepted in the server. 
	//Does the parser function do this? DO I have to read a file (to make sure the server can accept this?)

	public String returnOK (getHostName()){
		return "250 OK";
	}
}