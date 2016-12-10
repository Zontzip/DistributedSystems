package ie.dit.business;

import java.util.*;

public class Item {
  private String name;
  private int bid;
  private boolean sold = false;

  public Item(String name, int startingBid) {
    setName(name);
    setBid(startingBid);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getBid() {
    return this.bid;
  }

  public void setBid(int bid) {
    this.bid = bid;
  }

  public void markAsSold() {
    this.sold = true;
  }

  public boolean isSold() {
    return this.sold;
  }

  public String toString() {
    return "Item [name=" + name + ", bid=" + bid + "]";
	}
}
