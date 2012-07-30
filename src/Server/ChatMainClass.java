import javax.swing.*;

import java.awt.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ChatMainClass {
	
	public static String listenForClient ()throws Exception
	{
			String clientName = null;
			serverSock=new ServerSocket(9003);
			serverSock.setSoTimeout(10000);
			client=serverSock.accept();
			InputStream inStream;
			inStream=client.getInputStream();
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(myName);
			Scanner input=new Scanner(inStream);
			clientName=input.next();
			return clientName;
	}
	
	static void disconnect()
	{
		myChatUI.statusField.setMessage("Disconnected..");
		myChatUI.chatBox.setText("");
		try 
		{
			if(!serverSock.isClosed()){
				h.sendMessage("$EndTransmission$");
				serverSock.close();
			}
			if(h!=null){
				h.stopReader();
				h=null;
			}
			if(client!=null)
				client.close();
		
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
		
	}
	
	static void startChat()
	{
		String clientName="";
		JOptionPane.showMessageDialog(myChatUI,"Waiting for Client (20s Timeout)");

		try 
		{
			clientName=listenForClient();
			h=new ClientHandler(myChatUI,serverSock,client,clientName);
			myChatUI.setSendHandler(h);
			myChatUI.statusField.setMessage("Connected to "+clientName);
		}
		catch(SocketTimeoutException e)
		{
			try {
				serverSock.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			myChatUI.statusField.setMessage("Ready...");
			JOptionPane.showMessageDialog(myChatUI,"Could not connect to client!");

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
				myName=JOptionPane.showInputDialog( "Please enter your nickname..." );


			}
		});
	}
	public static ServerSocket serverSock;
	static Socket client;
	static ClientHandler h;
	static ChatUI myChatUI;
	static String myName;
	
}
