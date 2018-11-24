package application;

import java.util.LinkedList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.input.*;

import library.ChartHelp;

public class Chart extends Application {
	private Scene scene;
	private Button button;
	private TextField [] fields;
	private Label Wx,from,to,freq_;
	private WebView [] webs;
	
	private int first,last;
	private double freq;
	private int [] coeffs;
	private LinkedList<Double> setOfX;
	private LinkedList<Double> setOfY;
	ChartHelp chartHelp;
	
	@Override
	public void start(Stage stage) throws  RangeException,Exception {
		initScene();
		setScene();
		stage.setTitle("Charts");	
		stage.setScene(scene);
		stage.show();
	}	
	
	public void initScene() {
		first=last=0;
		freq=0;
		fields = new TextField[9];
		for(int i=0; i<9; i++) {
			fields[i]= new TextField();
			fields[i].setPrefColumnCount(2);
		}
		coeffs = new int[6];
		webs = new WebView[5];
		button = new Button("Draw");
		Wx = new Label ("W(x)=");
		from = new Label("From");
		to = new Label("to");
		freq_ = new Label("Frequency:");
		Wx.setPrefHeight(30);
		getLabels();
		
		for(int i=0; i<9; i++) {	
			if(i != 8)
				fields[i].setOnKeyTyped(maskNotDigit());
			else
				fields[i].setOnKeyTyped(maskNotDigitNotDot());
		}
	}
	
	public void setScene() {
		BorderPane root = new BorderPane();
		BorderPane border1 = new BorderPane();
		BorderPane border2 = new BorderPane();
		BorderPane border3 = new BorderPane();
		BorderPane border4 = new BorderPane();
		BorderPane border5 = new BorderPane();
		root.setStyle("-fx-background-color: white");
		
		FlowPane flow1 = new FlowPane();
		FlowPane flow2 = new FlowPane();

		flow1.setPrefWidth(600);
	    FlowPane.setMargin(Wx, new Insets(20, 0, 20, 20));
		flow1.getChildren().addAll(Wx,fields[0],webs[4],fields[1],webs[3], fields[2],webs[2],fields[3],webs[1],fields[4],webs[0],fields[5]);
		flow2.getChildren().addAll(from,fields[6],to,fields[7],freq_,fields[8]);
		FlowPane.setMargin(fields[7], new Insets(10, 50, 10, 0));
		FlowPane.setMargin(from, new Insets(10, 0, 10, 50));
		FlowPane.setMargin(webs[0], new Insets(10, 0, 0, 0));
		flow2.setHgap(5);
		
		Group flowg1 = new Group(flow1);
		Group buttons = new Group(button);
		Group bord = new Group(border2);
		
		border1.setCenter(flowg1);
		BorderPane.setMargin(flow2, new Insets(0, 20, 10, 0));
		border2.setCenter(flow2);
		border3.setCenter(buttons);
		border5.setCenter(bord);
		border4.setTop(border1);
		border4.setCenter(border5);
		border4.setBottom(border3);
		root.setTop(border4);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent actionEvent) {
		    	Group chart = new Group(makeChart());
		    	root.setCenter(chart);
		    }
		});
		
		scene = new Scene (root,800,900);				
	}
	
	public void getLabels() {
		for(int i=0; i<5;i++) {
			String fontText="";
			webs[i] = new WebView();
			StringBuilder sb = new StringBuilder(fontText);
			if(i==0) {
				sb.append("<html>x+</html>");	
			}else
				sb.append("<html>x<sup>").append(i+1).append("</sup>+</html>");	
			fontText = sb.toString();
			webs[i].getEngine().loadContent(fontText);
			webs[i].setMaxHeight(39);
			webs[i].setPrefWidth(35);
		}
		
		
	}
	
	public void getData(){
		try {
			for(int i=0; i<6; i++) {
				if((fields[i].getText()).equals(""))
					coeffs[i] = 0;
				else
					coeffs[i] = Integer.parseInt(fields[i].getText());
			}
			if((fields[6].getText()).equals(""))
				first = -10;
			else
				first = Integer.parseInt(fields[6].getText());
			if((fields[7].getText()).equals(""))
				last = 10;
			else
				last = Integer.parseInt(fields[7].getText());
			if((fields[8].getText()).equals(""))
				freq = 1;
			else
				freq = Double.parseDouble(fields[8].getText());
			if(first>last)
				throw new RangeException("Wrong range");
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(RangeException e) {
			e.printStackTrace();
		}
	}
	
	
	public LineChart<Number,Number> makeChart() {
		getData();
		chartHelp = new ChartHelp();
		setOfX = new LinkedList<Double>();
		setOfY = new LinkedList<Double>();
		chartHelp.setXAndY(setOfX, setOfY, first, last, coeffs, freq);
		
		NumberAxis xAxis = new NumberAxis(); 
		xAxis.setLabel("x"); 
		NumberAxis yAxis = new NumberAxis();  
		yAxis.setLabel("y");
		
		LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
		XYChart.Series<Number,Number> series = new XYChart.Series<>(); 
		series.setName("Polynomial"); 
		for(int i=0; i<setOfX.size();i++)
			series.getData().add(new XYChart.Data<>(setOfX.get(i), setOfY.get(i))); 
		
		lineChart.getData().add(series);
		lineChart.setPrefSize(700, 700);
		lineChart.setCreateSymbols(false);
		return lineChart;
		
	}
	

	public EventHandler<KeyEvent> maskNotDigit() {		//mozliwosc wpisania tylko - i cyfr
		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e) {
            	for(int i=0; i<e.getCharacter().length(); i++) {
            		char c = e.getCharacter().charAt(i);
            		if (!Character.isDigit(c) && c != '-') 
            			e.consume();
            	}
            }
        };
        return event;
	}

	public EventHandler<KeyEvent> maskNotDigitNotDot() {		//mozliwosc wpisania .
		EventHandler<KeyEvent> event = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e) {
            	for(int i=0; i<e.getCharacter().length(); i++) {
            		char c = e.getCharacter().charAt(i);
            		if (!Character.isDigit(c) && c != '.')
            			e.consume();
            	}
            }
        };
        return event;
	}
	
	public static void main(String args[]){   
			launch(args);      
	} 
	
	
	
}

