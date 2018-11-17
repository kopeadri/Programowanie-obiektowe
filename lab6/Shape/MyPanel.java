import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.math.*;

public class MyPanel extends JPanel implements MouseListener,
MouseMotionListener {
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private LinkedList<Shape> figures = new LinkedList<Shape>();
	private int x_=10, y_=10;
	private Shape movingShape;
	private int x, y;
	
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
			x = movingShape.getX() - e.getX();
			y = movingShape.getY() - e.getY();
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
			int width = getWidth();
			int height = getHeight();
			if(e.getX()+x <0)
				movingShape.setX(0);
			else if(e.getX()+x +100 > width)
				movingShape.setX(width-100);
			else if(e.getY()+y <0)
				movingShape.setY(0);
			else if(e.getY()+y +100> height)
				movingShape.setY(height-100);
			else {
				movingShape.setX(e.getX()+x);
				movingShape.setY(e.getY()+y);
			}
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE); //ustawiamy t³o
		g2d.fillRect(0, 0, getWidth(), getHeight());
		for (Shape x: figures)
			x.draw(g);
		
	}
}
