package pv211;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
public class DependencyInjectionsApplication {
	static JFrame frame1 = new JFrame(), frame2 = new JFrame();//extended version of frame that adds support for swing component architecture
	static String communication = "<html>", links = "<html>", ip = "192.168.1.146";//message outputs & protocol
	public static void main(String[] args) {
		Thread thread = new Server();//thread of execution program
		thread.start();
		frame1.setLayout(null);
		JTextArea jTextArea = new JTextArea();//multi-line area that displays plain text
		jTextArea.setBounds(30, 30, 270,
				300);
		jTextArea.setLineWrap(true);
		JLabel jLabel = new JLabel();//display area for short text string or image
		jLabel.setBounds(300, 30, 270,
				300);
		JButton jButton1 = new JButton("отправить"), jButton2 = new JButton("весь список сообщений");//implementation of push button
		jButton1.setBounds(30, 330, 120,
				30);
		jButton2.setBounds(180, 330, 120,
				30);
		Component[] components = { jTextArea, jLabel, jButton1,
				jButton2 };//array of object having graphical representation that can be displayed on screen and that can interact with user
		for (int i = 0; i < components.length; i++) frame1.add(components[i]);
		frame1.setSize(300, 300);
		frame1.setLocationRelativeTo(null);
		frame1.pack();
		frame1.setBounds(600, 210, 600,
				600);
		frame1.setVisible(true);
		jButton1.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
			try {
				InetAddress inetAddress = InetAddress.getByName(ip);//representation address
				Socket socket = new Socket(inetAddress, 1111);//implementation client sockets
				try {
					PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);//formation representations of objects to text-output stream
					printWriter.println(jTextArea.getText());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//reading text from character-input stream
					String message = bufferedReader.readLine();//received message
					String[] messages = message.split("	");//separation message
					ArrayList<String> msgs = new ArrayList<String>();//message collection
					for (int i = 0; i < messages.length; i++) {
						msgs.add(messages[i]);
						if (msgs.size() > 10) msgs.remove(0);
					}
					links = "<html>";//reset links
					for (int i = 0; i < msgs.size(); i++) links += "<div>" + msgs.get(i) + "</div>";//adding info to message
					links += "</html>";//closure message
					jLabel.setText(links);
					jTextArea.setText(null);
				}
				finally { socket.close(); }
			}
			catch(IOException iOException) { }
		}});
		jButton2.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
			try {
				InetAddress inetAddress = InetAddress.getByName(ip);
				Socket socket = new Socket(inetAddress, 1111);
				try {
					PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					printWriter.println();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message = bufferedReader.readLine();//received message
					String[] msg = message.split("	");//separation message
					JLabel label = new JLabel();
					label.setBounds(30, 30, 540,
							300);
					frame2.setLayout(null);
					frame2.add(label);
					frame2.setSize(300, 300);
					frame2.setLocationRelativeTo(null);
					frame2.pack();
					frame2.setBounds(600, 210, 600,
							600);
					frame2.setVisible(true);
					communication = "<html>";//reset communication
					for (int i = 0; i < msg.length; i++) communication += "<div>" + msg[i] + "</div>";//adding info to message
					communication += "</html>";//closure communication
					label.setText(communication);
					communication = new String();//reset communication
					frame2.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent windowEvent) { label.setText(communication); }
					});
				}
				finally { socket.close(); }
			}
			catch(IOException iOException) { }
		} });
	}
}