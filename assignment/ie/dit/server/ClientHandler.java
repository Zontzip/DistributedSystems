/*
  Author: Alex Kiernan

  - Manage interaction with a client
  - Allow clients to submit a bid
    - Say hello
    - Tell the user what item is currently on auction
    - Take a bid
*/

import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler implements Runnable {

  private Socket client;
  private ItemHandler itemHandler;
  private Scanner input;
  private PrintWriter output;
  private String currentBid;

  public ClientHandler(Socket client, ItemHandler itemHandler) {
    // client is referenced by socket number
    this.client = client;
    this.itemHandler = itemHandler;
  }

  public void run() {
    try {
      input = new Scanner(client.getInputStream(), "UTF8");
      output = new PrintWriter(client.getOutputStream(), true);

      System.out.println("Input/Output streams opened");

      output.println(itemHandler.getCurrentItem());
      output.println(itemHandler.getCurrentBid());

      String recieved;
      currentBid = itemHandler.getCurrentBid();

      // Infinite loop
      do {
        System.out.println("\nWaiting for client input...");
        // Get client input
        recieved = input.nextLine();
        System.out.println("Client input: " + recieved);
        try {
          // Return the current bid to the user
          if(recieved.equals("1")) {
            output.println("The current bid is " + currentBid);
          }
          else if (Integer.parseInt(recieved) > Integer.parseInt(currentBid)){
              currentBid = itemHandler.getCurrentBid();
              output.println(currentBid);
          } else {
              output.println("Your bid is too low, the current bid is: " + currentBid );
          }
        } catch (NumberFormatException e) {
          System.out.println("Caught number format error");
          System.out.println(e);
          output.println("Please input a number");
        }
      } while (!recieved.equals("q") && !recieved.equals("Q"));
      System.out.println("User disconnected");
    } catch(IOException ioEx) {
        ioEx.printStackTrace();
    }
  }
}
