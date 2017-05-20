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

    private String from;
    private boolean didSpecifyMailFrom;

    private List<String> recipients;
    private boolean didSpecifyRcptTo;

    private String data;
    private String subject;
    private String date;
    private String to;
    private String fromInData;
    private String cc;
    
    private boolean isWritingData;
    private boolean doneWritingData;

    //HELO
    public void setDidSayHelo(boolean didSayHelo) {
        this.didSayHelo = didSayHelo;
    }

    public boolean didSayHelo() {
        return didSayHelo;
    }

    //MAIL FROM
    public void setFrom(String from) {
        this.recipients = new ArrayList<>();
        this.from = from;
    }

    public String getFrom() {
        return this.from;
    }

    public void setDidSpecifyMailFrom(boolean didSpecifyMailFrom) {
        this.didSpecifyMailFrom = didSpecifyMailFrom;
    }

    public boolean didSpecifyMailFrom() {
        return didSpecifyMailFrom;
    }

    //RCPT TO
    public void addRecipient(String recipient) {
        this.recipients.add(recipient);
    }

    public List<String> getRecipients() {
        return this.recipients;
    }

    public void setDidSpecifyRcptTo(boolean didSpecifyRcptTo) {
        this.didSpecifyRcptTo = didSpecifyRcptTo;
    }

    public boolean didSpecifyRcptTo() {
        return didSpecifyRcptTo;
    }

    //DATA
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

    public void setData(String data) {
        this.data = data;
    }
    
    public String getData(){
        return this.data;
    }
    
    public void setSubject(String subject){
        this.subject = subject;
    }
    
    public String getSubject(){
        return this.subject;
    }

    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setTo(String to){
        this.to = to;
    }
    
    public String getTo(){
        return to;
    }
    
    public void setCc(String cc){
        this.cc = cc;
    }
    
    public String getCc(){
        return cc;
    }
    
    public void setFromInData(String fromInData){
        this.fromInData = fromInData;
    }
    
    public String getFromInData(){
        return fromInData;
    }
    
    public void reset() {
        didSayHelo = false;
        this.from = null;
        this.didSpecifyMailFrom = false;
        this.recipients = new ArrayList<>();
        this.didSpecifyRcptTo = false;
        this.isWritingData = false;
        this.doneWritingData = false;
        this.data = null;
        this.date = null;
        this.subject = null;
        this.cc = null;
        this.fromInData = null;
        this.to = null;
    }
}
