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

    List<Item> auctionItems = new ArrayList<Item>();
    auctionItems.add(new Item("Bicycle", 100));
    auctionItems.add(new Item("Car", 500));
    auctionItems.add(new Item("Boat", 9000));
    auctionItems.add(new Item("Wheelbarrow", 20));
    auctionItems.add(new Item("House", 50000));
    ItemHandler itemHandler = new ItemHandler(auctionItems.get(0));

    do {
      try {
        // Wait for client...
        Socket client = serverSocket.accept();

        System.out.println("\nA client has connected.");

        Thread handler = new Thread(new ClientHandler(client, itemHandler));
        handler.start();
      }
      catch (IOException err) {
        err.printStackTrace();
      }
    } while(true);
  }
}

class ClientHandler implements Runnable {

  private Socket client;
  private Scanner input;
  private PrintWriter output;
  private ItemHandler itemHandler;
  private String msg;

  public ClientHandler(Socket socket, ItemHandler itemHandler) {
    this.client = socket;
    this.itemHandler = itemHandler;
  }

  public void run() {
    try {
      input = new Scanner(client.getInputStream(), "UTF8");
      output = new PrintWriter(client.getOutputStream(), true);

      output.println(itemHandler.getItemName());
      output.println(itemHandler.getItemBid());

      System.out.println("\nWaiting for client input...");

      do {
        msg = input.nextLine();
        System.out.println(Thread.currentThread().getName() + " input: " + msg);

        if (isNumeric(msg)) {
          int bid = Integer.parseInt(msg);

          if (bid > itemHandler.getItemBid()) {
            output.println("You are the highest bidder");
            itemHandler.setItemBid(bid);
          } else {
            output.println("Bid is less then the highest bid");
          }
        } else {
          if (msg.equals("I") || msg.equals("i")) {
            output.println("The current bid is " + itemHandler.getItemBid());
          } else if (msg.equals("q") || msg.equals("Q")) {
            System.out.println("Client disconnected");
            client.close();
            break;
          }
        }
      } while (true);
    } catch(IOException ioEx) {
        ioEx.printStackTrace();
    }
  }

  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?");
  }
}
