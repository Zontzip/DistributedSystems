package ie.dit.client;

import java.net.*;
import java.io.*;
import java.util.*;

public class AuctionClient  {

  private static Socket socket;
  private static InetAddress host;
  private static final int PORT = 8080;

  public static void main(String[] args) {
    try {
      try {
        host = InetAddress.getLocalHost();
      }
      catch (UnknownHostException uhEx){
        System.out.println("\nHost ID not found");
        System.exit(1);
      }

      socket = new Socket(host, PORT);
      /**
       * Create a distinct client object.
       */
      Client client = new Client(socket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
