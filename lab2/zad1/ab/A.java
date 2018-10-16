package ab;

public class A{
	protected int number;
	String name;
	
	public A( int aNumber, String aName){
		this.number = aNumber;
		this.name = aName;
	}
	
	public int getNumber(){
		return number;
	}
	public String getName(){
		return name;
	}
	public void callDecrement(){
		decrement();
	}
	public void callChangeName(){
		changeName();
	}
	public void callIncrement(){
		increment();
	}
	private void increment(){
		++number;
	}
	protected void decrement(){
		--number;
	}
	void changeName(){
		name = "NameA";
	}
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

	
