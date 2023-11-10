package SocketServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MultiClientServer {
//	static Socket client;
//	static DataInputStream in;
//	static DataOutputStream out;
//	static BufferedInputStream sysin;
	static ServerSocket ss;
	static List<Socket> clientLists;
	static class ClientHandler implements Runnable {
		Socket client;
		DataInputStream in;
		DataOutputStream out;
		public ClientHandler(Socket socket) {
			try {
				this.client = socket;
				in = new DataInputStream(new BufferedInputStream(this.client.getInputStream()));
				out = new DataOutputStream(this.client.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			String line = null;
			try {
				while(!"over".equals(line)&&!client.isClosed()) {
					line = in.readUTF();
					MultiClientServer.sendToAll(line);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	};
	public static void sendToAll(String msg) {
		DataOutputStream out;
		try {
			for(Socket cl:clientLists) {
				out = new DataOutputStream(cl.getOutputStream());
				out.writeUTF(msg);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println("starting server");
			ss = new ServerSocket(5000);
			System.out.println("started");
			Socket client= null;
			try {
				clientLists = Collections.synchronizedList(new ArrayList<Socket>());
				System.out.println("waiting on clients");
				int i=1;
				while(true) {
					client = ss.accept();
					System.out.println("clients connected: "+i);
					if(clientLists.size()>4) {
						break;
					}
					ClientHandler ch = new ClientHandler(client);
					Thread hm = new Thread(ch);
					hm.setDaemon(true);
					hm.start();
					clientLists.add(client);
					i++;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
