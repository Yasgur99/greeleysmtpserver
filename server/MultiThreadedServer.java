package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yasgur99
 */
public class MultiThreadedServer {

	private final static int POOL_SIZE = 50;

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
    	closeDownServer();
	}

	private void initServer() {
    	try(this.serverSocket = new ServerSocket(port)) {
        	makeConnection();
    	} catch (IOException ex) {
        	System.out.println("Could not create serverSocket on port " + port);
    	}
	}

	private void makeConnection() {
		ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
			while (running) {
        	try {
            	Socket newClient = this.serverSocket.accept();
            	Callable<Void> task = new ClientHandler(newClient);
							pool.submit(task);
        	} catch (IOException ex) {
            	System.out.println("Issue with connection with client...");
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
