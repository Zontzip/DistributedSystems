package ie.dit.server;

import java.util.*;

public class Item {
  private String name;
  private int bid;

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

  public String toString() {
    return "Item [name=" + name + ", bid=" + bid + "]";
	}
}
