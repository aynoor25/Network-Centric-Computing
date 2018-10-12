import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Admin implements Runnable {
	int port;
	Admin(int port_num) {
		Thread client_server = new Thread(this);
		port = port_num;
		client_server.start();
	}
	
	// Whatever is inside the run method will be executed when the thread starts
	public void run() {
		System.out.println("hi");
		try {
			// Start listening for connection on the specified port
			ServerSocket server = new ServerSocket(port);
			System.out.println("Starting Client server.");
			while (true) {
				// The following is a blocking call
				Socket client = server.accept();
				InputStreamReader isr = new InputStreamReader(client.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String filepath = "/home/aynoor/Documents/Semester6/CS 382/AA1/Assign1/src/"+ Integer.toString(port)+"/" + br.readLine() +".txt";
				Thread _t = new Thread(new client_Workers(client, filepath));
				_t.start();
				
				
				
				
				
				
				/*// Send message to the connected client
				PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
				pw.println("Hello from server.");
				
				// Read message from the client
				InputStreamReader isr = new InputStreamReader(client.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				
				// The following is a blocking call
				String message = br.readLine();*/
				
			}
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
	}
}