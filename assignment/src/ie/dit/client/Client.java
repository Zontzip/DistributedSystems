package ie.dit.client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  Socket socket;
  /**
   * Create seperate threads for keyboard input and server output. This prevents
   * the system having to wait for user input as the buffer fills.
   */
  public Client(Socket socket) {
    this.socket = socket;
    try {
      new Thread(new KeyboardInput(socket)).start();
      new Thread(new ServerResponseHandler(socket)).start();
    } catch(IOException e) {
      System.out.println("Error creating worker IO threads");
    }
  }

  private class KeyboardInput implements Runnable {
    private BufferedReader userInput;
    private DataOutputStream outputStream;

    KeyboardInput(Socket socket) throws IOException {
      userInput = new BufferedReader(new InputStreamReader(System.in));
      try {
        this.outputStream = new DataOutputStream(socket.getOutputStream());
      } catch (IOException e) {
        System.out.println("Comms error");
      }
    }
    /**
     * Run until the user quits.
     */
    public void run() {
      do {
        System.out.println("\nEnter a value: ");
        try {
          String msg = userInput.readLine();
          outputStream.writeUTF(msg);
          outputStream.flush();

          if (msg.equals("q") || msg.equals("Q")) {
            break;
          }
        } catch (IOException e) {
          System.out.println("Comms error");
        }
      } while(true);

      System.out.println("Goodbye!");
      System.exit(0);
    }
  }

  private class ServerResponseHandler implements Runnable {
    private DataInputStream inputStream;

    ServerResponseHandler(Socket socket) throws IOException {
      try {
        inputStream = new DataInputStream(socket.getInputStream());
      } catch (IOException e) {
        System.out.println("Comms error");
      }
    }

    public void run() {
      while(true) {
          try {
            String serverMessage = inputStream.readUTF();
            System.out.println(serverMessage);
          } catch (IOException e) {
            System.out.println("Comms error");
          }
      }
    }
  }
}
