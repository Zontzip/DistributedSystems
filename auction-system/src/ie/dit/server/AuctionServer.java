package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionServer {

  private static ServerSocket serverSocket;
  private static final int PORT = 8080;

  private static AuctionHandler auctionHandler;

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

    /**
     * Each new client is assigned their own client handler. This will take care
     * of communication between the client and server in a new thread.
     */
    do {
      try {
        Socket client = serverSocket.accept();
        new Thread(new ClientHandler(client)).start();
        System.out.println("\nA client has connected.");
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }
}
