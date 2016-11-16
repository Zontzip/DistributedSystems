package ie.dit.handler;

import java.net.*;
import java.io.*;
import java.util.*;

import ie.dit.business.Message;
import ie.dit.business.Item;

public class ClientThreadHandler implements Runnable {

  private Socket socket;
  private int PORT;
  private InetAddress host;
  private PrintWriter output;
  private Scanner userInput;
  private String username;

  public ClientThreadHandler (InetAddress host, int PORT) {
    this.host = host;
    this.PORT = PORT;
  }

  public void run() {
    try {
      socket = new Socket(host, PORT);

      output = new PrintWriter(socket.getOutputStream(), true);

      // Look for user input
      userInput = new Scanner(System.in);

      ServerResponseHandler networkInput = new ServerResponseHandler(socket);
      new Thread(networkInput).start();

      System.out.println("Opened input and output streams");

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Enter your name: ");
    setUsername(userInput.nextLine());

    // Give the user instructions
    System.out.println("\n*****************************************************");
    System.out.println("You have entered the Auction - "+ username +"\n");
    System.out.println("Current item: ");
    System.out.println("Current bid: ");
    System.out.println("\nPlace a numeric bid that is greater then the current bid. " +
                       "\nI - Get current bid info " +
                       "\nQ - Quit");
    System.out.println("*****************************************************");

    String response = "";
    // Wait for user input
    do {
      System.out.println("\nEnter a value: ");
      Message message = new Message(this.username, userInput.nextLine());
      output.println(username + "," + message.getValue());

      if (message.getValue().equals("q") || message.getValue().equals("Q")) {
        break;
      }
    } while(true);

    System.out.println("\nGoodbye");
    System.exit(1);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  private class ServerResponseHandler implements Runnable {
    private Scanner networkInput;

    ServerResponseHandler(Socket socket) throws IOException {
      networkInput = new Scanner(socket.getInputStream());
    }

    public void run() {
      String serverMessage;
      while(true) {
          serverMessage = networkInput.nextLine().toString();
          System.out.println(serverMessage);
      }
    }
  }
}
