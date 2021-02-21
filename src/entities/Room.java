package entities;

import java.net.InetAddress;
import java.util.*;

public class Room {

  private ArrayList<String> clients = null;
  private String name;
  private InetAddress address = null;

  public Room(String name, InetAddress address) {
    this.clients = new ArrayList<String>();
    this.setName(name);
    this.address = address;
  }

  public void addClient(String user) { // pode ser adicionado algum tratamento
    clients.add(user);
  }

  public void removeClient(String user) { // pode ser adicionado algum tratamento
    clients.remove(user);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InetAddress getAddress() {
    return address;
  }

  public void setAddress(InetAddress address) {
    this.address = address;
  }

  public ArrayList<String> getClients() {
    return clients;
  }
}
