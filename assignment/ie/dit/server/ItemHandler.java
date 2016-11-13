/*
  Author: Alex Kiernan

  This class handles all of the interactions between the "items" for auction
  and each of the clients

  Description
  - Creates an instance of ItemFactory
  -

  functions
  - ItemHandler() : constructor
  - bid(String newBid): Takes in the request for a bid
  - getCurrentItem() : Returns the item currently up for auction
  - getCurrentBid() : Returns the current bid on the item currently for sale
  - Functions : nextItem, removeItems and listItems have been removed
*/

import java.util.*;

public class ItemHandler extends Thread {

  ItemFactory itemFactory;
  private HashMap<String, String> item;
  private List<Map> auctionItems;
  int  currentItem = 0;

  public ItemHandler () {
    // Default constructor
    this.itemFactory = new ItemFactory();
    this.auctionItems = itemFactory.newAuction();
  }

  // Set the bid of a new item
  public String bid (String newBid){
    if(Integer.parseInt(newBid) > (Integer)auctionItems.get(currentItem).get("bid")){
      auctionItems.get(currentItem).put("bid", Integer.parseInt(newBid));
      return "Bid Successful! Current bid " + newBid;
    } else {
      return "Bid Unsucessful. Current bid is "
      + auctionItems.get(currentItem).get("bid").toString();
    }
  }

  // Get the name of the current item
  public String getCurrentItem() {
    return auctionItems.get(currentItem).get("name").toString();
  }

  // Get the top bid on the current item
  public String getCurrentBid() {
    return auctionItems.get(currentItem).get("bid").toString();
  }
}
