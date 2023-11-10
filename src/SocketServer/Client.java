package SocketServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main (String[] args) {
		try {
			Scanner  sc = new Scanner(System.in);
			Socket con = new Socket("127.0.0.1",5000);
			System.out.println("connected to server");
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			DataInputStream in = new DataInputStream(new BufferedInputStream(con.getInputStream()));
			String line = null;
			while(!con.isClosed() && !"over".equals(line)) {
				try {
					line = sc.next();
					out.writeUTF(line);
					System.out.println("server says:" + in.readUTF());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("disconnecting");
//			sysin.close();
			in.close();
			out.close();
			sc.close();
			if(!con.isClosed()) {
				con.close();
			}
			System.out.println("disconnected");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
