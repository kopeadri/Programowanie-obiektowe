package application;


//mateusz.lepicki@gmail.com

import java.net.*;

public class Serwer {
	private static int PORT = 8888;
	private static ServerSocket serverSocket = null;
	private static Socket gracz1Socket = null;
	private static Socket gracz2Socket = null;
	
    public static void main(String[] args) throws Exception {
        try{
        	serverSocket = new ServerSocket(PORT);
            System.out.println("Server is Running");
            while (true) {
            	gracz1Socket = serverSocket.accept();
            	gracz2Socket = serverSocket.accept();
            	Gra gra = new Gra();
            	Gra.Gracz graczX = gra.new Gracz(gracz1Socket, 'X');
            	Gra.Gracz graczO = gra.new Gracz(gracz2Socket, 'O');
            	graczX.przeciwnik = graczO;
            	graczO.przeciwnik = graczX;
                gra.obecnyGracz = graczX;
                graczX.start();
                graczO.start();
            }
        }finally {
        	serverSocket.close();
        }
    }
}

