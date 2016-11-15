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

  public ClientHandler(Socket socket, ItemHandler itemHandler) {
    this.client = socket;
    this.itemHandler = itemHandler;
  }

  public void run() {
    try {
      input = new Scanner(client.getInputStream(), "UTF8");
      output = new PrintWriter(client.getOutputStream(), true);

      output.println(itemHandler.getItemName());
      output.println(itemHandler.getItemBid());

      System.out.println("\nWaiting for client input...");

      do {
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

  public void setTimer(int seconds) {

  }
}
