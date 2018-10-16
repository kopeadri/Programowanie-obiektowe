import ab.*;
import c.C;

public class Main {
	public static void main(String[] args){
		A a = new A(5,"A");
		B b = new B(10,"B");
		C c = new C(11,"C");
		
		a.callChangeName();
		System.out.println(a.getName());
		a.callDecrement();
		System.out.println(a.getNumber());
		a.callIncrement();
		System.out.println(a.getNumber());
		
		b.callChangeName();
		System.out.println(b.getName());
		b.callDecrement();
		System.out.println(b.getNumber());
		b.callIncrement();
		System.out.println(b.getNumber());
		
		c.callChangeName();
		System.out.println(c.getName());
		c.callDecrement();
		System.out.println(c.getNumber());
		c.callIncrement();
		System.out.println(c.getNumber());
	}
}
