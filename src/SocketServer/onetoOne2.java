package SocketServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class onetoOne2 {
	static Socket client;
	static DataInputStream in;
	static DataOutputStream out;
	static Scanner sysin;
	static ServerSocket ss;
//	oneToOne1(Socket socket){
//		this.client = socket;
//		in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
//		out = new DataOutputStream(client.getOutputStream());
//		sysin = new DataInputStream(System.in);
//	}
	static class HandleMessages implements Runnable{
		public void run() {
			String line = null;
			while(!client.isClosed() && !"over".equalsIgnoreCase(line)) {
				try {
					line = in.readUTF();
					System.out.println("user 1 says: "+line);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	static void close() {
		try {
			in.close();
			out.close();
			sysin.close();
			if (!client.isClosed()) {
				client.close();
			}
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println("connecting to user 1");
			client = new Socket("127.0.0.1",5000);
			System.out.println("connected to user 1");
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			out = new DataOutputStream(client.getOutputStream());
			sysin = new Scanner(System.in);
			HandleMessages handleMessages = new HandleMessages();
			Thread hm = new Thread(handleMessages);
			hm.setDaemon(true);
			hm.start();
			String line  = null;
			while(!client.isClosed() && !"over".equalsIgnoreCase(line)) {
				try {
					line = sysin.next();
					out.writeUTF(line);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
