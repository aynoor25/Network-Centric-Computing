// To run: java Server <port>
// e.g. java Server 8822

import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
	//List<Client> clientsPresent = new ArrayList<Client>();
	
	public static void main(String args[]) {
		// Port number taken from command line and converted to integer
		int port = Integer.parseInt(args[0]);
		try {
			// Start listening for connection on the specified port
			ServerSocket server = new ServerSocket(port);
			System.out.println("Starting server.");
			while(true) {
				Thread _t = new Thread(new Worker(server.accept()));
				_t.start();
			}
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
	}
	
	
	
}