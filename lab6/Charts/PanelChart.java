import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.LinkedList;

import javax.swing.JPanel;

import library.ChartHelp;


public class PanelChart extends JPanel {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 700;
	private LinkedList<Double> setOfX;
	private LinkedList<Double> setOfY;
	private LinkedList<Integer> setOfXOnScreen;
	private LinkedList<Integer> setOfYOnScreen;
	private int first,last;
	private double freq,min,max;
	private ChartHelp chartHelp;
	
	PanelChart(LinkedList<Double> _setOfX,LinkedList<Double> _setOfY, int _first, int _last, double _freq){
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOfX = _setOfX;
		setOfY = _setOfY;
		first = _first;
		last = _last;
		freq = _freq;
		min=max=0;
		chartHelp = new ChartHelp();
		min = chartHelp.findMin(min,setOfY);
		max = chartHelp.findMax(max,setOfY);
		//findMin();
		//findMax();
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
		g2d.drawString("x", 780, 665);
		g2d.drawLine(790, 650, 785, 645);
		g2d.drawLine(790, 650, 785, 655);
		
		g2d.drawString(String.valueOf(first), 50, 670);
		
		
		
		//osX
		double n = (last-first)/freq ; //liczba przedzialow 
		int r = (int)Math.round(700/(n+1)); //wartoœæ przedzialu w pikselach
		int temp=0;
		System.out.println(n);
		System.out.println(r);
		if(first == last) {
			g2d.drawString(String.valueOf(first), 300, 670);
			g2d.drawLine(300, 648, 300, 652);
			setOfXOnScreen.add(300);
		}else {
			setOfXOnScreen.add(50);
			g2d.drawString(String.valueOf(first), 50, 670);
			if(n*r < 700 && r!=0) {
				for(int i= 1; i <= n; i++) {
						if(n<=30) {
							g2d.drawLine(50+i*r, 648, 50+i*r, 652);
							if(i%2==0) {
								g2d.drawLine(50+i*r, 646, 50+i*r, 654);
								g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
							}
						}else if(n>=30) {
							int u = (int)(n-(n%100))/100;
							//System.out.println(u);
							if(i%10==0) {
								if(r > 1 && u!=0) {
									g2d.drawLine(50+i*r, 648, 50+i*r, 652);
									if(i%u == 0) {
										g2d.drawLine(50+i*r, 646, 50+i*r, 654);
										g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
									}
								}
								else {
									g2d.drawLine(50+i*r, 648, 50+i*r, 652);
									if(i%(u+1) == 0) {
										g2d.drawLine(50+i*r, 646, 50+i*r, 654);
										g2d.drawString(String.valueOf(setOfX.get(i)), 50+i*r, 670);
									}
								}
							}
						}
						
					
						setOfXOnScreen.add(50+i*r);
						temp = i;
				}
			}else {
				n= 30;
				r = (int)Math.round(700/n);
				if(r==0)
					r = 1;
				double v1 = (last-first)/n;
				double p1 = chartHelp.dRound(v1/r);
				for(int i= 1; i <= n; i++) {
					if(first != last) {
						g2d.drawLine(50+i*r, 648, 50+i*r, 652);					
						if(i%2==0) {
							g2d.drawLine(50+i*r, 646, 50+i*r, 654);
							g2d.drawString(String.valueOf(chartHelp.dRound(first+i*v1)), 50+i*r, 670);
						}
					}
				}
				for(int i = 0; i < setOfX.size(); i++) {
					double a = setOfX.get(i);
					
					for(int j=0; j < n; j++) {	//sprawdzamy ka¿dy przedzia³ na osi
							if(a == chartHelp.dRound(first + j*v1) && j!=0) {	//y równy dolnej granicy przedzialu
								setOfXOnScreen.add(50+j*r); 
								break;
							}
							else if(a == chartHelp.dRound(first + (j+1)*v1)) {		//y rowny gornej granicy przedzialu
								setOfXOnScreen.add(50+(j+1)*r);
								break;
							}
							else if(a > first + j*v1 && a <first + (j+1)*v1) {	
								for(int k=1; k<r; k++) { //sprawdzam przedzia³y rowne 1 pikselowi
									if(a>first+j*v1+(k-1)*p1 && a<= first+j*v1+k*p1) {
										setOfXOnScreen.add(50+j*r+k);
										break;
									}
									if(k==r-1 && a>first+j*v1+k*p1 && a<first + (j+1)*v1 )
										setOfXOnScreen.add(50+(j+1)*r);
								}
							}
						
					}
			}
		}
	
		//osY
        int n1=30;  //liczba przedzia³ów
		int r1 = (int)Math.round(550/n1);  //wartosc przedzialu w pikselach
		double v = (max-min)/n1;	//wartosc przedzialu 
		//System.out.println(max+" "+min);
		//System.out.println(v);
		double p = chartHelp.dRound(v/r1);  //wartoœæ 1 piksela
		//System.out.println(k);
		
		for(int i= 1; i <= n1; i++) {
			if(min == max) {
				g2d.drawLine(45, 500, 55, 500);
				g2d.drawString(String.valueOf(min), 10, 500);
			}else{
				g2d.drawString(String.valueOf(min), 10, 650);
				g2d.drawLine(45, 650-i*r1, 55, 650-i*r1);
			
				if(i%2==0)
					g2d.drawString(String.valueOf(chartHelp.dRound(min+i*v)), 10, 650-i*r1);
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
					if(y == chartHelp.dRound(min + j*v)) {	//y równy dolnej granicy przedzialu
						setOfYOnScreen.add(650-j*r1); 
						break;
					}
					else if(y == chartHelp.dRound(min + (j+1)*v)) {		//y rowny gornej granicy przedzialu
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
		}}	
		
	
	}

}
