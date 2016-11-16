package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;
import ie.dit.shared.Message;

public class ClientHandler implements Runnable {

  private Socket client;
  private Scanner input;
  private PrintWriter output;
  private ItemHandler itemHandler;
  private String recieved;

  private static List<String> messageList;
  private static List<ClientHandler> clientList;

  public ClientHandler(Socket socket, ItemHandler itemHandler) {
    this.client = socket;
    this.itemHandler = itemHandler;
    this.clientList = AuctionServer.getClientList();
    this.messageList = new ArrayList<String>();
  }

  public void run() {
    try {
      input = new Scanner(client.getInputStream(), "UTF8");
      output = new PrintWriter(client.getOutputStream(), true);

      output.println(itemHandler.getItemName());
      output.println(itemHandler.getItemBid());

      System.out.println("\nWaiting for client input...");

      do {
        if (!messageList.isEmpty()) {
          String message = messageList.get(messageList.size() - 1);
          messageList.remove(messageList.size() - 1);
          output.println(message);
        }

        recieved = input.nextLine();
        String[] parts = recieved.split(",");
        Message msg = new Message(parts[0], parts[1]);

        System.out.println("User: " + msg.getUsername() + " bidded: " +
              msg.getValue() + " on item: " + itemHandler.getItemName());

        if (isNumeric(msg.getValue())) {
          int bid = Integer.parseInt(msg.getValue());

          if (bid > itemHandler.getItemBid()) {
            output.println("You are the highest bidder");
            itemHandler.setItemBid(bid);
            itemHandler.setUsername(msg.getUsername());
            notifyClients("User: " + msg.getUsername() + " bidded: " +
                  msg.getValue() + " on item: " + itemHandler.getItemName());
          } else {
            output.println("Bid is less then the highest bid");
          }
        } else {
          if (msg.getValue().equals("I") || msg.getValue().equals("i")) {
            output.println("The current bid is " + itemHandler.getItemBid());
          } else if (msg.getValue().equals("q") || msg.getValue().equals("Q")) {
            System.out.println("Client disconnected");
            client.close();
            break;
          } else {
            output.println("Value not recognized");
          }
        }
      } while (true);
    } catch(IOException ioEx) {
        ioEx.printStackTrace();
    }
  }

  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }

  public void sendMessage(String msg) {
    output.println(msg);
  }

  public void notifyClients(String msg) {

    for(ClientHandler client : clientList) {
      client.sendMessage(msg);
    }
  }

  public void updateItem() {
    for(ClientHandler client : clientList) {
      List<Item> auctionsItems = AuctionServer.getItemList();

      if (!auctionsItems.isEmpty()) {
        client.itemHandler.setItem(auctionsItems.get(0));
        notifyClients("New auction started");
      } else {
        notifyClients("Auction has ended");
      }
    }
  }
}
