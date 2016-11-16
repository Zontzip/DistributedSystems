package ie.dit.business;

import java.util.*;

public class User {
  private String username;

  public User(String username) {
    setUsername(username);
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }
}
