
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Cryptographer{
	static void cryptfile(String rFile, String wFile, Algorithm algorithm) throws Exception{
		try {
				FileReader fReader = new FileReader(rFile);
				BufferedReader bufferReader = new BufferedReader(fReader);
				PrintStream pStream = new PrintStream(wFile);

				String textLine = bufferReader.readLine();
				do {
					int i = 0;
					while(i < textLine.length()) {
						String word = "";
						char c = textLine.charAt(i);
						while(c != ' ') {
							c = textLine.charAt(i);             	 			
							word += c;
							i++;
							if(i == textLine.length()) {
								//System.out.println("dupa");
								break;
							}
							c = textLine.charAt(i);
						}
						//System.out.println(word);
						//System.out.println(algorithm.crypt(word));
						pStream.print(algorithm.crypt(word)+" ");
						i++;
					}
					pStream.println();            
					//pStream.println(textLine);
                                
					textLine = bufferReader.readLine();
				} while (textLine != null);
				bufferReader.close();
				pStream.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void decryptfile(String rFile, String wFile, Algorithm algorithm) throws Exception{
		try {
				FileReader fReader = new FileReader(rFile);
				BufferedReader bufferReader = new BufferedReader(fReader);
				PrintStream pStream = new PrintStream(wFile);

				String textLine = bufferReader.readLine();
				do {
					int i = 0;
					while(i < textLine.length()) {
						String word = "";
						char c = textLine.charAt(i);
						while(c != ' ') {
							c = textLine.charAt(i);             	 			
							word += c;
							i++;
							if(i == textLine.length()) {
								//System.out.println("dupa");
								break;
							}
							c = textLine.charAt(i);
						}
						//System.out.println(word);
						//System.out.println(algorithm.decrypt(word));
						pStream.print(algorithm.decrypt(word)+" ");
						i++;
					}
					pStream.println();            
					//pStream.println(textLine);
                                
					textLine = bufferReader.readLine();
				} while (textLine != null);
				bufferReader.close();
				pStream.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	public static void main(String [] args){
		
		Algorithm rot = new ROT11();
		Cryptographer.cryptfile("src/plik.txt","src/zaszyfrowany.txt",rot);
		Cryptographer.decryptfile("src/zaszyfrowany.txt","src/odszyfrowany.txt",rot);
     }
    */
}

