package application;

import java.io.*;
import java.net.MalformedURLException;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.*;
 
public final class ImageBrowser extends Application {
 
	private File [] photos;
	private BorderPane root,button,bigPhoto;
	private DirectoryChooser dirChooser;
	private Button openButton;
	private FlowPane photosPane;
	private Scene scene,scene2;
	private LinkedList<Image> images;
	private LinkedList<BorderPane> panes;
	private LinkedList<ImageView> imageViews;
	
    @Override public void start(final Stage stage) {
        stage.setTitle("Photos Browsing");
        dirChooser = new DirectoryChooser();
        openButton = new Button("Select folder");
        root = new BorderPane();
        button = new BorderPane();
        
        button.setCenter(openButton);
        BorderPane.setMargin(button, new Insets(20, 0, 10, 0));
        
        
        openButton.setOnAction((final ActionEvent e) -> {
            File dir = dirChooser.showDialog(stage);
            if (dir != null) {
            	initPhotosPane();
                try {
					loadFiles(dir);
					
					scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
			            public void handle(MouseEvent t) {
							Rectangle2D screen = Screen.getPrimary().getVisualBounds();
							int height;
							int width;
			            	for(int i=0; i<panes.size(); i++) {
			            		if(t.getTarget() == panes.get(i) || t.getTarget() == imageViews.get(i)) {
			            			bigPhoto = new BorderPane();
			            			bigPhoto.setPrefSize(600, 500);
			            			height = (int)(images.get(i).getHeight());
			            			width = (int)(images.get(i).getWidth());
			            			if( height > screen.getHeight() || width > screen.getWidth()){
			            				height = (int)screen.getHeight()-50;
				            			width = (int)screen.getWidth();
			            			}
			            			bigPhoto.setCenter(view(images.get(i),height,width));
			            			scene2 = new Scene(bigPhoto);
			            	 
			            			Stage newWindow = new Stage();
			            			newWindow.setTitle("");
			            			newWindow.setScene(scene2);
			
			            			newWindow.show();
			            		}
			            			
			            	}		
			            }
			 
			        }); 
			        
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
                showImages();
            }
        });
        
        root.setPrefSize(1050,600);
        root.setTop(button);
        scene = new Scene (root);
        stage.setScene(scene);
        stage.show();
    }

   private void initPhotosPane() {
	   images = new LinkedList<>();
       panes = new LinkedList<>();
       imageViews= new LinkedList<>();
       
       photosPane = new FlowPane();
       photosPane.setVgap(5);
       photosPane.setHgap(5);
       photosPane.setPadding( new Insets(10, 5, 10, 5));
       
       ScrollPane sp = new ScrollPane();
       //sp.setVmax(photosPane.getHeight());
       sp.setPrefSize(1050, 500);
       sp.setFitToWidth(true);
       sp.setContent(photosPane);
       root.setCenter(sp);
   }

    private void loadFiles(File directory) throws MalformedURLException {
       photos = directory.listFiles(photoFilter);
       for (File x : photos) {
    	   //System.out.println(x.getAbsolutePath());
    	   //images.add(new Image("file:"+x.getAbsolutePath()));
    	   images.add(new Image(x.toURI().toURL().toString()));
       }
    }
    
    FilenameFilter photoFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String lowercaseName = name.toLowerCase();
			if (lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".png")) {
				return true;
			} else {
				return false;
			}
		}
	};
    
    private ImageView view(Image image, int height, int width) {
    	imageViews.add(new ImageView(image));
    	int x = imageViews.size() -1;
        imageViews.get(x).setFitHeight(height);
        imageViews.get(x).setFitWidth(width);
        imageViews.get(x).setPreserveRatio(true);
        return imageViews.get(x);
    }
    
    
    private void showImages() {
    	int i = 0;
    	for(Image image: images) {
    		panes.add(new BorderPane());
    		panes.get(i).setStyle("-fx-background-color: white" );
    		panes.get(i).setPrefSize(200,150);
    		panes.get(i).setCenter(view(image, 150, 200));
    		photosPane.getChildren().add(panes.get(i));
    		i++;
    	}
    }
	
	public static void main(String[] args) {
        Application.launch(args);
    }
}