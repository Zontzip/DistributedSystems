package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.Item;

/**
 * Handles item logic for the system.
 */
public class ItemHandler {
  private static ItemFactory itemFactory;
  private Item item;
  private String highestBidder;
  private int highestBid;

  public ItemHandler() {
    itemFactory = ItemFactory.getInstance();
    newItem();
    System.out.println(this.item.toString());
  }

  public void newItem() {
    this.item = itemFactory.getItem();
    setHighestBid(this.item.getBid());
  }

  public Item getItem() {
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

  public String getItemName() {
    return this.item.getName();
  }

  public void removeItem(Item item) {
    this.itemFactory.removeItem(item.getName());
  }

  public int getItemCount() {
    return this.itemFactory.remainingItems();
  }
}
