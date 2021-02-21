import entities.Protocols;
import entities.RequestBody;
import entities.ResponseBody;
import entities.Room;
import enums.ResponseCodes;
import interfaces.ConvertSerializable;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class MulticastServer {

  public static ArrayList<Room> Rooms = new ArrayList<Room>();
  public static ConvertSerializable<RequestBody> serialReq = new ConvertSerializable<RequestBody>();
  public static ConvertSerializable<ResponseBody> serialRes = new ConvertSerializable<ResponseBody>();
  public static DatagramSocket mSocket = null;

  public static void main(String args[]) {
    // args  prov� o conte�do da mensagem e o endere�o  do grupo multicast (p. ex. "228.5.6.7")

 
    try {
      
      
      mSocket = new DatagramSocket(6789);
      System.out.println("Servidor: ouvindo porta 6789.");
      while (true) {
    	byte[] buffer = new byte[100000];
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        mSocket.receive(request);
        // message = new String(request.getData()).trim();

        byte[] message = decodeRequest(request);
        DatagramPacket messageOut = new DatagramPacket(
  		      message,
  		      message.length,
  		      request.getAddress(),
  		      request.getPort()
  		    );
    mSocket.send(messageOut);
      }
    } catch (SocketException e) {
      System.out.println("Socket: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("IO: " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (mSocket != null) mSocket.close();
    }
  }

  public static byte[] decodeRequest(DatagramPacket request)
    throws IOException, ClassNotFoundException {
	  byte[] response = null;
    RequestBody req = serialReq.deserialize(request.getData());
    System.out.println("Servidor: recebido \'" + req.protocol + "\'.");

    switch (req.protocol) {
      case Protocols.createRoom:
        InetAddress address = InetAddress.getByName(ramdonAddress());
        Rooms.add(new Room(req.body, address));
        ResponseBody resBody = new ResponseBody(ResponseCodes.CREATE_SUCCESSFUL, req.body +":"+ address.getHostAddress());
        response = serialRes.serialize(resBody);
        	
        return response;
      case Protocols.getInRoom:
    	  for (Room Room : Rooms) {
    	        if (Room.getName() == req.body) {
    	        	Room.addClient(req.clientId);
    	              
    	              ResponseBody res = new ResponseBody(ResponseCodes.ENTER_ROOM, Room.getAddress().getHostAddress() );
    	     
    	              try {
    					response = serialRes.serialize(res);
    					
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    	             
    				}
    	        }
    	    
    	  return response;
      case Protocols.roomOut:
    	  for (Room Room : Rooms) {
  	        if (Room.getName() == req.body) {
            
  	        	Room.removeClient(req.clientId);
  	        	ResponseBody res = new ResponseBody(ResponseCodes.QUIT_ROOM, req.body );
  	        	response = serialRes.serialize(res);
            }
          }
    	return response;
        
      case Protocols.send:
    	  for (Room Room : Rooms) {
            if (Room.getClients().indexOf(req.clientId) == 1) {
              req.body = req.clientId + ": " + req.body;
              ResponseBody res = new ResponseBody(ResponseCodes.SEND_MESSAGE, req.body );
              response = serialRes.serialize(res);
            }
          }
    	  return response;
      case Protocols.listMembers:
        StringBuilder listMembers = new StringBuilder(
          "Lista de membros - SALA: " + req.body + "/n"
        );
        for (Room Room : Rooms) {
            if (Room.getName() == req.body) {
              Room
                .getClients()
                .forEach(
                  client -> {
                    listMembers.append("\n" + client);
                  }
                );

              ResponseBody res = new ResponseBody(ResponseCodes.LIST_MEMBERS, listMembers.toString());
              response = serialRes.serialize(res);
          }
        }
        return response;
      case Protocols.listRooms:
        StringBuilder listRooms = new StringBuilder("- Lista de Salas -");
        for (Room Room : Rooms) {
            listRooms.append("\n" + Room.getName());
          }
        ResponseBody res = new ResponseBody(ResponseCodes.LIST_ROOMS, listRooms.toString());
        response = serialRes.serialize(res);
        return response;

      default:
        break;
    }
	return response;
  }

  public static void sendResponse(DatagramPacket response, byte[] message) {
    
    try {
    	DatagramPacket messageOut = new DatagramPacket(
    		      message,
    		      message.length,
    		      response.getAddress(),
    		      response.getPort()
    		    );
      mSocket.send(messageOut);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  finally {
        if (mSocket != null) mSocket.close();
      }
  }

  public static String ramdonAddress() {
    String address =
      numberRandomRange(224, 239) +
      "." +
      numberRandomRange(0, 255) +
      "." +
      numberRandomRange(0, 255) +
      "." +
      numberRandomRange(0, 255);

    return address;
  }

  public static int numberRandomRange(int min, int max) {
    Random gerador = new Random();

    int number = gerador.ints(min, max).findFirst().getAsInt();

    return number;
  }
}
