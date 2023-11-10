package SocketServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	public static void main(String[] args) {
		try {
			ServerSocket serv = new ServerSocket(5000);
			System.out.println("server waiting on connection");
			Socket socket = serv.accept();
			Scanner sc = new Scanner(System.in);
			System.out.println("client connected");
			String line = null;
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			while(!"Over".equals(line)) {
				try {
					line = in.readUTF();
					System.out.println("client: " + line);
					line = sc.next();
					out.writeUTF(line);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("closing connection");
			out.close();
			in.close();
			socket.close();
			sc.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
