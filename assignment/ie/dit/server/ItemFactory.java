/*
  Author: Alex Kiernan

  Description
  Creates an ArrayList, each item within the arraylist is a HashMap which represents and item and its properties.
  An example of its structure can be seen below

  ArrayList[0] {
    Map.contents {
      "name" : "example"
      "bid"  : "0"
    }
  }

  Functions :
  - ItemFactory() : Default constructor
  - newAuction() : Returns an ArrayList of HashMaps containing all of the items to be auctioned and thier properties
*/

import java.util.*;

public class ItemFactory {

  public ItemFactory () {
    // Default constructor
  }

  public ArrayList<Map> newAuction() {
    // holds the list of items for auction and their values
    ArrayList auctionItems = new ArrayList<Map>();
    // holds the information of a single item in the auction
    HashMap item = new HashMap();

    // fill the auction with items
    // Set the name and starting bid
    item.put("name", "Antique Chair");
    item.put("bid", "10");
    auctionItems.add(item);

    item = new HashMap();
    item.put("name", "Antique Dresser");
    item.put("bid", "500");
    auctionItems.add(item);

    item = new HashMap();
    item.put("name", "Oak Table");
    item.put("bid", "0");
    auctionItems.add(item);

    item = new HashMap();
    item.put("name", "Victorian House");
    item.put("bid", "500000");
    auctionItems.add(item);

    // Return the list of items
    return auctionItems;
  }

}
