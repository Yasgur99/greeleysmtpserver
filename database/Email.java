public class Email {

	/*
	*One email to someone
	*
	*Don't save the edited version of the text (with the annotations)
	*/


	private String[] senderAddress;
	private String[] recipientAddress;
	private String[] senderName;
	private String[] recipientName;
	private String[] subject;
	private String[] text;
	private Random uniqueID;

	public Email(String[] senderAddress, String[] recipientAddress, String[] senderName, String[] recipientName, String[] subject, String[] text) {
		this.senderAddress = senderAddress;
		this.recipientAddress = recipientAddress;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.subject = subject;
		this.text = text;

		uniqueID = new Random();
	}

	public String[] getSenderAddress() {
		return senderAddress;
	}
	public String[] getRecipientAddress() {
		return senderAddress;
	}
	public String[] getSenderName() {
		return senderName;
	}
	public String[] getRecipientName() {
		return recipientName;
	}
	public String[] getSubject() {
		return subject;
	}
	public String[] getText() {
		return text;
	}
	public String[] getID() {
		return uniqueID;
	}
}
