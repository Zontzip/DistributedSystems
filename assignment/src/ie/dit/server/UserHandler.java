package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.*;

public class UserHandler {
  private User user;
  private ClientHandler clientHandler;
  private String username;

  public UserHandler(ClientHandler clientHandler) {
    this.clientHandler = clientHandler;
    setUserName("Joe");
    //generateGreeting();
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
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
    this.clientHandler.auctionHandler.MessageClients(msg);
  }

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

  public String generateRandomname() {
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
    sendMessageToClient("\n*****************************************************" +
                        "\nYou have entered the Auction - " + getUserName() +
                        "\n\nCurrent item: " +
                        "\nCurrent bid: " +
                        "\n\nPlace a numeric bid that is greater then the current bid. " +
                        "\nI - Get current bid info " +
                        "\nQ - Quit" +
                        "\n*****************************************************");
  }
}
