package entities;

import interfaces.ConvertSerializable;
import java.io.Serializable;

public class Client
  extends ConvertSerializable<Client>
  implements Serializable {

  private static final long serialVersionUID = 1L;
  private String name;

  public Client(String name) {
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
