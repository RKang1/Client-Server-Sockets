import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	BufferedReader input = null;
	PrintWriter out = null;
	Socket socket;

	public void run() {
		// Read the request from the client
		try {
			Process cmdProc;
			cmdProc = null;
			String[] cmd;
			boolean qFlag = false;
			String answer;

			while (!qFlag) {
				answer = input.readLine();

				switch (answer) {
				case "MC:1":
					System.out.println("Responding to date request from the client ");
					cmd = new String[] { "bash", "-c", "date" };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:2":
					System.out.println("Responding to number of running processes request from client ");
					cmd = new String[] { "bash", "-c", "ps -ef|wc -l" };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:3":
					System.out.println("Responding to number of active socket connections request from client ");
					cmd = new String[] { "bash", "-c", "ss -t -a|grep ESTAB|wc -l" };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:4":
					System.out.println("Responding to time of last server system boot request from client ");
					cmd = new String[] { "bash", "-c", "who -b" };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:5":
					System.out.println("Responding to list of current users request from client ");
					cmd = new String[] { "bash", "-c", "users" };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:6":
					System.out.println("Responding to echo request from client ");
					cmd = new String[] { "bash", "-c", "echo " + input.readLine() };
					cmdProc = Runtime.getRuntime().exec(cmd);
					break;

				case "MC:7":
					System.out.println("Responding to quit request from client ");
					cmd = new String[] { "bash", "-c", "echo Closing socket " };
					cmdProc = Runtime.getRuntime().exec(cmd);
					qFlag = true;
					break;

				default:
					System.out.println("Unknown request ");
					socket.close();
					qFlag = true;
					return;
				}

				// Read the result of the commands and send the final result to
				// the server
				BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));

				out.println("Server Done");
				out.println(cmdin.readLine());
			}
			socket.close();
			return;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// run

	public Server(Socket sock) throws IOException {
		this.socket = sock;
		System.out.println("Accepted Client connection");

		// Attach a buffered reader to the socket's input stream
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Attach a print writer to the socket's output stream
		out = new PrintWriter(socket.getOutputStream(), true);
	}// Server()

	public static void main(String[] args) {
		System.out.println("CNT 4504 Server Socket Project");
		System.out.println("Server starting on socket 1337");

		ServerSocket listener;
		try {
			// Create the socket and bind to port number 1337
			listener = new ServerSocket(1337);
			while (true) {
				Socket sock = listener.accept();
				new Thread(new Server(sock)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// main
}// Server
