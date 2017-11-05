package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private String hostName;
	private int portNumber;
    private Socket kkSocket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader stdIn;
    private String fromServer;
    private String fromUser;
    
    public Client(){
    	hostName ="";//args[0];
        portNumber = Integer.parseInt("1500");//args[1]);

            try {
				kkSocket = new Socket(hostName, portNumber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				out = new PrintWriter(kkSocket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				in = new BufferedReader(
				    new InputStreamReader(kkSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            try {
				while ((fromServer = in.readLine()) != null) {
				    System.out.println("Server: " + fromServer);
				    if (fromServer.equals("Bye."))
				        break;
				    
				    fromUser = stdIn.readLine();
				    if (fromUser != null) {
				        System.out.println("Client: " + fromUser);
				        out.println(fromUser);
				    }
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
    }
}
