import entities.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MulticastClient {

  public static Scanner read = new Scanner(System.in);

  public static void main(String args[]) {
    // args fornecem a mensagem e o endere�o do servidor.
    DatagramSocket aSocket = null;
    int serverPort = 6789;
    String message;

    try {
      InetAddress aHost = InetAddress.getByName(args[0]);

      System.out.println("Digite seu nome: ");
      String name = read.next();

      Client client = new Client(name);
      byte[] clientInByte = convertToBytes(client);

      System.out.println("Commands:. \"*create-room*  value\"");
      System.out.println();
      System.out.printf(
        "%-25s %-25s %-25s",
        "[ create-Room ]",
        "[ list-Rooms ]",
        "[ getIn-Room ]"
      );
      System.out.println();
      System.out.printf(
        "%-25s %-25s %-25s",
        "[ room-Out ]",
        "[ sendM ]",
        "[ list-Members ]"
      );
      System.out.println();
      System.out.println();

      while (true) {
        System.out.printf("%-20s", "Digite um comando: ");
        String command = read.next() + " " + name;
        aSocket = new DatagramSocket();

        DatagramPacket request = new DatagramPacket(
          command.getBytes(),
          command.length(),
          aHost,
          serverPort
        );
        aSocket.send(request);

        byte[] buffer = new byte[1000];

        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(reply);

        handleResponde(reply);
      }
    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    } finally {
      if (aSocket != null) aSocket.close();
    }
  }

  private static byte[] convertToBytes(Object object) throws IOException {
    try (
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bos)
    ) {
      out.writeObject(object);
      return bos.toByteArray();
    }
  }

  public static void handleResponde(DatagramPacket reply) {
    String message = new String(reply.getData()).trim();

    System.out.println("Resposta: " + message);
  }
}
