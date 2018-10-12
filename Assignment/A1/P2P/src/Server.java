// To run: java Server <port>
// e.g. java Server 8822

import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
	public static void main(String args[]) {
		// Port number taken from command line and converted to integer
		int port = Integer.parseInt(args[0]);
		//int port = 4321;
		try {
			// Start listening for connection on the specified port
			ServerSocket server = new ServerSocket(port);
			System.out.println("Starting server.");
			while (true) {
				System.out.println("hi");
				// The following is a blocking call
				Socket client = server.accept();
				System.out.println("yo");
				// Send message to the connected client
				// to print on console
				// client.getOutputStream outputs to clients console
				PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
				pw.println("Hello from server.");
				
				// Read message from the client
				InputStreamReader isr = new InputStreamReader(client.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				
				// The following is a blocking call
				String message = br.readLine();
			}
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
	}

}