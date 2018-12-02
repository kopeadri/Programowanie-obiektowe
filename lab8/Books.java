package application;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Books {
		
	public static ObservableList<Book> getAllRecords(Connection con, String query) throws SQLException{
		ResultSet rs = con.createStatement().executeQuery(query);
		ObservableList<Book> bookList =  getBookObjects(rs);
		return bookList;
	}
	public static ObservableList<Book> getBookObjects(ResultSet rs) throws SQLException{		
		ObservableList<Book> bookList = FXCollections.observableArrayList();
		while(rs.next()) {
			Book book = new Book();
			book.setIsbn(rs.getString("isbn"));
			book.setTitle(rs.getString("title"));
			book.setAuthor(rs.getString("author"));
			book.setYear(rs.getString("year"));
			bookList.add(book);
		}
		return bookList;
	}
		
}
