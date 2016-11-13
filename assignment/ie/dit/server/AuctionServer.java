/*
  Take a client connection, create a new instance of ItemHandler and generate a
  ClientHandler and pass a reference to the ItemHandler
*/
import java.io.*;
import java.net.*;
import java.util.*;

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

    ItemHandler itemHandler = new ItemHandler();

    do {
      try {
        // Wait for client...
        Socket client = serverSocket.accept();

        System.out.println("\nA client has connected.\n");

        // Create a thread to handle communication with this client and pass the
        // constructor for this thread a regerence to the relevant socket
        Thread handler = new Thread(new ClientHandler(client, itemHandler));
        handler.start();
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }
}
