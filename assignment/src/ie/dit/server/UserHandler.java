package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.*;

/**
 * Handles logic between the client and the server. If a user wants to send a
 * message, they can do so through one these methods. Also handles sending of
 * update data to client screen.
 */
public class UserHandler {
  private ClientHandler clientHandler;
  private String username;

  public UserHandler(ClientHandler clientHandler) {
    this.clientHandler = clientHandler;
    setUserName(generateRandomName());
    System.out.println(getUserName());
  }

  public String getUserName() {
    return this.username;
  }

  public void setUserName(String username) {
    this.username = username;
  }

  public void sendMessageToClient(String msg) {
    this.clientHandler.addToOutbox(msg);
  }

  public void sendMessageToGroup(String msg) {
    this.clientHandler.auctionHandler.messageClients(msg);
  }

  /**
   * If a message is numeric, it's considered a bid. Else, if it matches one of
   * the commands, the command is executed.
   */
  public void handleMessage(String msg) {
    int currentBid = this.clientHandler.auctionHandler.getHighestBid();

    if (isNumeric(msg)) {
      int bid = Integer.parseInt(msg);

      if (bid > currentBid) {
        sendMessageToClient("You are the highest bidder");
        this.clientHandler.auctionHandler.setHighestBid(bid);
        this.clientHandler.auctionHandler.setHghestBidder(getUserName());

        sendMessageToGroup("User: " + getUserName() + " bidded: " +
              msg + " on item: " + this.clientHandler.auctionHandler
              .getItemName());
      } else {
        sendMessageToClient("Bid is less then the highest bid");
      }
    } else {
        if (msg.equals("I") || msg.equals("i")) {
          sendMessageToClient("The current bid is " + currentBid);
        } else if (msg.equals("q") || msg.equals("Q")) {
          System.out.println("Client disconnected");
          sendMessageToClient("Goodbye!");
          clientHandler.disconnectClient();
        } else {
          sendMessageToClient("Value not recognized");
        }
    }
  }

  public boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  public String generateRandomName() {
    Random r = new Random(); // just create one and keep it around
    String alphabet = "abcdefghijklmnopqrstuvwxyz";

    final int N = 10;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < N; i++) {
        sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
    }
    String randomName = sb.toString();

    return randomName;
  }

  public void generateGreeting() {
    sendMessageToClient("Hi");
  }
}
