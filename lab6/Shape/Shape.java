import java.awt.*;

public abstract class Shape{
	public String name;
	public int x;
	public int y;
	
	public abstract void draw (Graphics g);
	public abstract String getName();
	public abstract int getX();
	public abstract int getY();
	public abstract void setX(int a);
	public abstract void setY(int b);
}
