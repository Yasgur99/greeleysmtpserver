package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author yasgur99
 */
public class ClientHandler implements Runnable {

	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;

	public ClientHandler(Socket connection) {
    	this.connection = connection;
	}

	@Override
	public void run() {
    	setupStreams();
    	this.out.write("Connection with server has been made");
    	readClientMessage();
    	closeConnection();
	}

	private void setupStreams() {
    	try {
        	this.in = new BufferedReader(
                	new InputStreamReader(
                        	this.connection.getInputStream()));
    	} catch (IOException ex) {
        	System.out.println("IOException setting up input stream");
    	}

    	try {
        	this.out = new PrintWriter(
                	new OutputStreamWriter(
                        	this.connection.getOutputStream()));
    	} catch (IOException ex) {
        	System.out.println("IOException setting up output stream");
    	}
	}

	private void readClientMessage() {
    	try {
        	String line = in.readLine();
        	System.out.println("Client: " + line);
    	} catch (IOException ex) {
        	System.out.println("IOException reading the clients message");
    	}
	}

	private void closeConnection() {
    	try {
        	this.in.close();
        	this.out.write("Closing connection");
        	this.out.close();
        	this.connection.close();
    	} catch (IOException ex) {
        	System.out.println("Exception closing resources");
    	}
	}

}
