package c;

import ab.*;

public class C extends B{
	public C( int cNumber, String cName){
		super(cNumber, cName);
	}
	
	//- Nie mo¿na nadpisaæ metody niedostêpnej dla innej paczki.
	//void changeName(){name = "NameC";} - 
	
}

