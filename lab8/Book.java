package application;

import javafx.beans.property.*;

public class Book {
	private StringProperty isbn;
	private StringProperty title;
	private StringProperty author;
	private StringProperty year;
	
	Book(){
		isbn = new SimpleStringProperty(this, "isbn");
		title = new SimpleStringProperty(this, "title");
		author = new SimpleStringProperty(this, "author"); 
		year = new SimpleStringProperty(this, "year");
	}
	
    public void setIsbn(String value) { 
    	isbnProperty().set(value); 
    }
    public String getIsbn() { 
    	return isbnProperty().get(); 
    }
    public StringProperty isbnProperty() { 
    	return isbn; 
    }

 
    public void setTitle(String value) { 
    	titleProperty().set(value); 
    }
    public String getTitle() { 
    	return titleProperty().get(); 
    }
    public StringProperty titleProperty() {   
        return title; 
    }
    
    
    public void setAuthor(String value) { 
    	authorProperty().set(value); 
    }
    public String getAuthor() { 
    	return authorProperty().get(); 
    }
    public StringProperty authorProperty() {  
        return author; 
    }
   
    public void setYear(String value) { 
    	yearProperty().set(value); 
    }
    public String getYear() { 
    	return yearProperty().get(); 
    }
    public StringProperty yearProperty() {    
        return year; 
    }
}
