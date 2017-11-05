package chat;

import chat.client.Client;
import chat.server.Server;

public class Main {
	public static void main(String[] args) {
		if (!checkInput(args)) {
			System.err.println("Wrong input");
			return;
		}
		String mode = "client";
		if (mode.equals("client")) {
			System.out.println("Start client");
			new Client();
		} else if (mode.equals("server")) {
			System.out.println("Start server");
			new Server();

		} else {
			System.err.println("Unknown mode selected");
		}
	}

	public static boolean checkInput(String[] args) {
		// TODO
		return true;
	}
}
