package lab2_1;

public class B extends A{
	public B(int number, String name){
		super(number,name);
	}
	
	protected void decrement(){ number -= 2;}
	protected void changeName(){name = "NameB";}
	private void increment(){ number += 2;}
}
