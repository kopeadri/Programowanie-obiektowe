import java.awt.*;

public class Rectangle extends Shape{
	public String name ="rectangle";
	
	public int x;
	public int y;
	
	Rectangle(int a, int b){
		x = a;
		y = b;
	}
	public void draw (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.YELLOW);
		g2.drawRect(x, y, 100, 100);
		g2.fillRect(x, y, 100, 100);
	}
	
	public String getName() {
		return name;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int a) {
		x = a;
	}
	public void setY(int b) {
		y = b;
	}
}
