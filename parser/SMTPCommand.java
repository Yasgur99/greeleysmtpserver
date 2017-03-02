public abstract class SMTPCommand {
	protected String[] arguments;
	protected boolean extended;

	public abstract String getCommandName();
	public abstract void parse(String line);

	public String[] getArguments() {
		return arguments;
	}
}
