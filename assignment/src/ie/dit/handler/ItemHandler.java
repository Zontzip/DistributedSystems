package ie.dit.handler;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.Message;
import ie.dit.business.Item;

public class ItemHandler {
  private static ItemFactory itemFactory;
  private Item item;
  private String highestBidder;
  private int highestBid;

  public ItemHandler() {
    itemFactory = ItemFactory.getInstance();
    setItem(itemFactory.getItem());
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Item getItem(Item item) {
    return this.item;
  }

  public void setHighestBidder(String bidder) {
    this.highestBidder = bidder;
  }

  public String getHighestBidder() {
    return this.highestBidder;
  }

  public void setHighestBid(int bid) {
    this.highestBid = bid;
  }

  public int getHighestBid() {
    return this.highestBid;
  }
}
