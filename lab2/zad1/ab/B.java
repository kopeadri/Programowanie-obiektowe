package ab;

public class B extends A{
	public B(int bNumber, String bName){
		super(bNumber,bName);
	}
	
	protected void decrement(){ 
		number -= 2;
	}
	void changeName(){
		name = "NameB";
	}
	private void increment(){ //Nie mo¿na nadpisaæ metody private.
		number += 2;
	} 
}
