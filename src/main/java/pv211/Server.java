package pv211;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Date;
import java.util.List;
public class Server extends Thread {
	static int port = 1111;//number identifier that helps to direct traffic to needed application on server
	static String filename = "весь список сообщений.txt", allMess = new String(), communication = new String();//message file & whole lists of messages in string objects
	public Server() { }
	@Override
	public void run() {
		ArrayList<String> messages = new ArrayList<String>();//message collection
		try {
			ServerSocket serverSocket = new ServerSocket(port);//implementation server sockets
			try { while (true) {
				Socket socket = serverSocket.accept();
				try {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message = bufferedReader.readLine();//received message
					PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					if (!message.isEmpty()) {
						Date date = new Date();//representation specific instant in time
						messages.add(date + " " + message);
						if (messages.contains("null")) messages.remove(messages.indexOf("null"));
						if (messages.size() > 0) {
							communication = new String();//reset communication
							FileOutputStream fos = new FileOutputStream(filename);//output stream for writing data to file or to file descriptor
							for (int i = 0; i < messages.size(); i++) {
								String links = messages.get(i);//message from collection
								communication += links + "	";//adding info to communication
								fos.write((links + "\n").getBytes());
							}
							fos.flush();
							fos.close();
							printWriter.println(communication);
						}
					}
					else {
						List<String> msgs = messages;//message collection
						allMess = new String();//reset all messages
						for (int i = 0; i < msgs.size(); i++) {
							String msg = msgs.get(i);//message from collection
							allMess += msg + "	";//adding info to all messages
						}
						printWriter.println(allMess);
					}
				}
				finally { socket.close(); }
			}
			}
			finally { serverSocket.close(); }
		}
		catch(IOException iOException) { }
	}
}