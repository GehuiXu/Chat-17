package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Semaphore;

import chat.data.Chatroom;
import chat.data.Console;

public class Connection implements Runnable {
	private Socket socket;
	private Server server;
	private Console console;
	private Chatroom chatroom;
	private BufferedReader in;
	private PrintWriter out;
	private Thread thread;
	private List<ConnectionCommand> internCommands; //Sammelt die commandos die der Server und der Chatraum an die Connection sendet um sie dann synchronisiert ausführen zu können
	private Semaphore commandsSemaphore;
	
	public Connection (Socket socket, Server server, Console console) throws NullPointerException {
		if(socket==null||server==null) {
			throw new NullPointerException();
		}
		this.socket=socket;
		this.server=server;
		this.console=console;
		chatroom=null;
		thread=new Thread(this);
		commandsSemaphore=new Semaphore(1,true);
		thread.start();
		
	}
	
	@Override
	public void run() {
		try {
			//TODO später alles schließen
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			while(true) {
				if(!server.serverRunning()||thread.isInterrupted()) {
					closeConnection();
					break;
				}
				internCommands();
				readInput();
			}
		}
		catch(IOException e) {
			
		}
		
	}
	
	private void internCommands() {
		// TODO Auto-generated method stub
		
	}

	public void chatroomDeleted() {
		//TODO
	}
	
	private void readInput() throws IOException {
		while(in.ready()) {
			String input = in.readLine();
			//TODO string prüfen und antworten
			out.println(input); //zum testen
		}
	}
	
	public void closeConnection() {
		//TODO
		
	}
	
	private class Command {
		ConnectionCommand command;
		String message;
		public Command (String messages, ConnectionCommand command) {
			this.command=command;
			this.message=message;
		}
	}
}
