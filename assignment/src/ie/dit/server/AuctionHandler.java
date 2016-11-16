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

  public void removeUser(String username) {
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
      userHandler.sendMessageToClient(msg);
    }
  }
}
