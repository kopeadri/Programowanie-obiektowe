package lab2_1_c;

import lab2_1.*;

public class C extends B{
	public C( int number, String name){
		super(number, name);
	}
	
	protected void changeName(){name = "NameC";}

	public static void main(String[] args){
		A a = new A(5,"Nazwa");
		B b = new B(10,"Halo");
	
		a.callChangeName();
		a.callDecrement();
		System.out.println(a.getName());
		System.out.println(a.getNumber());
		a.callIncrement();
		System.out.println(a.getNumber());
		
		b.callDecrement();
		System.out.println(b.getNumber());
		b.callChangeName();
		System.out.println(b.getName());
	}

}

