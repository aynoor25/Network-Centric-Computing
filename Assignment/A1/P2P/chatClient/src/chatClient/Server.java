//package chatClient;

import java.util.*;
import java.net.*;
import java.io.*;

public  class Server {
	//static List<String[]> clientsPresent = new ArrayList<String[]>();			// stores the files kept with clients 
	//static List<String> clientsPresent = new ArrayList<String>();	
	static List<Client> clientsPresent = new ArrayList<Client>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Port number taken from command line and converted to integer
				int port = Integer.parseInt(args[0]);
				//int port = 4321;
				int cases = 0;
				String messageClient = "";
				Socket client = null;
				int clientID = 1;
				try {
					// Start listening for connection on the specified port
					ServerSocket server = new ServerSocket(port);
					System.out.println("Starting server.");
						// The following is a blocking call
						client = server.accept();
						//while(true){
						System.out.println("client accepted");
						PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
						pw.println("Hello from server.");
						pw.println("Please choose from the following options:");
						pw.println("1. Press 1 to register Client");
						pw.println("2. Press 2 to place a new file.");
						pw.println("3. Press 3 to lookup a file");
						pw.flush();
						System.out.println("before while");
						while (true){
		                InputStream in = client.getInputStream();
		                InputStreamReader ir = new InputStreamReader(in);
		                BufferedReader breader = new BufferedReader(ir);
		                messageClient = breader.readLine();
		                System.out.println("Message received from client is "+ messageClient);
						if (messageClient != "") {
							cases = Integer.parseInt(messageClient);
						} else {cases = 0;}
						switch(cases) {
						case 1:
							if ((breader.readLine()).equals("Registered")) {
								
							} else {
							//System.out.println("in case 1");
							InetAddress address = client.getInetAddress();
							//String client_name = address.getHostName();
							String client_name = Integer.toString(clientID);
							addClient(client_name);
							pw.println(client_name);
							pw.flush();
							clientID++;
							//System.out.println("out of case 1");
							System.out.println("Client registered.");
							}
							break;
						case 2:
							//System.out.println("In case " + cases);
							String filename = breader.readLine();
							String data = breader.readLine();
							String IDClient = breader.readLine();
							addFiles(filename,data,IDClient);
							//System.out.println("Out of case 2");
							System.out.println("New file added successfully.");
							break;
						case 3:
							System.out.println("In case 3");
							String filename1 = breader.readLine();
							String ans = lookup(filename1);
							if (ans!=null) {
								System.out.println("File found with client "+ ans);
								pw.println("File found");
								pw.println(ans);
								pw.flush();
							} else {
								pw.println("File not found");
							}
							System.out.println("Out of case 3");
							break;
						case 4:
							String fileToSend = "E:/Uni Work/Semester 6/CS 382/Assignment/A1/P2P/chatClient/src/chatClient/output1.txt";
							OutputStream os = null;
							try {
								
								//while(true) {
									//try{
									File fileSend = new File(fileToSend);
									FileInputStream f = new FileInputStream(fileSend);
									BufferedInputStream b = new BufferedInputStream(f);
									//byte[] byteArray = new byte[(int)fileToSend.length()];
									//System.out.println("Length of file:  "+fileToSend.length());
									byte[] byteArray = new byte[532000];
									//pw.println(Integer.toString((int)fileToSend.length()));
									pw.println((int)byteArray.length);
									pw.flush();
									System.out.println("File size = "+(int)fileToSend.length());
									b.read(byteArray,0,byteArray.length);
									os = client.getOutputStream();
									System.out.println("Sending the file "+fileToSend);
									os.write(byteArray, 0, byteArray.length);
									os.flush();
									System.out.println("File sent successfully!");
								
									 /*finally {
										if (os != null) os.close();
									}*/
								//}
							} finally {//if (os != null) os.close();
								
							}
							
							break;
						default:
							System.out.println("Invalid input");
							break;
						}
					}
				} catch (Exception e) {
					// Exception printed on console in case of error
					e.printStackTrace();
				}		
				
				
	}
	public static void addClient(String clientName) {
		//clientsPresent.add(new String[] {clientName});
		Client newClient = new Client();
		newClient.files.add(clientName);
		clientsPresent.add(newClient);
		//clientsPresent.add(clientName);
		List<String> temp = clientsPresent.get(0).files;
		//System.out.println("Exiting addClient "+ temp.get(0));
	}
	
	public static void addFiles(String filename, String data, String id) {
		for (int i = 0; i < clientsPresent.size(); i++) {
			if (clientsPresent.get(i).files.get(0).equals(id)) {
				clientsPresent.get(i).files.add(filename);
			}
		}
		//System.out.println("Size of files " + clientsPresent.get(0).files.size());
	}
	
	public static String lookup(String filename) {
		for (int i = 0; i < clientsPresent.size(); i++) {
			for (int j = 0; j < clientsPresent.get(i).files.size(); j++) {
				if (clientsPresent.get(i).files.get(j).equals(filename)) {
					return clientsPresent.get(i).files.get(0);
				}
			}
		}
		return null;
	}
	
}
