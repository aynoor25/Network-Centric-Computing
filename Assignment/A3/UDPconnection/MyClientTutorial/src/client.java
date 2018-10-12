
import java.io.*;
import java.net.*;
public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DatagramSocket skt;
		try {
			skt = new DatagramSocket();
			String msg = "test message ";
			
			// to send this message it is converted to bytes
			
			byte [] b = msg.getBytes();
			
			InetAddress host = InetAddress.getByName("localhost");
			int serverSocket = 5000;
			
			DatagramPacket  request = new DatagramPacket(b, b.length, host, serverSocket);
			
			// use the socket to send the packet
			skt.send(request);
			
			
			//********************************************************
			// make another datagram packet to recieve reply
			// store the server reply in this buffer
			byte[] buffer = new byte[1000];
			// store the message being recieved in this packet
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			
			skt.receive(reply);
			
			System.out.println("Client recieved " + new String (reply.getData()));
			
			skt.close();
		} catch(Exception e) {
			
		}
		
	}

}
