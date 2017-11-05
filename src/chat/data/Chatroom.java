package chat.data;

import java.util.Iterator;
import java.util.List;

import chat.server.Connection;

public class Chatroom {
	private List<Connection> connections;
	
	//Alle Referenzen von Chatroom in anderen Objekten beseitigen
	public void delete () {
		Iterator<Connection> iterator =  connections.iterator();
		while(iterator.hasNext()) {
			iterator.next().chatroomDeleted();
		}
	}
}
