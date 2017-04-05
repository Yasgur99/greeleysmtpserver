package greeleysmtpserver.parser;

public abstract class SMTPCommand {
    protected boolean extended;
    public abstract String getCommandName();
    public abstract void parse(String line);
}
