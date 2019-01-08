
//mateusz.lepicki@gmail.com

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(8901)) {
            System.out.println("Server is Running");
            while (true) {
                Game game = new Game();
                Game.Player graczX = game.new Player(listener.accept(), 'X');
                Game.Player	graczO = game.new Player(listener.accept(), 'O');
                graczX.setOpponent(graczO);
                graczO.setOpponent(graczX);
                game.obecnyGracz = graczX;
                graczX.start();
                graczO.start();
            }
        }
    }
}

class Game {
    private Player[] board = {
        null, null, null,
        null, null, null,
        null, null, null};

    Player obecnyGracz;

    public boolean hasWinner() {
        return
            (board[0] != null && board[0] == board[1] && board[0] == board[2])
          ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
          ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
          ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
          ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
          ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
          ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
          ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean legalMove(int location, Player player) {
        if (player == obecnyGracz && board[location] == null) {
            board[location] = obecnyGracz;
            obecnyGracz = obecnyGracz.opponent;
            obecnyGracz.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    class Player extends Thread {
    	
        char mark;
        Player opponent;
        String name;
        int score;
        Socket socket;
        BufferedReader input;
        PrintWriter output;
        Connection con;
        private String query;
        
        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;
            this.score=0;
            this.con=null;
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println("WELCOME " + mark);
                output.println("MESSAGE Waiting for opponent to connect");
                String command = input.readLine();
                if (command.startsWith("NAME")) 
                	this.name = command.substring(5,command.length()-1);
            } catch (IOException e) {
                System.out.println("Gracz zgin¹³: " + e);
            }
            
        }

        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }

        public void otherPlayerMoved(int location) {
            output.println("OPPONENT_MOVED " + location);
            output.println(hasWinner() ? "DEFEAT" : boardFilledUp() ? "TIE" : "");
        }

        public void run() {
            try {
                output.println("MESSAGE All players connected");
                if (mark == 'X') {
                    output.println("MESSAGE Your move");
                }
                while (true) {
                    String command = input.readLine();
                    if (command.startsWith("MOVE")) {
                        int location = Integer.parseInt(command.substring(5));
                        if (legalMove(location, this)) {
                            output.println("VALID_MOVE");
                            output.println(hasWinner() ? "VICTORY" : boardFilledUp() ? "TIE" : "");
                            if(hasWinner()) {
                            	if(con == null)
                            		con = Connect.connect();
                            	if (con==null) {
                    				System.out.println("Connection failed.");
                            	}else {
                            		score = 1;
                            		query = "INSERT INTO wyniki VALUES('"+name+"',"+score+");";
                            		Statement stmt;
									try {
										stmt = con.createStatement();
										stmt.executeUpdate(query);
									} catch (SQLException e) {
										e.printStackTrace();
									}
                            	}
                            }
                        } else {
                            output.println("MESSAGE ?");
                        }
                    } else if (command.startsWith("QUIT")) {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("Gracz zgin¹³: " + e);
            } catch (SQLException e) {
				e.printStackTrace();
			} finally {
                try {
                	socket.close();
                	} catch (IOException e) {}
            }
        }
    }
}