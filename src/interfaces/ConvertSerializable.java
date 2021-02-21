package interfaces;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ConvertSerializable<T> {

  public byte[] serialize(T obj) throws IOException {
    try (
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bos)
    ) {
      out.writeObject(obj);
      return bos.toByteArray();
    }
  }

  @SuppressWarnings("unchecked")
  public T deserialize(byte[] data) throws IOException, ClassNotFoundException {
    try (
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream in = new ObjectInputStream(bis)
    ) {
      return (T) in.readObject();
    }
  }
}
