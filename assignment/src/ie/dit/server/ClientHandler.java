package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {

  private Socket client;
  private Scanner input;
  private PrintWriter output;

  public static AuctionHandler auctionHandler;
  private UserHandler userHandler;
  private static Queue<String> inbox;
  private static Queue<String> outbox;

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

    this.inbox = new LinkedList<>();
    this.outbox = new LinkedList<>();
    try {
      this.input = new Scanner(client.getInputStream(), "UTF8");
      this.output = new PrintWriter(client.getOutputStream(), true);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Scan inbox and outbox for new messages. Messages can be added to outbox (to
   * be delivered to the client) by the user handler and added to inbox by the
   * auction server.
   */
  public void run() {

      do {
        String recieved = input.nextLine();
        inbox.add(recieved);
        System.out.println("Message added to queue");

        while(!inbox.isEmpty()) {
          String msg = inbox.remove();
          System.out.println("Message: " + msg);
          userHandler.handleMessage(msg);
        }

        while(!outbox.isEmpty()) {
          String msg = outbox.remove();
          output.println(msg);
        }
      } while (true);
  }

  public void addToOutbox(String msg) {
    outbox.add(msg);
  }

  public void disconnectClient() {
    this.auctionHandler.removeClient(this.userHandler.getUserName());
    try {
      client.close();
    } catch (IOException e) {
      System.out.println("Could not disconnet client: " + e);
    }
  }
}
