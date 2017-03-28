public class SMTPVrfyCommand extends SMTPCommand {
	private String hostname; 




	//Example: VRFY hostname

	public void parse (Sting line){
		if ( line.indexOf(" ") > -1 ) {
		hostname = line.substring(line.indexOf(" ") +1, line.length()); 
		}

	}

	public String getCommandName(){
		return "VRFY"; 

	}

	public String gethostname (){
		return hostname; 
	}


}