package application;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;


public class Main extends Application {
	private BorderPane root;
	private BorderPane tablePane;
	private Button buttons[];
	private static Button searchButton;
	private static Button insertButton;
	private Label menuLabel;
	private Scene scene;
	private TableView<Book> table;
	private String query;
	private static String authorSearch;
	private static String isbnSearch; 
	private static String isbnInsert;
	private static String titleInsert;
	private static String authorInsert;
	private static String yearInsert;
	private static TextField authorSearch_;
	private static TextField isbnSearch_;
	private static TextField insertFields[];
	
	private Connection con;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			con=null;
			menu();
			
			for(int i=1; i<4; i++) {
				if(con == null)
					con = Connect.connect();
			}
			if (con==null) {
				System.out.println("Connection failed.");
			}
			//db = new DataBase("SELECT * FROM books");
			
			scene= new Scene(root,650,700);
			primaryStage.setScene(scene);
			authorSearch = new String();
			isbnSearch = new String();
			
			buttons[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		           public void handle(MouseEvent t) {
					try {
						query = "SELECT * FROM books";
						root.setBottom(showTable(query));
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}); 
			
			buttons[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		           public void handle(MouseEvent t) {
						Stage tempStage = search();							
						searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
					           public void handle(MouseEvent t) {
								authorSearch = authorSearch_.getText();				
								isbnSearch = isbnSearch_.getText();
								if(!isbnSearch.equals("") )
									query = "SELECT * FROM books where isbn="+isbnSearch;
								else if(!authorSearch.equals(""))
									query = "SELECT * FROM books where author like'% "+authorSearch+"'";
								if(!isbnSearch.equals("") || !authorSearch.equals("")) {
									try {
										root.setBottom(showTable(query));
										tempStage.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}			
							}
						});
						
				}
			});
			
			buttons[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		           public void handle(MouseEvent t) {
						System.out.println("Halo");
						insertFields = new TextField[4];
						for(int i=0; i<4; i++)
							insertFields[i] = new TextField();
						Stage insertStage = insert();

						insertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
					        public void handle(MouseEvent t) {							
								Stage tempWindow = new Stage();
								VBox vBox = new VBox(6);
								Label label = new Label();
								Button okButton = new Button("OK");
						
								label.setFont(Font.font("Times New Roman", 20));
								okButton.setStyle("-fx-background-color: linear-gradient(cadetblue, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white");
								okButton.setFont(Font.font("Times New Roman", 20));
						
								vBox.setAlignment(Pos.CENTER);
								vBox.setPadding(new Insets(10));
								vBox.setStyle("-fx-background-color: silver grey");
								
								
								isbnInsert = insertFields[0].getText();
								titleInsert = insertFields[1].getText();
								authorInsert = insertFields[2].getText();
								yearInsert = insertFields[3].getText();
								if(!isbnInsert.equals("") & !titleInsert.equals("") & !authorInsert.equals("") & !yearInsert.equals("")) {
									query="INSERT INTO books VALUES('"+isbnInsert+"','"+titleInsert+"','"+authorInsert+"',"+yearInsert+")";
									Statement stmt;
									try {
										stmt = con.createStatement();
										stmt.executeUpdate(query);
										insertStage.close();
										label.setText("Added successfully.");
									} catch (SQLException e) {
										label.setText("Adding failed.Try again!");
										e.printStackTrace();
									}
									Scene scene2 = new Scene(vBox);
									vBox.getChildren().addAll(label, okButton);
									tempWindow.setTitle("");
									tempWindow.setScene(scene2);
									tempWindow.show();
									okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
										@Override
								        public void handle(MouseEvent t) {
											tempWindow.close();
										}
									});
								}
							}
						});
				}
			});
			
			
			int i=0;
			for(i=0; i<3; i++){
				int k =i;
				buttons[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						buttons[k].setScaleX(1.25);
						buttons[k].setScaleY(1.25);
					}
				});

				buttons[i].setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override public void handle(MouseEvent e) {
						buttons[k].setScaleX(1);
						buttons[k].setScaleY(1);
					}
				});
			}
			
			primaryStage.setTitle("DataBase");
			primaryStage.sizeToScene();
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			//scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Playfair+Display");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void menu() {
		root = new BorderPane();
		root.setPrefWidth(500);
		root.setPrefHeight(500);
		root.setStyle("-fx-background-color: silver grey");
		buttons = new Button[3];
		buttons[0] = new Button("Show all data");
		buttons[1] = new Button("Search data");
		buttons[2] = new Button("Add new book");
		
		BorderPane label = new BorderPane();
		//label.setStyle("-fx-background-color: slategrey");
		
		menuLabel = new Label("Menu");
		menuLabel.setFont(Font.font("Elephant", 80));
		menuLabel.setTextFill(Color.web("#9a5b5b"));
		menuLabel.setEffect( new InnerShadow());
		label.setStyle("-fx-background-color: linear-gradient(cadetblue, #be1d00)");
		label.setCenter(menuLabel);
		
		//buttons[0].setFont(Font.font("Toledo", 20));
		
		//buttons[2].setFont(Font.font("Toledo", 20));
		//menuLabel.setStyle("-fx-font-family: Playfair+Display; -fx-font-size: 50;");
		for(int i=0; i<3; i++) {
			buttons[i].setStyle("-fx-background-color: linear-gradient(cadetblue, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white");
			buttons[i].setFont(Font.font("Times New Roman", 25));
		}
		
		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
	    menu.setSpacing(8);
	    menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(label,buttons[0],buttons[1],buttons[2]);
		root.setTop(menu);
		root.setPadding(new Insets(10));
	}
	
	public BorderPane showTable(String query) throws SQLException {
		tablePane = new BorderPane();
		//tablePane.setPrefSize(500, 500);
		table = new TableView<Book>();
		
		/*
		Connect con=null;
		int i=1;
		while(con == null && i<4) {
			con = new Connect();
			i++;
		}
		if(con == null && i==3) {
			System.out.println(i);
			Label label = new Label("Connection failed.");
			tablePane.setCenter(label);
			return tablePane;
		}
		*/
		ObservableList<Book> listOfBooks = Books.getAllRecords(con,query);
		
		
		TableColumn<Book,String> isbn = new TableColumn<Book,String>("isbn");
		isbn.setCellValueFactory(new PropertyValueFactory<Book,String>("isbn"));
		TableColumn<Book,String> title = new TableColumn<Book,String>("title");
		title.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		TableColumn<Book,String> author = new TableColumn<Book,String>("author");
		author.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
		TableColumn<Book,String> year = new TableColumn<Book,String>("year");
		year.setCellValueFactory(new PropertyValueFactory<Book,String>("year"));
		 
		table.setItems(listOfBooks); 
		table.getColumns().addAll(isbn, title, author, year);
		table.setEditable(false);
		tablePane.setCenter(table);
		/*
		Scene scene2 = new Scene(tablePane);
        Stage newWindow = new Stage();
		newWindow.setTitle("");
		newWindow.setScene(scene2);

		newWindow.show();
		*/ 
		return tablePane;
	}
	
	public static Stage search() {
		Label search = new Label("Search by:");
		Label isbn = new Label("Isbn:");
		Label author = new Label("Author's\nsurname:");
		isbnSearch_ = new TextField();
		isbnSearch_.setPrefColumnCount(15);
		isbnSearch_.setOnKeyTyped(maskNotDigit());
		authorSearch_ = new TextField();
		authorSearch_.setPrefColumnCount(15);
		authorSearch_.setOnKeyTyped(maskNotLetter());
		searchButton = new Button("Search");
		
		BorderPane pane = new BorderPane();
		HBox authorH = new HBox(5);
		HBox isbnH = new HBox(5);
		HBox buttonH = new HBox(5);
		VBox box = new VBox(6);
		
		search.setFont(Font.font("Elephant", 40));
		search.setTextFill(Color.web("#9a5b5b"));
		search.setEffect( new InnerShadow());
		box.setPadding(new Insets(10,10,10,10));
	    isbn.setFont(Font.font("Times New Roman", 20));
	    author.setFont(Font.font("Times New Roman", 20));
	    
	    searchButton.setStyle("-fx-background-color: linear-gradient(cadetblue, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white");
		searchButton.setFont(Font.font("Times New Roman", 20));
		//autorH.setPadding();
		pane.setTop(search);
		isbnH.getChildren().addAll(isbn,isbnSearch_);
		authorH.getChildren().addAll(author,authorSearch_);
		buttonH.getChildren().add(searchButton);
		box.getChildren().addAll(isbnH,authorH,buttonH);
		buttonH.setMargin(searchButton, new Insets(30,0,0,150));
		authorH.setMargin(authorSearch_, new Insets(10,0,0,0));
		pane.setCenter(box);
		
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: silver grey");
		Scene scene2 = new Scene(pane,350,250);
		
        Stage newWindow = new Stage();
		newWindow.setTitle("Search");
		newWindow.setScene(scene2);
		
		
		newWindow.show();
		return newWindow;
	}
	
	private static Stage insert() {
		Label insert = new Label("Insert book:");
		Label isbn = new Label("Isbn:");
		Label title = new Label("Title:");
		Label author = new Label("Author:");		
		Label year = new Label("Year:");
		insertButton = new Button("Insert");
		
		BorderPane pane = new BorderPane();
		HBox isbnH = new HBox(5);
		insertFields[0].setPrefColumnCount(13);
		insertFields[1].setPrefColumnCount(13);
		insertFields[3].setPrefColumnCount(13);
		insertFields[0].setOnKeyTyped(maskNotDigit());
		insertFields[2].setOnKeyTyped(maskNotLetter());
		insertFields[3].setOnKeyTyped(maskNotDigit());
		
		HBox titleH = new HBox(5);
		HBox authorH = new HBox(5);
		HBox yearH = new HBox(5);
		HBox buttonH = new HBox(5);
		VBox box = new VBox(6);
		
		insert.setFont(Font.font("Elephant", 40));
		insert.setTextFill(Color.web("#9a5b5b"));
		insert.setEffect( new InnerShadow());
		insertButton.setStyle("-fx-background-color: linear-gradient(cadetblue, #be1d00); -fx-background-radius: 30; -fx-background-insets: 0; -fx-text-fill: white");
		insertButton.setFont(Font.font("Times New Roman", 20));
		box.setPadding(new Insets(10,10,10,10));
	    isbn.setFont(Font.font("Times New Roman", 20));
	    author.setFont(Font.font("Times New Roman", 20));
	    title.setFont(Font.font("Times New Roman", 20));
	    year.setFont(Font.font("Times New Roman", 20));
	    buttonH.setMargin(insertButton, new Insets(30,0,0,100));
	    
		pane.setTop(insert);
		isbnH.getChildren().addAll(isbn,insertFields[0]);
		titleH.getChildren().addAll(title,insertFields[1]);
		authorH.getChildren().addAll(author,insertFields[2]);
		yearH.getChildren().addAll(year,insertFields[3]);
		buttonH.getChildren().add(insertButton);
		box.getChildren().addAll(isbnH,titleH,authorH,yearH,buttonH);
		pane.setCenter(box);
		
		pane.setPadding(new Insets(10));
		pane.setStyle("-fx-background-color: silver grey");
		
		Scene scene2 = new Scene(pane);
        Stage newWindow = new Stage();
		newWindow.setTitle("");
		newWindow.setScene(scene2);
		
		
		newWindow.show();
		return newWindow;
	}
	
	public static EventHandler<KeyEvent> maskNotDigit() {		//mozliwosc wpisania tylko - i cyfr
		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e) {
            	for(int i=0; i<e.getCharacter().length(); i++) {
            		char c = e.getCharacter().charAt(i);
            		if (!Character.isDigit(c)) 
            			e.consume();
            	}
            }
        };
        return event;
	}

	public static EventHandler<KeyEvent> maskNotLetter() {		//mozliwosc wpisania .
		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e) {
            	for(int i=0; i<e.getCharacter().length(); i++) {
            		char c = e.getCharacter().charAt(i);
            		if (!Character.isLetter(c) && c != ' ' && c != '-')
            			e.consume();
            	}
            }
        };
        return event;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
