package application;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
public class EchoServer{
	static final int MAX = 5;
    static ExecutorService executor=null;
    static ServerSocket serverSocket = null;
    		 
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            serverSocket = new ServerSocket(6666);
            executor= Executors.newFixedThreadPool(MAX);
            while(true) {
            	ThreadSocket thread = new ThreadSocket(serverSocket.accept());
            	executor.execute(thread);
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port: 6666");
            System.exit(-1);
        } 
        executor.shutdown();
        serverSocket.close();
    }
    
}
class ThreadSocket extends Thread{
	private Socket socket;
	ThreadSocket(Socket socket){
		this.socket=socket;
		this.start();
	}
	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputLine;
 
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
			}
			out.close();
			in.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}


