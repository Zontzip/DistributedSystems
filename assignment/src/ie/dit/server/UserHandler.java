package ie.dit.server;

import java.io.*;
import java.net.*;
import java.util.*;

import ie.dit.business.User;

public class UserHandler {
  private User user;
  private ClientHandler clientHandler;
  private String username;

  public UserHandler(ClientHandler clientHandler) {
    this.clientHandler = clientHandler;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUserName() {
    return this.username;
  }

  public void setUserName(String username) {
    this.username = username;
  }

  public void sendMessageToClient(String msg) {
    this.clientHandler.addToOutbox(msg);
  }

  public void handleMessage(String msg) {
    System.out.println("Message recieved");
  }
}
