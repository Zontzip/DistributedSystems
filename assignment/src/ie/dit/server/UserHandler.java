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
  Socket socket;
  private ClientHandler clientHandler;
  private String username;
  private DataOutputStream outputStream;

  public UserHandler(ClientHandler clientHandler) {
    this.socket = clientHandler.client;
    this.clientHandler = clientHandler;
    setUserName(generateRandomName());
    System.out.println(getUserName());
    try {
      outputStream = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {}
  }

  public String getUserName() {
    return this.username;
  }

  public void setUserName(String username) {
    this.username = username;
  }

  public void sendMessage(String msg) {
    try {
      outputStream.writeUTF(msg);
      outputStream.flush();
    } catch (IOException e) {}
  }

  public void sendMessageToGroup(String msg) {
    this.clientHandler.auctionHandler.messageClients(msg);
  }

  /**
   * If a message is numeric, it's considered a bid. Else, if it matches one of
   * the commands, the command is executed.
   */
  public void handleMessage(String msg) {
    int currentBid = getHighestBid();

    System.out.println("HEY!!");

    if (isNumeric(msg)) {
      int bid = Integer.parseInt(msg);

      if (bid > currentBid) {
        sendMessage("You are the highest bidder");
        this.clientHandler.auctionHandler.setHighestBid(bid);
        this.clientHandler.auctionHandler.setHghestBidder(getUserName());

        sendMessageToGroup("User: " + getUserName() + " bidded: " +
              msg + " on item: " + this.clientHandler.auctionHandler
              .getItemName());
      } else {
        sendMessage("Bid is less then the highest bid");
      }
    } else {
        if (msg.equals("I") || msg.equals("i")) {
          sendMessage("The current bid is " + currentBid);
        } else if (msg.equals("q") || msg.equals("Q")) {
          System.out.println("Client disconnected");
          sendMessage("Goodbye!");
          clientHandler.disconnectClient();
        } else {
          sendMessage("Value not recognized");
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
    String greeting = ("\n*****************************************************" +
                        "\nYou have entered the Auction - " + getUserName() +
                        "\n\nCurrent item: " + getItem() +
                        "\nCurrent bid: " + String.valueOf(getHighestBid()) +
                        "\n\nPlace a numeric bid that is greater then the current bid. " +
                        "\nI - Get current bid info " +
                        "\nQ - Quit" +
                        "\n*****************************************************");
    sendMessage(greeting);
  }

  public String getItem() {
    return this.clientHandler.auctionHandler.getItemName();
  }

  public int getHighestBid() {
    return this.clientHandler.auctionHandler.getHighestBid();
  }
}
