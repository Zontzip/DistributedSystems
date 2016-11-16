package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {

  private Socket client;
  private Scanner input;
  private PrintWriter output;

  private static AuctionHandler auctionHandler;
  private UserHandler userHandler;
  private static Queue<String> inbox;
  private static Queue<String> outbox;
  private static List<ClientHandler> clientList;

  /**
   * Constructor takes the new socket to run in a thread, the auction handler
   * which handles the auction,a user handler which has a 1-1 relationship with
   * client handler. Then, adds the users handle to the auctions list of users.
  */
  public ClientHandler(Socket socket) {
    this.client = socket;
    this.auctionHandler = AuctionHandler.getInstance();
    this.userHandler = userHandler;
    this.auctionHandler.addUserHandler(this.userHandler);
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

        while(!inbox.isEmpty()) {
          String msg = inbox.remove();
          this.userHandler.handleMessageFromBuffer(msg);
        }

        while(!outbox.isEmpty()) {
          String msg = outbox.remove();
          output.println(msg);
        }
      } while (true);
  }

  public void addToOutboxQueue(String msg) {
    outbox.add(msg);
  }
}
