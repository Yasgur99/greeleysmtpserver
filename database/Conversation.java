import java.utils.*;

public class Conversation {
	/*
	*The record of a chain of emails to a certain person with a certain subject line.
	*Basically just an array
	*/
	private String senderAddress;
	private String senderName;
	private String recipientAddress;
	private String recipientName;
	ArrayList<Email> emailCollection = new ArrayList<Email>();

	public Conversation(String senderAddress, String senderName, String recipientAddress, String recipientName) {
		this.senderAddress = senderAddress;
		this.senderName = senderName;
		this.recipientAddress = recipientAddress;
		this.recpientName = recpientName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}
	public String getRecipientAddress() {
		return senderAddress;
	}
	public String getSenderName() {
		return senderName;
	}
	public String getRecipientName() {
		return recipientName;
	}

	public void addEmail(String subject, String text) {
		Email newEmail = new Email(senderAddress, recipientAddress, senderName, recipientName, subject, text);
		emailCollection.add(newEmail);
	}
}
