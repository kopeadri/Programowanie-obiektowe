import java.awt.*;

public class Circle extends Shape{
	public String name = "circle";
	public int x;
	public int y;
	
	Circle(int a, int b){
		x = a;
		y = b;
	}
	public void draw (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		g2.drawOval(x, y, 100, 100);
		g2.fillOval(x, y, 100, 100);
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