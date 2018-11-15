import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.math.*;

public class MyPanel extends JPanel implements MouseListener,
MouseMotionListener {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	LinkedList<Shape> figures = new LinkedList<Shape>();
	int x_=10, y_=10;
	Shape movingShape;
	//ArrayList points = new ArrayList();
	
	MyPanel(){
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.makeList(x_,y_);
	}
	
	public void makeList(int x, int y) {
		figures.add(new Circle(x,y));
		figures.add(new Rectangle(x+110,y));
		figures.add(new Triangle(x+2*110,y));
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		
		int newX = e.getX();
		int newY = e.getY();
		// wspolrzedne figur
		int x, y;

		if (e.getButton() != MouseEvent.BUTTON3) {
			int size = figures.size();
			int index = size -1;
			Shape s;
			while (movingShape == null && index >= 0) {
				s = figures.get(index);
				x = (int) s.getX();
				y = (int) s.getY();
				if((s.getName()).equals("rectangle")) {
					if (newX >= x && newY >= y && newX <= x + 100 && newY <= y + 100)
						movingShape = s;
				}else if((s.getName()).equals("circle")) {
					if((Math.pow(newX-(x+50),2) + Math.pow(newY-(y+50),2) <= 50*50)) //rownanie kola
						movingShape = s;
				}else if((s.getName()).equals("triangle")) {
					if(newX >= x && newX <= x+100 && newY >= y && newY <= 2*newX+y-2*x && newY <=-2*newX + y + 2*x +200)
						movingShape = s;	
				}
				index--;
			}
		}
		repaint();
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		 movingShape = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (movingShape != null) {
			movingShape.setX( e.getX());
			movingShape.setY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	/*
	public void drawCircle(Graphics g,int x,int y) {
		Circle cir = new Circle(x,y);
		cir.draw(g);
		
	}
	
	public void drawRectangle(Graphics g,int x,int y) {
		Rectangle rec = new Rectangle(x,y);
		rec.draw(g);
	}
	
	public void drawTriangle(Graphics g,int x,int y) {
		Triangle tri = new Triangle(x,y);
		tri.draw(g);
	}
	*/
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE); //ustawiamy t³o
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		for (Shape x: figures)
			x.draw(g);
		
	}
}
