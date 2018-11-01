
public class Polibiusz implements Algorithm {
	static final char[][] alfabet = new char[][] {{'a','b','c','d','e'},
	                               				  {'f','g','h','i','k'},
	                               				  {'l','m','n','o','p'},
	                               				  {'q','r','s','t','u'},
	                               				  {'v','w','x','y','z'}};
	
	public String crypt(String word) {
		String newWord ="";
		for(int i=0; i<word.length(); i++){
			boolean flag = false;	//kontrola znalezienia indeksu
			//System.out.println(word.charAt(i));
			char c =word.charAt(i); 			
			if (Character.isUpperCase(c))			
				c = Character.toLowerCase(c);
			if(c == 'j')
				c = 'i';
			int xIndex=0, yIndex=0;
			for(int x=0; x<5; x++) {
				for(int y=0; y<5; y++){
					if(c == alfabet[x][y]) {
						flag = true;
						xIndex = x+1;
						yIndex=y+1;
						break;
					}
				}
				if(flag)
					break;
			}
			newWord += Integer.toString(xIndex) + Integer.toString(yIndex);// + " ";
		}
		return newWord;
	};
	
    public String decrypt(String word) {
    	String newWord ="";
		for(int i=0; i<word.length(); i+=2){
			int xIndex = Integer.valueOf(word.charAt(i))-48;
			int yIndex = Integer.valueOf(word.charAt(i+1))-48;
			//System.out.println(xIndex);
			//System.out.println(yIndex);
			newWord += alfabet[xIndex-1][yIndex-1];
		}
		return newWord;
    };
    
    /*
    public static void main(String [] args) {
    	String word = "Scisle";
    	Polibiusz pol = new Polibiusz();
    	System.out.println(pol.crypt(word));
    	System.out.println(pol.decrypt(pol.crypt(word)));
    }
    */
}
