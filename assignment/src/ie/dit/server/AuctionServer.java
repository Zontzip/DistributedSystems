package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;
import ie.dit.shared.Message;

public class AuctionServer {

  private static ServerSocket serverSocket;
  private static final int PORT = 8080;

  public static void main(String[] args) throws IOException {

    try {
      serverSocket = new ServerSocket(PORT);
      System.out.println("Server has started.");
    }
    catch (IOException ioEx) {
      System.out.println("\nUnable to set up port!");
			System.exit(1);
    }

    List<Item> auctionItems = new ArrayList<Item>();
    auctionItems.add(new Item("Bicycle", 100));
    auctionItems.add(new Item("Car", 500));
    auctionItems.add(new Item("Boat", 9000));
    auctionItems.add(new Item("Wheelbarrow", 20));
    auctionItems.add(new Item("House", 50000));
    ItemHandler itemHandler = new ItemHandler(auctionItems.get(0));

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
}
