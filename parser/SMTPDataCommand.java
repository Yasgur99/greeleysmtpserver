package greeleysmtpserver.parser;

public class SMTPDataCommand extends SMTPCommand {

    private String data;
    private boolean done;

    SMTPDataCommand() {
    }

    // DATA ...
    public void parse(String line) {
        // nothing to parse
    }

    public void addData(String data) {
        this.data = this.data + data;
    }

    public String getCommandName() {
        return "DATA";
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public String getData() {
        return data;
    }
}
