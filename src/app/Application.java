package app;

import java.awt.peer.TextComponentPeer;

import comm.TCPConnection;

public class Application implements TCPConnection.OnMessageListener {

	private TCPConnection connection;
	
	public Application() {
		connection = new TCPConnection();
		connection.setList(this);
		connection.setPuerto(5000);
	}
	
	public void init() {
		connection.start();
	}

	@Override
	public void onMessage(String message) {
	
		System.out.println("Recibido:" + message);
		
	}

	
	
}
