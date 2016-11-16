package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.Item;

public class ItemFactory {
  private static ItemFactory itemFactory = null;
  private static List<Item> auctionItems;

  private ItemFactory() {
    auctionItems = new ArrayList<Item>();
    addItem("Bicycle", 100);
    addItem("Car", 500);
    addItem("Boat", 9000);
    addItem("Wheelbarrow", 20);
    addItem("House", 50000);
  }

  public static synchronized ItemFactory getInstance() {
    if(itemFactory == null) {
      itemFactory = new ItemFactory();
    }
    return itemFactory;
  }

  public static synchronized void addItem(String name, int price) {
    auctionItems.add(new Item(name, price));
  }

  public static synchronized Item getItem() {
    int randomIndex = randInt(0, auctionItems.size());
    return (auctionItems.get(randomIndex));
  }

  public static synchronized void removeItem(String name) {
    Iterator<Item> it = auctionItems.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (item.getName().equals(name)) {
        it.remove();
      }
    }
  }

  public static int randInt(int min, int max) {
    Random rand = new Random();
    int randomNum = rand.nextInt((max - min)) + min;
    return randomNum;
  }
}
