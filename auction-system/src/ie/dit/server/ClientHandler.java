package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {

  public Socket client;
  private DataInputStream inputStream;
  private DataOutputStream outputStream;

  public static AuctionHandler auctionHandler;
  private UserHandler userHandler;

  /**
   * Constructor takes the new socket to run in a thread, the auction handler
   * which handles the auction,a user handler which has a 1-1 relationship with
   * client handler. Then, adds the users handle to the auctions list of users.
  */
  public ClientHandler(Socket socket) {
    this.client = socket;
    this.auctionHandler = AuctionHandler.getInstance();
    this.userHandler = new UserHandler(this);
    this.auctionHandler.addUserHandler(this.userHandler);
    /**
     * Notify client of auction info
     */
    this.userHandler.sendMessage(this.userHandler.generateGreeting());
    try {
      this.inputStream = new DataInputStream(socket.getInputStream());
    } catch (IOException e) {}
  }

  /**
   * Scan inbox and outbox for new messages. Messages can be added to outbox (to
   * be delivered to the client) by the user handler and added to inbox by the
   * auction server. ---- REMOVED DUE TO BROKEN FUNCTIONALITY
   */
  public void run() {
      do {
        try {
          String inputMessage = inputStream.readUTF();
          userHandler.handleMessage(inputMessage);
        } catch (IOException e) {}
      } while (true);
  }

  public void disconnectClient() {
    this.auctionHandler.removeClient(this.userHandler.getUserName());
    try {
      client.close();
    } catch (IOException e) {
      System.out.println("Could not disconnect client: " + e);
    }
  }
}
