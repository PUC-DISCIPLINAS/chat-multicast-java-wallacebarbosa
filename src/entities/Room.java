package entities;

import java.net.InetAddress;
import java.util.*;

public class Room {

  private ArrayList<Client> clients = null;
  private String name;
  private InetAddress address = null;

  public Room(String name) {
    this.clients = new ArrayList<Client>();
    this.setName(name);
    
  }

  public void addClient(Client user) { // pode ser adicionado algum tratamento
    clients.add(user);
  }

  public void removeClient(Client user) { // pode ser adicionado algum tratamento
    clients.remove(user);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
