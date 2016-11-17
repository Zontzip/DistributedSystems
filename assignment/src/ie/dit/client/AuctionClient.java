package ie.dit.client;

import java.net.*;
import java.io.*;
import java.util.*;

import ie.dit.business.Message;
import ie.dit.business.Item;

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
      Client client = new Client(socket);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Client {
  public Client(Socket socket) {
    createThreads(socket);
  }

  public void createThreads(Socket socket) {
    try {
      ServerResponseHandler networkInput = new ServerResponseHandler(socket);
      new Thread(networkInput).start();

      KeyboardInput keyInput = new KeyboardInput(socket);
      new Thread(networkInput).start();
    } catch(IOException e) {
      System.out.println("Error creating worker IO threads");
    }
  }

  private class KeyboardInput implements Runnable {
    private Scanner userInput;
    private PrintWriter output;

    KeyboardInput(Socket socket) throws IOException {
      userInput = new Scanner(System.in);
    }

    public void run() {
      do {
        System.out.println("\nEnter a value: ");
        String msg = userInput.nextLine();
        output.println(msg);

        if (msg.equals("q") || msg.equals("Q")) {
          break;
        }
      } while(true);

      System.out.println("Goodbye!");
      System.exit(0);
    }
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
