package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionHandler {
  private static AuctionHandler auctionHandler = null;
  private static ItemHandler itemHandler;

  private static List<UserHandler> userList;

  private AuctionHandler() {
    userList = new ArrayList<UserHandler>();
  }

  public static synchronized AuctionHandler getInstance() {
    if(auctionHandler == null) {
      auctionHandler = new AuctionHandler();
      itemHandler = new ItemHandler();
    }
    return auctionHandler;
  }

  public void setItemHandler(ItemHandler itemHandler) {
    this.itemHandler = itemHandler;
  }

  public ItemHandler getItemHandler() {
    return this.itemHandler;
  }

  public void addUserHandler(UserHandler userHandler) {
    userList.add(userHandler);
  }

  public void removeClient(String username) {
    Iterator<UserHandler> it = userList.iterator();
    while (it.hasNext()) {
      UserHandler userHandler = it.next();
      if (userHandler.getUserName().equals(username)) {
        it.remove();
      }
    }
  }

  public List<UserHandler> getClientList() {
    return this.userList;
  }

  public void MessageClients(String msg) {
    for(UserHandler userHandler : userList) {
      ListClients();
      userHandler.sendMessageToClient(msg);
    }
  }

  public void ListClients() {
    for(UserHandler userHandler : userList) {
      System.out.println(userHandler.getUserName());
    }
  }

  public int getHighestBid() {
    return this.itemHandler.getHighestBid();
  }

  public void setHighestBid(int bid) {
    this.itemHandler.setHighestBid(bid);
  }

  public String getHighestBidder() {
    return this.itemHandler.getHighestBidder();
  }

  public void setHghestBidder(String username) {
    this.itemHandler.setHighestBidder(username);
  }

  public String getItemName() {
    return this.itemHandler.getItemName();
  }
}
