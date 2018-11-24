

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class Charts extends JFrame {

	public Charts() {
		
		super("Charts");
		MyPanel panel = new MyPanel();
		add(panel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Charts();
			}
		});
	}
	
}
