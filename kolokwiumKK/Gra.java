package application;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class Gra {
	Gracz obecnyGracz;
    private Gracz[] plansza = {null, null, null, null, null, null, null, null, null};

    public boolean jestZwyciezca() {
        if((plansza[0] != null && plansza[0] == plansza[1] && plansza[0] == plansza[2])
          ||(plansza[0] != null && plansza[0] == plansza[3] && plansza[0] == plansza[6])
          ||(plansza[0] != null && plansza[0] == plansza[4] && plansza[0] == plansza[8])
          ||(plansza[1] != null && plansza[1] == plansza[4] && plansza[1] == plansza[7])
          ||(plansza[2] != null && plansza[2] == plansza[5] && plansza[2] == plansza[8])          
          ||(plansza[2] != null && plansza[2] == plansza[4] && plansza[2] == plansza[6])
          ||(plansza[3] != null && plansza[3] == plansza[4] && plansza[3] == plansza[5])
          ||(plansza[6] != null && plansza[6] == plansza[7] && plansza[6] == plansza[8]))
        	return true;
        else
        	return false;	
    }

    public boolean pelnaPlansza() {
        for(Gracz x:plansza) 
        	if (x == null) return false;	
        return true;
    }

    public synchronized boolean dobryRuch(int x, Gracz gracz) {
        if (gracz == obecnyGracz && plansza[x] == null) {
        	plansza[x] = obecnyGracz;
            obecnyGracz = obecnyGracz.przeciwnik;
            obecnyGracz.ruchPrzeciwnika(x);
            return true;
        }
        return false;
    }

    class Gracz extends Thread {	
        char symbol;
        Gracz przeciwnik;
        String name;
        int wynik;
        
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        Connection con;
        private String query;
        
        public Gracz(Socket socket, char mark) {
            this.socket = socket;
            this.symbol = mark;
            this.wynik=0;
            this.con=null;
            
            try {
            	out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));     
                out.println("START " + mark);
                out.println("KOMUNIKAT Czekam na przeciwnika...");
                String komunikat = in.readLine();
                if (komunikat.startsWith("NAZWA")) 
                	this.name = komunikat.substring(6,komunikat.length());
            } catch (IOException e) {
                System.out.println("Gracz wyszed³." + e);
            }     
        }


        public void ruchPrzeciwnika(int x) {
            out.println("PRZECIWNIK " + x);
            if(jestZwyciezca()) {
            	wynik=0;
            	out.println("POKONANY ");
            	zapiszWynik();
            }else if(pelnaPlansza()) {
            	wynik=1;
            	out.println("REMIS");
            	zapiszWynik();
            }else
            	out.println("");
        }
        
        public void zapiszWynik(){
        	try {
        		if(con == null)
        			con = Connect.connect();
        		if (con==null) {
        			System.out.println("Connection failed.");
        		}else {
        				query = "INSERT INTO wyniki VALUES('"+name+"',"+wynik+");";
        				Statement stmt;
        				stmt = con.createStatement();
        				stmt.executeUpdate(query);
        		}
        	} catch (SQLException e) { 
					e.printStackTrace();
        	}
        }
        
        public void run() {
            try {
                if (symbol == 'X') {
                    out.println("KOMUNIKAT Ty zaczynasz");
                }
                while (true) {
                    String komunikat = in.readLine();
                    if (komunikat.startsWith("RUCH")) {
                        int x = Integer.parseInt(komunikat.substring(5));
                        if (dobryRuch(x, this)) {
                            out.println("DOBRY_RUCH");
                            if(jestZwyciezca()) {
                            	wynik = 2;
                            	out.println("ZWYCIEZCA ");  
                            	zapiszWynik();
                            }else if(pelnaPlansza()) {
                            	wynik = 1;
                            	out.println("REMIS");
                            	zapiszWynik();
                            }else
                            	out.println("");

                        } else {
                            out.println("KOMUNIKAT ?");
                        }
                    } else if (komunikat.startsWith("WYJDZ")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Gracz wyszed³: " + e);
            } finally {
                try {
                	socket.close();
                }catch(IOException e) {}
            }
        }
    }
}