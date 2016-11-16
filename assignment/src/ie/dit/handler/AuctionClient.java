package ie.dit.handler;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionClient {

  private static InetAddress host;
  private static final int PORT = 8080;
  private static Integer currentTime;
  private static Socket link;
  private static PrintWriter output;
  private static Scanner networkInput;
  private static Scanner userInput;

  public static void main(String[] args) {

    // Try to establish a connection with the server on localhost
    try {
      host = InetAddress.getLocalHost();
    }
    // Else exit the application
    catch (UnknownHostException uhEx){
      System.out.println("\nHost ID not found");
      System.exit(1);
    }
    Thread clientThread = new Thread(new ClientThreadHandler(host, PORT));
    clientThread.start();
  }
}
