//import java.lang.*;

public class ROT11 implements Algorithm{
	static final String alfabet = "abcdefghijklmnopqrstuvwxyz";//.toCharArray();
	int rotation = 11;

	public String crypt(String word){
		String newWord="";
		int index,newIndex;
		for(int i =0; i<word.length(); i++){
			boolean flag = false;				//kontrola wielkoœci znaku
			//System.out.println(word.charAt(i));
			char c =word.charAt(i); 			//znak do zaszyfrowania
			if (Character.isUpperCase(c)) {			//jeœli znak to du¿a litera
				c = Character.toLowerCase(c);
				flag = true;
			}
			index = alfabet.indexOf(c);			
			newIndex = index + rotation;
			if(newIndex > alfabet.length()-1)	//jeœli przesuniêcie wykracza poza rozmiar alfabetu
				newIndex =  newIndex - alfabet.length();
				if(flag)
					newWord += Character.toUpperCase(alfabet.charAt(newIndex));
				else
					newWord += alfabet.charAt(newIndex);
			}
		return newWord;
	}
        
	public String decrypt(String word){
		String newWord="";
		int index,newIndex;
		for(int i =0; i<word.length(); i++){
			boolean flag = false;
			//System.out.println(word.charAt(i));
			char c =word.charAt(i); 			//znak do zaszyfrowania
			if (Character.isUpperCase(c)) {			//jeœli znak to du¿a litera
				c = Character.toLowerCase(c);
				flag = true;
			}
			index = alfabet.indexOf(c);
			newIndex = index - rotation;
			if(newIndex < 0)
				newIndex =  newIndex + alfabet.length();
				if(flag)
					newWord += Character.toUpperCase(alfabet.charAt(newIndex));
				else
					newWord += alfabet.charAt(newIndex);
			}
		return newWord;
	}
	/*
        public static void main(String [] args){
                System.out.println(ROT11.alfabet);
                ROT11 rot = new ROT11();
                String word = "Halo";
                System.out.println(rot.crypt(word));
                System.out.println(rot.decrypt(rot.crypt(word)));
        }
     */
}
