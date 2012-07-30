import java.net.*;
import java.io.*;
import java.util.*;


public class ClientHandler{
	
	ChatUI gui;
	Socket server;
	InputStream inStream;
	OutputStream outStream;
	PrintWriter output;
	public String serverName;
	Thread readThread;


	
	public ClientHandler(ChatUI gui,Socket server, String serverName)
	{
		this.gui=gui;
		this.server=server;
		this.serverName=serverName;
		
		try 
		{
			inStream=server.getInputStream();
			outStream=server.getOutputStream();
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
				String mess=input.nextLine();
				if(mess.equals("$EndTransmission$"))
				{
					ChatMainClass.disconnect();
				}
				else
				{
					gui.chatBox.append("\n"+serverName+":"+mess);
				}
			}
		}
			
	}
	
	public void sendMessage(String message)
	{
		output.println(message);
	}
		
}
	
	


	

