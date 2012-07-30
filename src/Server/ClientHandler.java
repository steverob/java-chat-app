import java.net.*;
import java.io.*;
import java.util.*;


public class ClientHandler{
	
	ChatUI gui;
	ServerSocket server;
	Socket client;
	InputStream inStream;
	OutputStream outStream;
	PrintWriter output;
	public String clientName;
	Thread readThread;


	
	public ClientHandler(ChatUI gui,ServerSocket server,Socket client, String clientName)
	{
		this.gui=gui;
		this.server=server;
		this.client=client;
		this.clientName=clientName;
		
		try 
		{
			inStream=client.getInputStream();
			outStream=client.getOutputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		output = new PrintWriter(outStream, true);
		MessageReader reader=new MessageReader();
		readThread=new Thread(reader);
		readThread.start();
		
	}
	
	@SuppressWarnings("deprecation")
	public void stopReader()
	{
		readThread.stop();
	}
	
	class MessageReader implements Runnable
	{
		Scanner input=new Scanner(inStream);
		public void run() {
			
			while(true)
			{
				if(input.hasNextLine())
				{
					String mess=input.nextLine();
					if(mess.equals("$EndTransmission$"))
					{
						ChatMainClass.disconnect();
					}
					else
					{
						gui.chatBox.append("\n"+clientName+":"+mess);
					}
				}
			}
		}
			
	}
	
	public void sendMessage(String message)
	{
		output.println(message);
	}
		
}
	
	


	

