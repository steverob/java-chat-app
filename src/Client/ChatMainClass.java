import javax.swing.*;

import java.awt.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatMainClass {
	
	public static void connectToServer ()throws Exception
	{		server=new Socket();
			server.setSoTimeout(10000);
			server.connect(new InetSocketAddress("localhost",9003), 10000);
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			Scanner input=new Scanner(server.getInputStream());
			serverName=input.nextLine();
			out.println(clientName);
	}
	
	static void disconnect()
	{
		myChatUI.statusField.setMessage("Disconnected..");
		try 
		{
			if(!server.isClosed()) {
				h.sendMessage("$EndTransmission$");
				server.close();
			}
			if(h!=null){
				h.stopReader();
				h=null;
			}
			myChatUI.chatBox.setText("");
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
		
	}
	
	static void startChat()
	{
		JOptionPane.showMessageDialog(myChatUI,"Connecting to Server (20s Timeout)");

		try 
		{
			connectToServer();
			h=new ClientHandler(myChatUI,server,serverName);
			myChatUI.setSendHandler(h);
			myChatUI.statusField.setMessage("Connected to "+serverName);
		}
		catch(SocketTimeoutException e)
		{
			try {
				server.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			myChatUI.statusField.setMessage("Ready...");
			JOptionPane.showMessageDialog(myChatUI,"Could not connect to server!");

		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		
			
	}
	
	public static void main(String arg[])
	{
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				myChatUI=new ChatUI();
				myChatUI.setVisible(true);
				myChatUI.statusField.setMessage("Ready...");
				clientName=JOptionPane.showInputDialog( "Please enter your nickname..." );

			}
		});
	}
	static Socket server;
	static ClientHandler h;
	static ChatUI myChatUI;
	static String clientName;
	static String serverName;
	
}
