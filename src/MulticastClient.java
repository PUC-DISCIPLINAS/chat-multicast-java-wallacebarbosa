import entities.Client;
import entities.Protocols;
import entities.RequestBody;
import entities.ResponseBody;
import enums.ResponseCodes;
import interfaces.ConvertSerializable;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MulticastClient {
  public static ConvertSerializable<RequestBody> serialReq = new ConvertSerializable<RequestBody>();
  public static ConvertSerializable<ResponseBody> serialRes = new ConvertSerializable<ResponseBody>();
  public static Scanner read = null;
  public static String name;

  public static void main(String args[]) {
    // args fornecem a mensagem e o endere�o do servidor.
    DatagramSocket aSocket = null;
    int serverPort = 6789;
    String message;

    try {
      InetAddress aHost = InetAddress.getByName("localhost");
      read = new Scanner(System.in);
      System.out.println("Digite seu nome: ");
      name = read.next();

      Client client = new Client(name);

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

      aSocket = new DatagramSocket();
      while (true) {
        System.out.printf("%-20s", "Digite um comando: ");
        read = new Scanner(System.in);
        String command = read.nextLine();

        RequestBody body = buildRequest(command);
        byte[] serial = body.serialize(body);
        DatagramPacket request = new DatagramPacket(
          serial,
          serial.length,
          aHost,
          serverPort
        );
        aSocket.send(request);

        byte[] buffer = new byte[1000];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(reply);
        System.out.println("chegou");
        handleResponde(reply);
      }
    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      if (aSocket != null) aSocket.close();
    }
  }

  private static RequestBody buildRequest(String command) throws IOException {
    String[] sliptedCommand = command.split(" ", 2);
    String protocol = sliptedCommand[0];
    String body = null;
    if (sliptedCommand.length > 1) body = sliptedCommand[1];

    RequestBody requestBody = new RequestBody(protocol, name, body);
    return requestBody;
  }

  public static void handleResponde(DatagramPacket reply)
    throws ClassNotFoundException, IOException {
	  if(reply != null) {
    ResponseBody responseBody = new ResponseBody();
    responseBody = responseBody.deserialize(reply.getData());
    String body = responseBody.getBody();
    switch (responseBody.getResponseMsg()) {
      case ENTER_ROOM:
    	  System.out.println("-------------------------------");
          System.out.println("Bem vindo envie uma mensagem!");
          System.out.println("-------------------------------");
        new Thread(
          () -> {
            try {
              listenServer(InetAddress.getByName(body));
            } catch (UnknownHostException | ClassNotFoundException e) {
              e.printStackTrace();
            }
          }
        )
          .start();
        break;
      case CREATE_SUCCESSFUL:
    	  System.out.println("-------------------------------");
          System.out.println("Sala Criada: "+ responseBody.getBody());
          System.out.println("-------------------------------");
       break;
      case LIST_ROOMS:
    	  System.out.println("-------------------------------");
          System.out.println(responseBody.getBody());
          System.out.println("-------------------------------");
          break;
      default:
        break;
    }
	  }
  }

  public static void listenServer(InetAddress ip)
    throws ClassNotFoundException {
    MulticastSocket aSocket = null;
    try {
      aSocket = new MulticastSocket(6789);
      aSocket.joinGroup(ip);

      while (true) {
        byte[] buffer = new byte[1000];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        aSocket.receive(reply);

        ResponseBody responseBody = serialRes.deserialize(reply.getData());
        String[] sliptedCommand = responseBody.getBody().split(" ", 2);
        String user = sliptedCommand[0];
        String msg = sliptedCommand[0];
        System.out.println(user + ": " + msg);
      }
    } catch (SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (aSocket != null) aSocket.close();
    }
    // Aqui voc� escreve o c�digo para ouvir o servidor atrav�s do grupo Multicast
  }

  public static ResponseBody receiveResponse(DatagramPacket reply)
    throws ClassNotFoundException, IOException {
    ResponseBody responseBody = serialRes.deserialize(reply.getData());
    return responseBody;
  }
}
