package greeleysmtpserver.parser;

public abstract class SMTPCommand {
    public abstract String getCommandName();
    public abstract void parse(String line);
}
