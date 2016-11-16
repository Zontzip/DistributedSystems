package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ItemHandler extends Thread {
  private Item currentItem;
  private String highestBidder;
  private Timer timer;
  private static int countdown;
  private static List<ClientHandler> clientList;

  public ItemHandler(Item currentItem) {
    setItem(currentItem);
    timer = new Timer();
    setTime();
    // Run timer task every 10 seconds, with first execution after 0 seconds
    timer.schedule(new UpdateTask(), 0, 10000);
    this.clientList = AuctionServer.getClientList();
  }

  class UpdateTask extends TimerTask {
    public void run() {
      if (countdown <= 0) {
        timer.cancel();
        notifyClients("Auction has ended");
        finalizeAuction();
      } else {
        notifyClients("There is " + countdown + " secs left");
        countdown = countdown - 10;
      }
    }
  }

  public void setItem(Item item) {
    this.currentItem = item;
  }

  public String getItemName() {
    return currentItem.getName();
  }

  public int getItemBid() {
    return currentItem.getBid();
  }

  public void setItemBid(int bid) {
    this.currentItem.setBid(bid);
  }

  public void setUsername(String bidder) {
    this.highestBidder = bidder;
  }

  public String getUsername() {
    return this.highestBidder;
  }

  public void setTime() {
    this.countdown = 60;
  }

  public int getTime() {
    return this.countdown;
  }

  public void notifyClients(String msg) {

    for(ClientHandler client : clientList) {
      client.sendMessage(msg);
    }
  }

  public void finalizeAuction() {
    if (getUsername() == null) {
      notifyClients("Item not sold");
    } else {
      notifyClients("Item was sold to: " + getUsername());
    }
  }

}
