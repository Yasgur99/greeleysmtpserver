package greeleysmtpserver.server;

import greeleysmtpserver.parser.SMTPCommand;
import greeleysmtpserver.responder.SMTPResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author michaelmaitland
 */
public class Session {

    private boolean didSayHelo;
    private boolean didSpecifyMailFrom;
    private boolean didSpecifyRcptTo;
    private boolean isWritingData;
    private boolean doneWritingData;

    private String from;
    private List<String> recipients;

    public void setFrom(String from) {
        this.recipients = new ArrayList<>();
        this.from = from;
    }

    public void addRecipient(String recipient) {
        this.recipients.add(recipient);
    }

    public boolean didSayHelo() {
        return didSayHelo;
    }

    public void setDidSayHelo(boolean didSayHelo) {
        this.didSayHelo = didSayHelo;
    }

    public boolean didSpecifyMailFrom() {
        return didSpecifyMailFrom;
    }

    public void setDidSpecifyMailFrom(boolean didSpecifyMailFrom) {
        this.didSpecifyMailFrom = didSpecifyMailFrom;
    }

    public boolean didSpecifyRcptTo() {
        return didSpecifyRcptTo;
    }

    public void setdidSpecifyRcptTo(boolean didSpecifyRcptTo) {
        this.didSpecifyRcptTo = didSpecifyRcptTo;
    }

    public boolean isWritingData() {
        return isWritingData;
    }

    public void setIsWritingData(boolean isWritingData) {
        this.isWritingData = isWritingData;
    }

    public boolean isDoneWritingData() {
        return doneWritingData;
    }

    public void setDoneWritingData(boolean doneWritingData) {
        this.doneWritingData = doneWritingData;
    }

    public void reset() {
        didSayHelo = false;
        didSpecifyMailFrom = false;
        didSpecifyRcptTo = false;
        isWritingData = false;
        doneWritingData = false;
        from = null;
        recipients = new ArrayList<>();
    }
}
