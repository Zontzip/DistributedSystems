package ie.dit.shared;

public class Message {
  private String username;
  private String value;

  public Message(String username, String value) {
    setUsername(username);
    setValue(value);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String toString() {
    return "Message [username=" + username + ", value=" + value + "]";
	}
}
