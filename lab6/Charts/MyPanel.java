package charts;


import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class MyPanel extends JPanel implements ActionListener{

		public static final int HEIGHT = 800;
		public static final int WIDTH = 800;
		private JButton Button;
		private JTextField [] fields; //pobieranie wspolczynnikow wielomianu

		private JTextField first;
		private JTextField last;
		private JTextField freq;
		
		private int [] coeffs; //wspolczynniki wielomianu
		private int first_,last_;
		private double freq_;
		private LinkedList<Double> setOfX;
		private LinkedList<Double> setOfY;
		private PanelChart panelChart;
		private int k; //licznik wcisniecia Button
		
		MyPanel() {
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			
			Button = new JButton("Draw");
			coeffs = new int[6];
			fields = new JTextField[6];
			for(int i =0; i <6; i++)
				fields[i] = new JTextField(2);	
			first = new JTextField(3);
			last = new JTextField(3);
			freq = new JTextField(3);
			first_=last_=0;
			freq_=0;
			k=0;
			
			Button.addActionListener(this);
			
			for(JTextField x : fields)					//ograniczenia wpisywanych danych
				x.addKeyListener(maskNotDigit());
			first.addKeyListener(maskNotDigit());
			last.addKeyListener(maskNotDigit());
			freq.addKeyListener(maskNotDigitNotDot()); //czêstotliwoœæ mo¿liwa double
			
			setLayout(new FlowLayout());
			
			
			add(new JLabel("W(x)="));
			add(fields[0]);
			add(new JLabel("<html>x<sup>5</sup>+</html>"));
			add(fields[1]);
			add(new JLabel("<html>x<sup>4</sup>+</html>"));
			add(fields[2]);
			add(new JLabel("<html>x<sup>3</sup>+</html>"));
			add(fields[3]);
			add(new JLabel("<html>x<sup>2</sup>+</html>"));
			add(fields[4]);
			add(new JLabel("<html>x<sup></sup>+</html>"));
			add(fields[5]);
			add(new JLabel("Range: from"));
			add(first);
			add(new JLabel("to"));
			add(last);
			add(new JLabel("Frequency:"));
			add(freq);

			add(Button);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == Button) {
				k++;
				chart();
				if(k>1)
					remove(panelChart);
				panelChart = new PanelChart(setOfX, setOfY, first_, last_, freq_);
				add(panelChart);
				revalidate();
				
			}
			
		}
		
		
		public KeyAdapter maskNotDigit() {		//mozliwosc wpisania tylko - i cyfr
			
			KeyAdapter keyadapter = new KeyAdapter() {
	            public void keyTyped(KeyEvent e) {
	            	if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '-') {
	                    e.consume();
	            	}    
	            }
	        };
	        return keyadapter;
		}
		
		public KeyAdapter maskNotDigitNotDot() {		//mozliwosc wpisania .
			KeyAdapter keyadapter = new KeyAdapter() {
	            public void keyTyped(KeyEvent e) {
	            	if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '.')
	                    e.consume();
	            }
	        };
	        return keyadapter;
		}
		
		public void chart() {
			try {
				for(int i = 0; i<6; i++)
					if((fields[i].getText()).equals(""))
						coeffs[i] = 0;
					else
						coeffs[i] = Integer.parseInt(fields[i].getText());
				if(first.getText().equals("")) 
					first_ = 0;
				else
					first_ = Integer.parseInt(first.getText());
			
				if(last.getText().equals("")) 
					last_ = 10;
				else
					last_ = Integer.parseInt(last.getText());
			
				if(freq.getText().equals("")) 
					freq_ = 1;
				else
					freq_ = Double.parseDouble(freq.getText());
			}catch(NumberFormatException e) {
				System.out.print("Wrong format of number");
				e.printStackTrace();
			}
			
			setXAndY();
			
		}
		
		public double function(double x) {
			double y;
			y = 0;
			double j=5.0;
			for(int i = 0; i<6; i++) {
				y += coeffs[i]*Math.pow(x, j);
				j--;
			}
			return y;
		}
		
		public void setXAndY() {
			setOfX = new LinkedList<Double>();
			setOfY = new LinkedList<Double>();
			double x = first_;
			
			while( x <= last_) {
				System.out.println(x);
				setOfX.add(x);
				setOfY.add(dRound(function(x)));
				 x = dRound(x+freq_);
			}

		}
		public double dRound(double x) {
			x *= 100;
			x = Math.round(x);
			x /= 100;
			return x;
		}
		/*
		public LinkedList<Double> getSetOfX(){
			return setOfX;
		}
		public LinkedList<Double> getSetOfY(){
			return setOfY;
		}
		*/
}

