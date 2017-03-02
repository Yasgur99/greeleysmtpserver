package greeleysmtpserver;

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
  private Boolean running;
  private BufferedReader in;
  private PrintWriter out;

  public ClientHandler(Socket connection, Boolean running) {
    this.connection = connection;
    this.running = running;
  }

  @Override
  public void run() {
    setupStreams();
    readClientMessage();
    closeConnection();
  }

  private void setupStreams() {
    try {
      this.in = new BufferedReader(
              new InputStreamReader(
                      this.connection.getInputStream()));
      System.out.println("Input stream setup");
    } catch (IOException ex) {
      System.out.println("IOException setting up input stream");
    }

    try {
      this.out = new PrintWriter(
              new OutputStreamWriter(
                      this.connection.getOutputStream()));
          System.out.println("Output stream setup");
          this.out.write("Connection with server has been made");
    } catch (IOException ex) {
      System.out.println("IOException setting up output stream");
    }
  }

  private void readClientMessage() {
    try {
      String line = in.readLine();
      System.out.println("Client: " + line);
      //TODO: send input to central object
    } catch (IOException ex) {
      System.out.println("IOException reading the clients message");
    }
  }

  private void closeConnection() {
    try {
      this.in.close();
      this.out.write("Closing connection");
      this.out.flush();
      this.out.close();
      this.connection.close();
    } catch (IOException ex) {
      System.out.println("IOException closing resources");
    }
  }
}
