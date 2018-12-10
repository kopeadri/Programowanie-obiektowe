package application;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChatClient extends Application implements Runnable{
	private Scene scene;
	private Stage chatStage;
	private BorderPane root;
	private Button drawButton,sendButton;
	private TextField message_;
	private TextArea text;
	private static String login;
	private static String host;
	private static String receiver;
	private static String msg;
	
	private int portNumber;
	private static Socket clientSocket;
	private static PrintStream os;
	private static DataInputStream is;
	private static BufferedReader inputLine;
	private static boolean closed;
	
	@Override
    public void start(Stage primaryStage) {
		portNumber = 2222;
		clientSocket = null;
		is = null;
		BufferedReader inputLine = null;
		closed = false;
		login();

	}
	public void openSocket() {
		try {
		      clientSocket = new Socket(host, portNumber);
		      inputLine = new BufferedReader(new InputStreamReader(System.in));
		      os = new PrintStream(clientSocket.getOutputStream());
		      is = new DataInputStream(clientSocket.getInputStream());
		    } catch (UnknownHostException e) {
		      System.err.println("Don't know about host " + host);
		    } catch (IOException e) {
		      System.err.println("Couldn't get I/O for the connection to the host "
		          + host);
		    }
	}
	
	public void crateThread() {
		if (clientSocket != null && os != null && is != null) {
		      try {
		        new Thread(new ChatClient()).start();
		        while (!closed) {
		          os.println(inputLine.readLine().trim());
		        }

		        os.close();
		        is.close();
		        clientSocket.close();
		      } catch (IOException e) {
		        System.err.println("IOException:  " + e);
		      }
		    }
	}
	
	public void run() {
	    String responseLine;
	    try {
	      while ((responseLine = is.readLine()) != null) {
	        System.out.println(responseLine);
	        if (responseLine.indexOf("*** Bye") != -1)
	          break;
	      }
	      closed = true;
	    } catch (IOException e) {
	      System.err.println("IOException:  " + e);
	    }
	  }
	public void chat() {
		chatStage = new Stage();
		drawButton = new Button("Draw");
		sendButton = new Button("Send");
		text = new TextArea();
		message_ = new TextField();
		root = new BorderPane();
	
		sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				msg = message_.getText();
			}		
		}); 
	
		drawButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				draw();
			}		
		}); 
		
		scene = new Scene(panel(),600,400);
		chatStage.setTitle("Chat");
		chatStage.setScene(scene);
		chatStage.show();
	}
	public BorderPane panel() {
		Label message = new Label("message:");
		message.setFont(Font.font("Times New Roman", 20));

		BorderPane pane = new BorderPane();
		HBox textH = new HBox(5);
		HBox messH = new HBox(5);
		VBox box = new VBox(6);
		text.setEditable(false);
		
		box.setPadding(new Insets(10,10,10,10));
		textH.getChildren().addAll(text);
		messH.getChildren().addAll(message,message_,sendButton, drawButton);
		box.getChildren().addAll(textH,messH);
		//buttH.setMargin(button, new Insets(30,0,0,150));
		//logH.setMargin(login_, new Insets(10,0,0,0));
		pane.setCenter(box);
		
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: silver grey");

		return pane;
	}
	
	public void chooseReceiver() {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Button chooseButton = new Button("Choose");
		Label receiver_ = new Label("Receiver:");
		TextField to = new TextField();
		HBox hbox = new HBox(5);
		VBox vbox = new VBox(5);
		hbox.getChildren().addAll(receiver_,to);
		vbox.getChildren().addAll(hbox,chooseButton);
		vbox.setPadding(new Insets(10,10,10,10));
		root.setCenter(vbox);
		root.setPadding(new Insets(10));
		root.setStyle("-fx-background-color: silver grey");
		
		chooseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {	
			@Override
			public void handle(MouseEvent t) {
				receiver = to.getText();
				stage.hide();
				chat();
			}
			
		});
		
		Scene scene = new Scene(root,300,200);
		stage.setTitle("Receiver");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void login() {
		Stage stage = new Stage();
		Button loginButton;
		BorderPane root;
		TextField login_,host_;

		Label loginLabel = new Label("login:");
		Label hostLabel = new Label("host:");
		
		login_ =  new TextField();
		host_ =  new TextField();
		
		loginLabel.setFont(Font.font("Times New Roman", 20));
	    hostLabel.setFont(Font.font("Times New Roman", 20));
	    
		loginButton = new Button("Log in");
		
		BorderPane pane = new BorderPane();
		HBox logH = new HBox(5);
		HBox passH = new HBox(5);
		HBox buttH = new HBox(5);
		VBox box = new VBox(6);
		
		box.setPadding(new Insets(10,10,10,10));
	    
		logH.getChildren().addAll(loginLabel,login_);
		passH.getChildren().addAll(hostLabel,host_);
		buttH.getChildren().add(loginButton);
		box.getChildren().addAll(logH,passH,buttH);

		
		loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {	
			@Override
			public void handle(MouseEvent t) {
				login = login_.getText();
				host = host_.getText();
				stage.hide();
				chooseReceiver();
			}
			
		});
	
		pane.setCenter(box);
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: silver grey");
		
		root= new BorderPane();
		root.setCenter(pane);
		Scene scene = new Scene(root,300,200);
		stage.setTitle("Log");
		stage.setScene(scene);
		stage.show();

	}
	public void draw() {
		Stage stage = new Stage();
		int width = 600;
		int hight = 550;
    	BorderPane root = new BorderPane();
    	BorderPane bPane = new BorderPane();
        bPane.setStyle("-fx-background-color: snow");
        BorderPane cPane = new BorderPane();
        cPane.setStyle("-fx-border-color: black; -fx-background-color: white");
        
        Button buttonSave = new Button("Save");
    	Canvas canvas = new Canvas(width, hight-50); 
    	GraphicsContext aPen = canvas.getGraphicsContext2D();
    	
    	canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
    				@Override
    				public void handle(MouseEvent event) {
    					aPen.beginPath();
    					aPen.moveTo(event.getX(), event.getY());
    					aPen.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
    					aPen.stroke();
    				}
    	});
   
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){
            		@Override
            		public void handle(MouseEvent event) {
            			aPen.lineTo(event.getX(), event.getY());
            			aPen.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
            			aPen.stroke();
            		}
        });
 
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
        		new EventHandler<MouseEvent>(){
        			@Override
        			public void handle(MouseEvent event) {
        			}
        });
    	
  
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent t) {
                File file = new File("drawing.png");
                 
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage(width, hight-50);
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                   stage.hide(); 
                }
                
            }
             
        });
        
       
        bPane.setCenter(buttonSave);
        cPane.setCenter(canvas);
        root.setTop(bPane);
        root.setCenter(cPane);     
        Scene scene = new Scene(root, width, hight); 
        stage.setResizable(false);
        stage.setTitle("Drawing");
        stage.setScene(scene);
        stage.show();
    }
	
	public static void main(String[] args) {
        launch(args);
    }
    
}
