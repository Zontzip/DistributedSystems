/*
  Author: Alex Kiernan

  Description :
  Manages the users interaction with the server including all input and output

*/

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientThreadHandler implements Runnable {

  private Socket socket;
  private int PORT;
  private InetAddress host;
  private PrintWriter output;
  private Scanner networkInput;
  private Scanner userInput;
  private int currentTime;

  public ClientThreadHandler (InetAddress host, int PORT) {
    this.host = host;
    this.PORT = PORT;
  }

  public void run() {
    try {
      socket = new Socket(host, PORT);

      output = new PrintWriter(socket.getOutputStream(), true);
      networkInput = new Scanner(socket.getInputStream());

      // Look for user input
      userInput = new Scanner(System.in);

      // Show that we succesfully opened input and output streams
      System.out.println("Opened input and output streams");

    } catch (IOException e) {
      e.printStackTrace();
    }

    // Give the user instructions
    System.out.println("\n*****************************************************");
    System.out.println("You have entered the Auction.\n");
    System.out.println("Current item: " + networkInput.nextLine().toString());
    System.out.println("Current bid: " +  networkInput.nextLine().toString());
    System.out.println("\nPlace a bid that is greater then the current bid. " +
                       "\n1 - Get current bid " +
                       "\nQ - Quit");
    System.out.println("*****************************************************");

    String message = "";
    String response = "";
    // Wait for user input
    do {
      message = userInput.nextLine();
      output.println(message);

      response = networkInput.nextLine().toString();
      System.out.println("Response to ("+ message +"): " + response);
    } while(!message.equals("q") && !message.equals("Q"));

    System.out.println("\nGoodbye");
    System.exit(1);
  }
}
