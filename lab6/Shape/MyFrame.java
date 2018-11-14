import java.awt.*;
import java.util.*;

import javax.swing.*;

public class MyFrame extends JFrame {

	public MyFrame() {
		super("Shapes");
		MyPanel panel = new MyPanel();

		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {

		
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyFrame();
			}
		});
	}
	
}
