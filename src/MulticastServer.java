import entities.Protocols;
import entities.Room;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastServer {

  public static ArrayList<Room> Rooms = new ArrayList<Room>();

  public static void main(String args[]) {
    // args  prov� o conte�do da mensagem e o endere�o  do grupo multicast (p. ex. "228.5.6.7")

    MulticastSocket mSocket = null;
    String message = null;
    try {
      InetAddress groupIp = InetAddress.getByName(args[0]);
      mSocket = new MulticastSocket(6789);

      byte[] buffer = new byte[1000];
      while (true) {
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        message = new String(request.getData()).trim();
        System.out.println("Servidor: recebido \'" + message + "\'.");
        decodeRequest(message, request);
      }
    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    } finally {
      if (mSocket != null) mSocket.close();
    }
  }

  public static void decodeRequest(String message, DatagramPacket request) {
    String[] sliptedCommand = message.split(" ", 2);
	String protocolCommand =  sliptedCommand[0];

	switch (protocolCommand) {
		case Protocols.createRoom:
			Rooms.add(new Room(sliptedCommand[1]));
			break;
	
		default:
			break;
	}
  }
}
