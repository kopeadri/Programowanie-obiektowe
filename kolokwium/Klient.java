package application;

import java.io.*;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Klient extends Application{
	private Scene scene;
	private BorderPane root;
	private Label komunikat,nazwaLabel,symbolLabel;
	private Image symbol,przeciwnySymbol;
	private TextField nazwa;
	private Button ok;
	private HBox nazwaPanel;
	
    private Pole[] plansza = new Pole[9];
    private Pole obecnePole;

    private static int PORT = 8888;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	socket = new Socket("localhost", PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        root = new BorderPane();
        komunikat = new Label("");
        symbolLabel = new Label("");
        nazwaLabel = new Label("Nazwa:");
        nazwa = new TextField();
        ok = new Button("OK");
        
        komunikat.setStyle("-fx-background-color: silver grey");
        //nazwa.setPreferredSize(new Dimension(200,24));
        GridPane planszaPanel = new GridPane();        
        //SplanszaPanel.setStyle("-fx-background-color: black");
        planszaPanel.setMinSize(400, 200); 
        planszaPanel.setPadding(new Insets(10, 10, 10, 10)); 
        planszaPanel.setVgap(5); 
        planszaPanel.setHgap(5);    
        planszaPanel.setAlignment(Pos.CENTER); 
        for (int i = 0; i < plansza.length; i++) {
            final int j = i;
            plansza[i] = new Pole();
            plansza[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		           public void handle(MouseEvent t) {
                    obecnePole = plansza[j];
                    out.println("RUCH " + j);}});
            planszaPanel.getChildren().add(plansza[i]);
            		
            
        }
        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
	           public void handle(MouseEvent t) {
					if (!nazwa.getText().equals(""))
						out.println("NAZWA " + nazwa.getText());
					graj();
					nazwaPanel.getChildren().add(symbolLabel);
					nazwaPanel.requestLayout();
				}});
        nazwaPanel = new HBox();
        nazwaPanel.setPadding(new Insets(15, 12, 15, 12));
        nazwaPanel.setSpacing(10);
        nazwaPanel.setStyle("-fx-background-color: #336699;"); //(255,218,185)

        nazwaPanel.getChildren().addAll(nazwaLabel,nazwa,ok);
        
        root.setTop(nazwaPanel);
        root.setCenter(planszaPanel);
        root.setBottom(komunikat);
        
        scene = new Scene(root,400,320);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Kó³ko i krzy¿yk");
        primaryStage.setScene(scene);
		
		
        primaryStage.show();
               
    }
    
    public void graj() {
        String odpowiedz;
        try {
        	odpowiedz = in.readLine();
            if (odpowiedz.startsWith("START")) {
                char symbol_ = odpowiedz.charAt(6);
                if(symbol_ == 'X') {
                	symbol = new Image(getClass().getResourceAsStream("x.jpg"));
                	przeciwnySymbol = new Image(getClass().getResourceAsStream("o.jpg"));
                }else {
                	symbol = new Image(getClass().getResourceAsStream("o.jpg"));
                	przeciwnySymbol = new Image(getClass().getResourceAsStream("x.jpg"));
                }
                symbolLabel.setText("Twój symbol: " + symbol_);
            }
            while (true) {
            	odpowiedz = in.readLine();
                if (odpowiedz.startsWith("DOBRY_RUCH")) {
                	komunikat.setText("Prawid³owy ruch.");
                    obecnePole.setSymbol(symbol);
                    //obecnePole.requestLayout();
                } else if (odpowiedz.startsWith("PRZECIWNIK")) {
                    int loc = Integer.parseInt(odpowiedz.substring(15));
                    plansza[loc].setSymbol(przeciwnySymbol);
                    //plansza[loc].requestLayout();
                    komunikat.setText("Twój ruch.");
                } else if (odpowiedz.startsWith("ZWYCIEZCA")) {
                	komunikat.setText("Wygra³eœ!");
                    break;
                } else if (odpowiedz.startsWith("POKONANY")) {
                	komunikat.setText("Przegra³eœ!");
                    break;
                } else if (odpowiedz.startsWith("REMIS")) {
                	komunikat.setText("Zremisowa³eœ!");
                    break;
                } else if (odpowiedz.startsWith("KOMUNIKAT")) {
                	komunikat.setText(odpowiedz.substring(8));
                }
            }
            out.println("WYJDZ");
        } catch (IOException e) {
			e.printStackTrace();
		}
        finally {
            try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    static class Pole extends BorderPane {
        Label label = new Label();
        
        public Pole() {
            setStyle("-fx-background-color: white");
            getChildren().add(label);
        }

        public void setSymbol(Image image) {
            label.setGraphic(new ImageView(image));
        }
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}