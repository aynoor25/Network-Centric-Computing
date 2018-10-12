
import java.io.*;
import java.net.*;

public class server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatagramSocket skt = null;
		try {
			skt = new DatagramSocket(5000); 

			byte[] buffer = new byte[1000];
			
			while (true) {
				// recieve request from client
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				
				skt.receive(request);
				
				String [] arrayMsg = (new String(request.getData())).split(" ");
				
				byte [] sendMsg = (arrayMsg[0] + " server processed").getBytes();
				
				DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, request.getAddress(), request.getPort());
				
				skt.send(reply);
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}