package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;
import ie.dit.shared.Message;

public class AuctionServer {

  private static ServerSocket serverSocket;
  private static final int PORT = 8080;
  private static List<Item> auctionItems;
  public static ItemHandler itemHandler;

  public static void main(String[] args) throws IOException {

    try {
      serverSocket = new ServerSocket(PORT);
      System.out.println("Server has started.");
    }
    catch (IOException ioEx) {
      System.out.println("\nUnable to set up port!");
			System.exit(1);
    }

    addItem("Bicycle", 100);
    addItem("Car", 500);
    addItem("Boat", 9000);
    addItem("Wheelbarrow", 20);
    addItem("House", 50000);

    try {
      itemHandler = new ItemHandler(auctionItems.get(0));
    } catch (Exception e) {
      System.err.println("Couldn't initialise ItemHandler: " + e.getMessage());
    }


    do {
      try {
        // Wait for client...
        Socket client = serverSocket.accept();

        System.out.println("\nA client has connected.");

        Thread handler = new Thread(new ClientHandler(client, itemHandler));
        handler.start();
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }

  public static synchronized void addItem(String name, int price) {
    if (auctionItems == null) {
        auctionItems = new ArrayList<Item>();
    } else {
      auctionItems.add(new Item(name, price));
    }
  }
}
