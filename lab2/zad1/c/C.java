package c;

import ab.*;

public class C extends B{
	public C( int cNumber, String cName){
		super(cNumber, cName);
	}
	
	//- Nie mo�na nadpisa� metody niedost�pnej dla innej paczki.
	//void changeName(){name = "NameC";} - 
	
}

