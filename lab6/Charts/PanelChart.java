package charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JPanel;

public class PanelChart extends JPanel {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 700;
	private LinkedList<Double> setOfX;
	private LinkedList<Double> setOfY;
	private LinkedList<Integer> setOfXOnScreen;
	private LinkedList<Integer> setOfYOnScreen;
	private int first,last;
	private double freq,min,max;
	
	PanelChart(LinkedList<Double> _setOfX,LinkedList<Double> _setOfY, int _first, int _last, double _freq){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOfX = _setOfX;
		setOfY = _setOfY;
		first = _first;
		last = _last;
		freq = _freq;
		findMin();
		findMax();
		setOfXOnScreen = new LinkedList<Integer>();
		setOfYOnScreen = new LinkedList<Integer>();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE); //ustawiamy t³o
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		if(!setOfX.isEmpty() && !setOfY.isEmpty())
			drawChart(g);
	}
	
	public void drawChart(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.drawLine(50, 50, 50, 650);				//osie
		g2d.drawLine(50, 650, 790, 650);		
		g2d.drawString("y", 30, 70);
		g2d.drawLine(50, 50, 45, 55);
		g2d.drawLine(50, 50, 55, 55);
		g2d.drawString("x", 765, 665);
		g2d.drawLine(790, 650, 785, 645);
		g2d.drawLine(790, 650, 785, 655);
		
		g2d.drawString(String.valueOf(first), 50, 670);
		
		
		
		//osX
		double n = (last-first)/freq ; //liczba przedzialow 
		int r = (int)Math.round(700/(n+1)); //wartoœæ przedzialu w pikselach
		int temp=0;
		
		if(first == last) {
			g2d.drawString(String.valueOf(first), 300, 670);
			g2d.drawLine(300, 648, 300, 652);
			setOfXOnScreen.add(300);
		}else {
			setOfXOnScreen.add(50);
			for(int i= 1; i <= n; i++) {
				g2d.drawString(String.valueOf(first), 50, 670);
				//g2d.drawLine(50+i*r, 648, 50+i*r, 652);
				if(setOfX.size()<=31) {
					g2d.drawLine(50+i*r, 648, 50+i*r, 652);
					if(i%2==0) {
						g2d.drawLine(50+i*r, 646, 50+i*r, 654);
						g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
					}
				}else if(setOfX.size()<=201) {
					if(i%10==0) {
						g2d.drawLine(50+i*r, 646, 50+i*r, 654);
						g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
					}
				}else if(setOfX.size()<=2001) {
					if(i%100==0) {
						g2d.drawLine(50+i*r, 646, 50+i*r, 654);
						g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
					}
				}
					
				setOfXOnScreen.add(50+i*r);
				temp = i;
			//g2d.drawRect(setOfXOnScreen.get(i)-5, 500, 10,6);
			}
		}
		
		//osY
        int n1=30;  //liczba przedzia³ów
		int r1 = (int)Math.round(550/n1);  //wartosc przedzialu w pikselach
		double v = (max-min)/n1;	//wartosc przedzialu 
		//System.out.println(max+" "+min);
		//System.out.println(v);
		double p = dRound(v/r1);  //wartoœæ 1 piksela
		//System.out.println(k);
		
		for(int i= 1; i <= n1; i++) {
			if(min == max) {
				g2d.drawLine(45, 500, 55, 500);
				g2d.drawString(String.valueOf(min), 10, 500);
			}else{
				g2d.drawString(String.valueOf(min), 10, 650);
				g2d.drawLine(45, 650-i*r1, 55, 650-i*r1);
			
				if(i%2==0)
					g2d.drawString(String.valueOf(dRound(min+i*v)), 10, 650-i*r1);
			//if(i==n1)
				//System.out.println(650-i*r1);
			}
		}
		
		for(int i = 0; i < setOfX.size(); i++) {
			double y = setOfY.get(i);
			
			for(int j=0; j < n1; j++) {	//sprawdzamy ka¿dy przedzia³ na osi
				if(min == max)
					setOfYOnScreen.add(500);
				else {
					if(y == dRound(min + j*v)) {	//y równy dolnej granicy przedzialu
						setOfYOnScreen.add(650-j*r1); 
						break;
					}
					else if(y == dRound(min + (j+1)*v)) {		//y rowny gornej granicy przedzialu
						setOfYOnScreen.add(650-(j+1)*r1);
						break;
					}
					else if(y > min + j*v && y <min + (j+1)*v) {	
						for(int k=1; k<r1; k++) { //sprawdzam przedzia³y rowne 1 pikselowi
							if(y>min+j*v+(k-1)*p && y<= min+j*v+k*p) {
								setOfYOnScreen.add(650-j*r1-k);
								break;
							}
							if(k==r1-1 && y>min+j*v+k*p && y<min + (j+1)*v )
								setOfYOnScreen.add(650-(j+1)*r1);
						}
					}
				}
			}
		}
		
		for(int i =0; i <setOfXOnScreen.size();i++) {
			g2d.setColor(Color.blue);
			g2d.drawOval(setOfXOnScreen.get(i)-2, setOfYOnScreen.get(i)-2, 4,4);
			if (i < setOfXOnScreen.size()-1) {
				g2d.setColor(Color.green);
				g2d.drawLine(setOfXOnScreen.get(i), setOfYOnScreen.get(i), setOfXOnScreen.get(i+1), setOfYOnScreen.get(i+1));
			}
		}	
		

		
			
		
			
		
	}
	public double dRound(double x) {
		x *= 100;
		x = Math.round(x);
		x /= 100;
		return x;
	}
	public void findMin() {
		double temp=0;
		min = setOfY.get(0);
		for(int i=0; i<setOfY.size();i++) {
			temp = setOfY.get(i); 
			if(temp < min)
				min = temp;
		}
		//System.out.println("min"+ min);		
	}
	
	public void findMax() {
		double temp=0;
		max = setOfY.get(0);
		for(int i=0; i<setOfY.size();i++) {
			temp = setOfY.get(i); 
			if(temp > max)
				max = temp;
		}
		//System.out.println(max);		
	}
	
}
