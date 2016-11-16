package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;
import ie.dit.shared.Message;

public class AuctionServer {

  private static ServerSocket serverSocket;
  private static final int PORT = 8080;
  private static List<Item> auctionItems;
  private static List<ClientHandler> clientList;
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

    auctionItems = new ArrayList<Item>();
    clientList = new ArrayList<ClientHandler>();

    addItem("Bicycle", 100);
    addItem("Car", 500);
    addItem("Boat", 9000);
    addItem("Wheelbarrow", 20);
    addItem("House", 50000);

    try {
      itemHandler = new ItemHandler(auctionItems.get(0));
    } catch (Exception e) {
      System.err.println("Couldn't initialise ItemHandler: " + e.getMessage());
      System.exit(1);
    }

    do {
      try {
        // Wait for client...
        Socket client = serverSocket.accept();

        System.out.println("\nA client has connected.");

        ClientHandler handler = new ClientHandler(client, itemHandler);
        Thread thread = new Thread(handler);
        thread.start();
        addClient(handler);
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }

  public static synchronized void addItem(String name, int price) {
    auctionItems.add(new Item(name, price));
  }

  public static synchronized void addClient(ClientHandler client) {
    System.out.println("Client added to list");
    clientList.add(client);
  }

  public static List<ClientHandler> getClientList() {
    return clientList;
  }
}
