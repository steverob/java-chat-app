import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ChatUI extends JFrame {
	
	JPanel mainPanel;
	JPanel messagePanel;
	JPanel chatPanel;
	JButton sendButton;
	JTextField inputBox;
	JTextArea chatBox;
	JScrollPane scrollPane;
	ClientHandler sendHandler;
	JMenuBar menuBar;
	JMenu messenger;
	JPanel statusPanel;
	StatusBar statusField;
	
	
	public ChatUI()
	{
		mainPanel=new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		//Message Box Panel Construction
		messagePanel=new JPanel();
		chatBox=new JTextArea(20,50);
		scrollPane = new JScrollPane(chatBox);
		messagePanel.add(scrollPane);
		chatBox.setEditable(false);
		mainPanel.add(messagePanel,BorderLayout.CENTER);
		
		//Chat Input Panel Construction
		chatPanel=new JPanel();
		chatPanel.setLayout(new GridLayout(3,1));
		inputBox=new JTextField();
		inputBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{
				int pressedKey=e.getKeyCode();
				if(pressedKey==KeyEvent.VK_ENTER)
				{
					chatBox.append("\nMe:"+inputBox.getText());
					sendHandler.sendMessage(inputBox.getText());
					inputBox.setText("");
				}
			}
		});
	
		sendButton=new JButton("Send");
		sendButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				chatBox.append("\nMe:"+inputBox.getText());
				sendHandler.sendMessage(inputBox.getText());
				inputBox.setText("");
			}
		});
		
		statusPanel=new JPanel();
		statusField=new StatusBar();
		
		chatPanel.add(inputBox);
		chatPanel.add(sendButton);
		chatPanel.add(statusField);
		mainPanel.add(chatPanel,BorderLayout.SOUTH);
		
		
		add(mainPanel);

		inputBox.requestFocusInWindow();
		
		//Safely close the Chat Application
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
					ChatMainClass.disconnect();
			}
		});
		
		
		//Menu Bar Construction
		menuBar=new JMenuBar();
		messenger=new JMenu("Messenger");
		JMenuItem connect=new JMenuItem("Connect");
		JMenuItem disconnect=new JMenuItem("Disconnect");
		JMenuItem exit=new JMenuItem("Exit");
		messenger.add(connect);
		messenger.add(disconnect);
		messenger.addSeparator();
		messenger.add(exit);
		menuBar.add(messenger);
		setJMenuBar(menuBar);
		
		//ActionListeners for MenuItems
		
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				statusField.setMessage("Waiting for client...(20s)");
				ChatMainClass.startChat();
			}
		});
		
		disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ChatMainClass.disconnect();
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ChatMainClass.disconnect();
				System.exit(0);
			}
		});
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("FnT Private Messenger (Server)");
		setResizable(false);
		pack();
	}
	

	public class StatusBar extends JLabel 
	{
    
	    public StatusBar() {
	        super();
	        super.setPreferredSize(new Dimension(100, 16));
	        setMessage("Ready");
	    }
	    
	    public void setMessage(String message) {
	        setText(" "+message);        
	    }        
	}
	
	
	public void setSendHandler(ClientHandler c)
	{
		this.sendHandler=c;
	}
		
}
