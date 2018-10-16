package lab2_1;

public class A{
	protected int number;
	protected String name;
	
	public A( int number, String name){
		this.number = number;
		this.name = name;
	}
	
	public int getNumber(){return number;}
	public String getName(){return name;}
	public void callDecrement(){decrement();}
	public void callChangeName(){changeName();}
	public void callIncrement(){increment();}
	private void increment(){++number;}
	protected void decrement(){--number;}
	protected void changeName(){name = "NameA";}
}

/*
class B extends A{
	public B(int number, String name){
		super(number,name);
	}
	
	protected void decrement(){ number -= 2;}
	void changeName(){name = "NameB";}
	private void increment(){ number += 2;}
}
*/

	
