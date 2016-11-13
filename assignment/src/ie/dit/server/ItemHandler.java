package ie.dit.server;

import java.util.*;

public class ItemHandler extends Thread {

  Item currentItem;
  String highestBidder;

  public ItemHandler(Item currentItem) {
    setItem(currentItem);
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

}
