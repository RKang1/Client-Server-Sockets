
/*
 * This program implements an example of the client side of a client server 
 * application using sockets. The client connects to the server at the IP address 
 * and port provided on the command line, then it sends a simple message cotaining 
 * the string MC:1 to the server (for management command 1) and waits for the response.  
 * When the response is recieved, it prints the response, closes the connection to the 
 * server and exits.  
 *
 * Input: The IP address and the port number of the server are supplied via the
 *        command line.  
 *
 * Output: The program prints the response from the server.
 *
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {
		String echo = " ", answer;
		boolean connected = true;
		Scanner reader = new Scanner(System.in);
		int k = 0, j = 0;
		long start_time, end_time;
		if (args.length < 2) // If there are no command line arguments print an
								// error and exit.
		{
			System.out.println("Usage: java Client <server IP Address> <port>\n");
			System.exit(0);
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		// Try to connect to port and the IP address given on the command line.
		Socket s = null;
		try {
			s = new Socket(args[0], Integer.parseInt(args[1]));
		} catch (Exception e) {
			System.out.println("Error: Could not open a socket to " + args[0] + "\n");
			System.exit(100);
		}

		// Create a buffered reader attached to the socket's input stream.
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		// Create a print writer attached to the socket's output stream.
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		System.out.println("Connecting to server at " + args[0] + ":" + args[1]);

		while (connected) {
			System.out.println("\nSelect a command below ");
			System.out.println("1. Server current Date and Time ");
			System.out.println("2. Server number of running processes ");
			System.out.println("3. Server number of active socket connections");
			System.out.println("4. Server time of last system boot ");
			System.out.println("5. Server list of current users");
			System.out.println("6. Server echo back what is sent from client");
			System.out.println("7. Quit \n");
			System.out.print("input: ");

			k = reader.nextInt();

			if (k > 0 && k < 7) {
				System.out.print("Enter number of times to run: ");
				j = reader.nextInt();
			}
			if (k == 6) {
				reader.nextLine();
				System.out.println("Enter String: ");
				echo = reader.nextLine();
			}

			if (k > 0 && k < 8) {
				if (k == 1) {
					System.out.println("Requesting system time");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:1");
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 1 took " + (end_time - start_time) + "ms");
				} else if (k == 2) {
					System.out.println("Requesting number of running processes");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:2");
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 2 took " + (end_time - start_time) + "ms");
				} else if (k == 3) {
					System.out.println("Requesting number of active socket connections");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:3");
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 3 took " + (end_time - start_time) + "ms");
				} else if (k == 4) {
					System.out.println("Requesting time of last system boot");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:4");
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 4 took " + (end_time - start_time) + "ms");
				} else if (k == 5) {
					System.out.println("Requesting current users");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:5");
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 5 took  " + (end_time - start_time) + "ms");
				} else if (k == 6) {
					System.out.println("Requesting echo back ");
					start_time = System.currentTimeMillis();

					for (int i = 0; i < j; i++) {
						out.println("MC:6");
						out.println(echo);
						answer = "";
						while (!answer.equals("Server Done")) {
							answer = input.readLine();
						}
					}
					end_time = System.currentTimeMillis();
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());

					System.out.println("Request 6 took  " + (end_time - start_time) + "ms");
				} else if (k == 7) {
					System.out.println("Requesting quit");
					out.println("MC:7");
					answer = "";
					while (!answer.equals("Server Done")) {
						answer = input.readLine();
					}
					System.out.println("Response from the server:\n");
					System.out.println(input.readLine());
					connected = false;
				}
			} else {
				System.out.println("Please enter a number 1-7.");

			}
		}
		return;
	}
}
