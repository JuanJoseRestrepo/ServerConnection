package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class TCPConnection extends Thread {

	public OnMessageListener listener;
	private ServerSocket server;
	private Socket socket;
	private int puerto;
	private String saveMessage;
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(puerto);
			
			System.out.println("Esperando cliente...");
			socket = server.accept();
			System.out.println("Conectado =)");
			System.out.println("Espere la respuesta del usuario...");
			
			InputStream is = socket.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));
			
			OutputStream out = socket.getOutputStream();
			BufferedWriter bwritter = new BufferedWriter(new OutputStreamWriter(out));
			
			Scanner scan = new Scanner(System.in);
			
			while(true) {
				
				String msj =breader.readLine();
				
				if(msj.equalsIgnoreCase("whatTimeIsIt")) {
					Calendar c = Calendar.getInstance();
					String fecha = c.getTime().toString();
					
					
					bwritter.write(fecha + "\n");
					bwritter.flush();
					
				}else if(msj.equalsIgnoreCase("interface")){
					String msj1 = "";
					Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
					while(interfaces.hasMoreElements()) {
						NetworkInterface netN = interfaces.nextElement();
						if(netN.isUp()) {
							msj1 += netN.getName() + " ";
						}
					}
					bwritter.write(msj1 + "\n");
					bwritter.flush();
				
			    }else if(msj.equalsIgnoreCase("remoteIpconfig")) {

			    	InetAddress addres = InetAddress.getLocalHost();
			    	String msj2 = addres.getLocalHost().toString();
			    	
					bwritter.write(msj2 + "\n");
					bwritter.flush();
				
			    }else if(msj.getBytes().length == 1024) {

					bwritter.write(msj + "\n");
					bwritter.flush();
				
			    
				}else if(msj.getBytes().length == 8192) {
				
					bwritter.write(msj + "\n");
					bwritter.flush();
			    
				}else{
				
					
					System.out.println("Escriba un mensaje para el cliente");
					String line = scan.nextLine() + "\n";
					bwritter.write(line);
					bwritter.flush();
					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	
	public interface OnMessageListener{

		public void onMessage(String message);
	}


}
