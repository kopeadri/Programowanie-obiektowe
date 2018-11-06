import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class MicroDVD{
	public void delay(final String fileIn, final String fileOut, int delay, int fps) {
	 	double fpms	 = fps * 0.001; //zamiana na : liczba klatek na milisekundê 
	 	int shift = (int)(delay*fpms);
		try {		
			FileReader fReader = new FileReader(fileIn);
			BufferedReader bufferReader = new BufferedReader(fReader);
			PrintStream pStream = new PrintStream(fileOut);
	
			String textLine = bufferReader.readLine();
			
			do {
				int i = 1;
				String[] tablica = new String[2];
				tablica[0] = "";
				tablica[1] = "";
				int kl1, kl2;
				if (textLine.charAt(0) == '{') {
					char c = textLine.charAt(i);
					while(c  != '}' ) {
						tablica[0] += c;
						i++;
						c = textLine.charAt(i);
					}
					i+=2;
					c = textLine.charAt(i);
					while(c  != '}' ) {
						tablica[1] += c;
						i++;
						c = textLine.charAt(i);
					}
					kl1 = Integer.parseInt(tablica[0]);
					kl2 = Integer.parseInt(tablica[1]);
					if( kl1 > kl2)
						throw new FirstBiggerThanSecondException("Nieprawid³owa sekwencja znaków: {"+tablica[0] +"}{"+tablica[1]+"}") ;
					kl1 += shift;
					kl2 += shift;
					tablica[0] = String.valueOf(kl1);
					tablica[1] = String.valueOf(kl2);
					
					pStream.print("{" + tablica[0] + "}{"+tablica[1]+"}"+ textLine.substring(i+1));
				}else{
					pStream.print(textLine);
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
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
		catch (FirstBiggerThanSecondException e) {
			e.printStackTrace();
		}	
	}
	
}


