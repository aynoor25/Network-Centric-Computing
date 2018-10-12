import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable {
	Socket client_socket;
	Worker(Socket sock) {
		client_socket = sock;
	}
	// Whatever is inside the run method will be executed when the thread starts
	public void run() {
		try {
			//while (true){
				// The following is a blocking call
				int cases = 0;
				// Send message to the connected client
				PrintWriter pw = new PrintWriter(client_socket.getOutputStream(), true);
				pw.println("Hello from server.");
				pw.println("Please choose from the following options:");
				pw.println("1. Press 1 to place a new file.");
				pw.println("2. Press 2 to lookup a file");
				pw.println("3. Press 3 to get a file");
				pw.flush();


				// Read message from the client
				InputStreamReader isr = new InputStreamReader(client_socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				// Check if client registered or not
				String id_from_client = br.readLine();
				if (check_if_registered(id_from_client) == false) {
					addClient(Integer.parseInt(id_from_client));
					File client_dir = new File(id_from_client);
					client_dir.mkdir();
				}
				while (true) {
					// check client's chosen option IF clients connected
					String read_case_from_client =  br.readLine();
					/*if ((read_case_from_client = br.readLine()) != null) {
				System.out.println("hey");
				cases = Integer.parseInt(read_case_from_client);
				}*/
					// connection with client lost
					if (read_case_from_client == null) break;
					// If user inputs a string and not numbers for choice
					try {
						cases = Integer.parseInt(read_case_from_client);
					} catch (Exception e) {
						cases = 0;
					}
					// Response of clients chosen option
					switch (cases) {
					case 1:
						String filename = br.readLine();
						String IDClient = br.readLine();
						addFile(filename,IDClient);
						System.out.println("New file added successfully.");
						/*for (int i = 0; i < GlobalVariables.filesPresent.size(); i=i+2) {
							System.out.println("File name: " + GlobalVariables.filesPresent.get(i));
							System.out.println("Client Id: " + GlobalVariables.filesPresent.get(i+1));
						}*/
						break;
					case 2:
						String filename1 = br.readLine();
						String client = lookup(filename1);
						if (client!=null) {
							System.out.println("File found with client "+ client);
							pw.println("File found");
							pw.println(client);
							pw.flush();
						} else {
							pw.println("File not found");
						}
						break;
					case 3:
						String _filename = br.readLine();
						String _clientId = br.readLine();

						String _client = lookup(_filename);
						System.out.println("1st client: " + _clientId + " 2nd Client: " + _client);
						if (_client.equals(_clientId)) {
							pw.println(_clientId);;
							pw.flush();
							System.out.println("File present in local directory.");
						} else {
							System.out.println("File found with client: " + _client);
							pw.println(_client);
							pw.flush();
						}
						break;
					default:
						System.out.println("Invalid option.");
						break;	
					}
				}

			//}
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
	}
	public static boolean check_if_registered(String client_id) {
		for (int i = 0; i < GlobalVariables.clientsPresent.size(); i++) {
			if (GlobalVariables.clientsPresent.get(i).equals(client_id)) {
				return true;
			}
		}
		return false;
	}
	
	public static  void addClient(int clientID) {
		GlobalVariables.clientsPresent.add(Integer.toString(clientID));
		/*for (int i = 0; i < GlobalVariables.clientsPresent.size(); i++) {
			System.out.println(GlobalVariables.clientsPresent.get(i));
		}*/
	}
	public static void addFile(String file_name, String client_id) {
		GlobalVariables.filesPresent.add(file_name);
		GlobalVariables.filesPresent.add(client_id);
	}
	
	public static String lookup(String file_name) {
		for (int i = 0; i < GlobalVariables.filesPresent.size(); i = i+2) {
			if (GlobalVariables.filesPresent.get(i).equals(file_name)) {
				return GlobalVariables.filesPresent.get(i+1);
			}
		}
		return null;
	}
}