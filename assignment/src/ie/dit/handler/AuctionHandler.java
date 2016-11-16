package ie.dit.handler;

import java.io.*;
import java.net.*;
import java.util.*;

public class AuctionHandler {
  private static AuctionHandler auctionHandler = null;

  private static ItemHandler itemHandler;


  private AuctionHandler() {

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
}
