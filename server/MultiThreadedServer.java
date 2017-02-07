package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yasgur99
 */
public class MultiThreadedServer {
	private ServerSocket serverSocket;
	private int port;
	private boolean running;

	public MultiThreadedServer(int port) {
    	this.port = port;
    	this.running = false;
	}

	public void start() {
    	this.running = true;
    	initServer();
    	makeConnection();
    	closeDownServer();
	}

	private void initServer() {
    	try {
        	this.serverSocket = new ServerSocket(port);
    	} catch (IOException ex) {
        	System.out.println("Could not create serverSocket on port " + port);
    	}
	}

	private void makeConnection() {
    	while (running) {
        	try {
            	Socket newClient = this.serverSocket.accept();
            	new Thread(new ClientHandler(newClient)).start();
        	} catch (IOException ex) {
            	System.out.println("IOException accepting a connection");
        	}
    	}
	}

	private void closeDownServer() {
    	try {
        	this.serverSocket.close();
        	System.out.println("Server shutdown");
    	} catch (IOException ex) {
        	System.out.println("IOException closing serverSocket");
    	}

	}

	public void shutdown() {
    	this.running = false;
	}

}
