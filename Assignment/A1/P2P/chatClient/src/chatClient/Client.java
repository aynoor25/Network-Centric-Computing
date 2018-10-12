//package chatClient;

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
	List<String> files = new ArrayList<String>();
	static int clientID;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Takes command line arguments
				int port = Integer.parseInt(args[1]);
				String ip = args[0];
				//int port = 4321;
				//String ip = "localhost";
				Socket sock = null;
				int cases = 0; boolean registered = false;
				try {
					// Connect to the given IP and Port and store in the sock variable
					sock = new Socket(ip, port);
					System.out.println("Connected to server.");
					// receive message from server
					InputStream in = sock.getInputStream();
					InputStreamReader ir = new InputStreamReader(in);
					BufferedReader breader = new BufferedReader(ir);
					System.out.println("hi");
					String messageReceived = breader.readLine();
					System.out.println("hello");
					System.out.println(messageReceived);
					String messageReceived1 = breader.readLine();
					System.out.println(messageReceived1);
					String messageReceived2 = breader.readLine();
					System.out.println(messageReceived2);
					String messageReceived3 = breader.readLine();
					System.out.println(messageReceived3);
					String messageReceived4 = breader.readLine();
					System.out.println(messageReceived4);
					while(true) {
					BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
					String messageToServer = clientInput.readLine();
					// send message to server
					
					PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
					pw.println(messageToServer);		// sending case to server
					pw.flush();
					if (messageToServer!= "") {
						cases = Integer.parseInt(messageToServer);
					} else {cases = 0;}
					//System.out.println(cases);
					switch (cases) {
					case 1:
						if (registered == false) {
							pw.println("Not registered");
							pw.flush();
							System.out.println("In case 1");
							clientID = Integer.parseInt(breader.readLine());
							System.out.println(clientID);
							System.out.println("Out of case 1");
							registered = true;
						} else {
							pw.println("Registered");
							pw.flush();
							System.out.println("Already registered.");
						}
						break;
					case 2:
						System.out.println("In case 2");
						// taking input from client
						System.out.println("Please enter file name: ");
						String fileName = clientInput.readLine();
						System.out.println("Please enter data: ");
						String data = clientInput.readLine();
						// Creating the file
						PrintWriter newFile = new PrintWriter(fileName+".txt");
						newFile.println(data);
						newFile.close();
						// passing variables to the server
						pw.println(fileName);
						pw.println(data);
						pw.println(Integer.toString(clientID));
						pw.flush();
						System.out.println("Out of case 2");
						break;
					case 3:
						System.out.println("In case 3");
						String filen = clientInput.readLine();
						pw.println(filen);
						pw.flush();	
						String fileFound = breader.readLine();
						if (fileFound.equals("File found")){
							System.out.println("File found with client "+ breader.readLine());
						} else {
							System.out.println(fileFound);
						}
						System.out.println("Out of case 3");
						break;
					case 4:
						//int sizeOfFile = 542100000;			///assumed; should larger than required
						FileOutputStream f = null;
						BufferedOutputStream b = null;
						String receiveFile = "E:/Uni Work/Semester 6/CS 382/Assignment/A1/P2P/chatClient/src/chatClient/output.txt";
						String fileSize = breader.readLine();
						int sizeOfFile = Integer.parseInt(fileSize);	
						System.out.println("####"+Integer.parseInt(fileSize));
						try {							
							//InputStream in1 = sock.getInputStream();
							f = new FileOutputStream(receiveFile);
							b = new BufferedOutputStream(f);
							System.out.println("in between");
							byte[] byteArray = new byte[sizeOfFile];
							int readBytes = in.read(byteArray,0,byteArray.length);
							System.out.println("before do");
							int curr = readBytes;		// number of bytes read
							System.out.println("curr= "+curr);
							System.out.println("bytes array length = "+byteArray.length);
							/*do {
								System.out.println("in do");
								readBytes = in.read(byteArray, curr, (byteArray.length-curr));
								System.out.println("bytes read= "+readBytes);
								if (readBytes>=0) {
									curr = curr+readBytes;
								}
								System.out.println(curr);
							} while(readBytes>-1);*/
							System.out.println("hmmmm");
							b.write(byteArray, 0, curr);
							b.flush();
							System.out.println("File "+receiveFile+"downloaded ("+curr+" bytes read)");
							System.out.println("File successfully recieved.");
						} finally {
							//if (sock != null) sock.close(); 
							if (b != null)  b.close();
							if (f != null) f.close();
						}
						break;
					default:
						break;
					}
					}
				} catch (Exception e) {
					// Exception printed on console in case of error
					e.printStackTrace();
				}
	}
	
	String[] ips;				// stores IP addresses
	
	public String lookup(String filename) {
		String[][] array  = new String[5][6];			// array is the clientsPresent array in server class
		for (int i = 0; i < array[0].length; i++) {
			for (int j = 0; j < array[1].length; j++) {
				if (array[i][j] == filename) {
					return ips[i];
				}
			}
		}
		return null;
	}
	
	public long get(String filename, long IPaddress, Socket sock) throws IOException{
		String[][] array  = new String[5][6];			// array is the clientsPresent array in server class
		String receiveFile = "C:/output.txt";		// initialize to path
		boolean found = false;;
		String ip = "111";				// don't initialize
		for (int i = 0; i < array[0].length; i++) {
			for (int j = 0; j < array[1].length; j++) {
				if (array[i][j] == filename) {
					// copy into local repo
					//return 1;
					found = true; ip = findIP(i);
					receiveFile = array[i][j];
					break;
				}
				if (found) break;
			}
		}
		int port = 4562;
		if (found == true) {
			int sizeOfFile = 5420000;			///assumed; should larger than required
			FileOutputStream f = null;
			BufferedOutputStream b = null;
			try {
				sock = new Socket(ip, port);
				System.out.println("Connecting to server...");
				
				InputStream in = sock.getInputStream();
				f = new FileOutputStream(receiveFile);
				b = new BufferedOutputStream(f);
				byte[] byteArray = new byte[sizeOfFile];
				int readBytes = in.read(byteArray,0,byteArray.length);
				int curr = readBytes;		// number of bytes read
				do {
					readBytes = in.read(byteArray, curr, (byteArray.length-curr));
					if (readBytes>=0) {
						curr = curr+readBytes;
					}
				} while(readBytes>-1);
				b.write(byteArray, 0, curr);
				b.flush();
				System.out.println("File "+receiveFile+"downloaded ("+curr+" bytes read)");
			} finally {
				//if (sock != null) sock.close(); 
				if (b != null)  b.close();
				if (f != null) f.close();
			}
		}
		if (found == false) return -1;
		return -1;
	}
	
	public int put(String filename, String data, Socket sock) throws IOException {
		try {
			File newFile = new File(filename);
			// send message to server
			OutputStream os = sock.getOutputStream();
			OutputStreamWriter o = new OutputStreamWriter(os);
			BufferedWriter bwriter = new BufferedWriter(o);
			String messageToServer = "1";
			bwriter.write(messageToServer);
			bwriter.flush();
			System.out.println("Message sent to server " + messageToServer);
			// receive message from server
			InputStream in = sock.getInputStream();
			InputStreamReader ir = new InputStreamReader(in);
			BufferedReader breader = new BufferedReader(ir);
			String messageReceived = breader.readLine();
			System.out.println("Message received from server "+messageReceived);
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
		return 1;
	}
	
	
	public  String findIP(int ip) {
		return ips[ip];
	}
}
