import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;


public class client_Workers implements Runnable {
	Socket client_socket;
	String file_name;
	client_Workers(Socket sock, String filename) {
		client_socket = sock;
		file_name = filename;
	}
	
	public void run() {
		// put a while loop so that if for some reason it fails one time it won't fail again
		try {
			BufferedOutputStream out_client = new BufferedOutputStream(client_socket.getOutputStream());
			File file_to_send = new File( file_name );
            byte[] byte_array = new byte[(int)file_to_send.length()];

            FileInputStream file_input_stream = new FileInputStream(file_to_send);
            BufferedInputStream b_input_stream = new BufferedInputStream(file_input_stream);

            b_input_stream.read(byte_array, 0, byte_array.length);
            out_client.write(byte_array, 0, byte_array.length);
            out_client.flush();
            out_client.close();
            client_socket.close();
		} catch (Exception e) {
			// Exception printed on console in case of error
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}