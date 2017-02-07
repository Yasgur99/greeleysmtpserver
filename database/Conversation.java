import java.utils.*;
import java.io.*;

public class Conversation {
	/*
	*The record of a chain of emails to a certain person with a certain subject line.
	*Basically just an array
	*/
	private String[] senderAddress;
	private String[] senderName;
	private String[] recipientAddress;
	private String[] recipientName;
	private String[] fileName;
	ArrayList<Email> emailCollection = new ArrayList<Email>();

	public Conversation(String[] fileName, String[] senderAddress, String[] senderName, String[] recipientAddress, String[] recipientName) {
		this.senderAddress = senderAddress;
		this.senderName = senderName;
		this.recipientAddress = recipientAddress;
		this.recpientName = recpientName;
		this.fileName = fileName;

		try {
			FileInputStream fileIn = new FileInputStream("/tmp/"+fileName+".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			this = (Conversation) in.readObject();
			in.close();
			fileIn.close();
		} catch(ClassNotFoundException c) {
			c.printStackTrace();
		}

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

	public void addEmail(String[] subject, String[] text) {
		Email newEmail = new Email(senderAddress, recipientAddress, senderName, recipientName, subject, text);
		emailCollection.add(newEmail);
		try {
			FireOutputStream fileOut = new FileOutputStream("tmp/"+fileName+".ser");
			ObjectOutputStream out =new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}
}
