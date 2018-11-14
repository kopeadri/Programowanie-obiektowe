import java.awt.*;

public class Triangle extends Shape{
	public String name ="triangle";
	
	public int x;
	public int y;
	
	Triangle(int a, int b){
		x = a;
		y = b;
	}
	public void draw (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Polygon polygon  = new Polygon(new int[] {x,x+50,x+100}, new int[] {y,y+100,y},3);
		g2.setColor(Color.RED);
		g2.drawPolygon(polygon);
		g2.fill(polygon);
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