package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionServer {

  private static ServerSocket serverSocket;
  private static final int PORT = 8080;

  private static AuctionHandler auctionHandler;
  private static List<ClientHandler> clientList;

  public static void main(String[] args) throws IOException {

    try {
      serverSocket = new ServerSocket(PORT);
      System.out.println("Server has started.");
    }
    catch (IOException ioEx) {
      System.out.println("\nUnable to set up port!");
			System.exit(1);
    }

    auctionHandler = AuctionHandler.getInstance();
    clientList = new ArrayList<ClientHandler>();

    do {
      try {
        Socket client = serverSocket.accept();

        ClientHandler handler = new ClientHandler(client);
        Thread thread = new Thread(handler);
        thread.start();

        clientList.add(handler);
        System.out.println("\nA client has connected.");
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }
}
