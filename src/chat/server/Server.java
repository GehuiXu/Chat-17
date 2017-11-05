package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import chat.data.Chatroom;
import chat.data.Console;
import chat.data.User;

public class Server {
	private ServerSocket serverSocket;
	private int port;
	private boolean serverRunning;
	private Console console;
	private HashMap<User,Connection> loggedInUsers; //Jeder zugriff auf die HashMap muss per loginSemaphore abgesichert werden
	private HashMap<String,Chatroom> chatrooms; //Jeder zugriff auf die HashMap muss per chatroomSemaphore abgesichert werden
	
	private Semaphore loginSemaphore; 
	private Semaphore chatroomSemaphore;
	
	public Server () {
		loadConfig();
		console = new Console();
		//TODO Konsolen Thread starten
		try {
			startServer();
			
		} catch (IOException e) {
			e.getStackTrace();
			return;
		}
	}
	
	private void loadConfig() {
		port = 4646;
		//TODO
	}
	
	private void startServer() throws IOException {
		loggedInUsers=new HashMap<User,Connection>();
		chatrooms=new HashMap<String,Chatroom>();
		loginSemaphore = new Semaphore(1, true);
		chatroomSemaphore = new Semaphore(1, true);
		
		serverRunning=true; //Möglichkeit einbauen den Server über Konsole auszuschalten (Thread)
		serverSocket = new ServerSocket(port);
		console.write("Server is running");
		while(serverRunning) {
			Socket clientSocket = serverSocket.accept();
			new Connection(clientSocket,this, console); //Eventuell eine Liste für Connections die noch keinen eingeloggten User haben
			
		}
	}
	public boolean logIn (User user, Connection con) throws InterruptedException, NullPointerException {
		if(user==null||con==null) {
			throw new NullPointerException();
		}
		loginSemaphore.acquire();
		boolean success=false;
		if(!loggedInUsers.containsKey(user)) {
			loggedInUsers.put(user, con);
			success=true;
		}
		loginSemaphore.release();
		return success;
	}
	//Eventuell alle booleans durch einen neuen/anderen Datentyp austauschen der den exakten Fehler für die Nachricht an die Connection weitergibt
	public boolean logOut (User user) throws InterruptedException, NullPointerException {
		if(user==null) {
			throw new NullPointerException();
		}
		loginSemaphore.acquire();
		boolean success=false;
		if(!loggedInUsers.containsKey(user)) {
			loggedInUsers.remove(user);
			success=true;
		}
		loginSemaphore.release();
		return success;
	}
	
	public boolean createChatroom(String name) throws InterruptedException, NullPointerException {
		if(name==null) {
			throw new NullPointerException();
		}
		chatroomSemaphore.acquire();
		//TODO erstelle Chatroom
		chatroomSemaphore.release();
		return true;
	}
	
	public boolean deleteChatroom(String name) throws InterruptedException, NullPointerException {
		if(name==null) {
			throw new NullPointerException();
		}
		chatroomSemaphore.acquire();
		if(chatrooms.containsKey(name)) {
			chatrooms.get(name).delete();
			chatrooms.remove(name);
		}
		chatroomSemaphore.release();
		return true;
	}
	
	public Chatroom getChatroom(String name) throws InterruptedException, NullPointerException {
		if(name==null) {
			throw new NullPointerException();
		}
		chatroomSemaphore.acquire();
		Chatroom chatroom = null;
		if(chatrooms.containsKey(name)) {
			chatroom=chatrooms.get(name);
		}
		chatroomSemaphore.release();
		return chatroom;
	}
	
	public List<String> getChatroomList() throws InterruptedException {
		chatroomSemaphore.acquire();
		Set<String> keys = chatrooms.keySet();
		Iterator<String> iterator = keys.iterator();
		List<String> chatroomList = new LinkedList<String>();
		while(iterator.hasNext()) {
			chatroomList.add(iterator.next());
		}
		chatroomSemaphore.release();
		return chatroomList;
	}
	
	public boolean serverRunning() {
		return serverRunning;
	}
}
