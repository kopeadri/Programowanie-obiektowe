import java.util.*;

public class Main {
	public static void main(String [] args) throws Exception{
		String inFile = args[0]; 
		String outFile = args[1];
		
		int x;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Co chcesz zrobiæ?\n"
				+ "1 - Szyfrowanie ROT11\n"
				+ "2 - Deszyfrowanie ROT11\n"
				+ "3 - Szyfrowanie Polibiusz\n"
				+ "4 - Deszyfrowanie Polibiusz");
		x = scanner.nextInt();
		
		//Cryptographer cryptographer = new Cryptographer();
		switch(x){
		case 1:
			Algorithm crot = new ROT11();
			Cryptographer.cryptfile(inFile,outFile,crot);
			break;
		case 2:
			Algorithm drot = new ROT11();
			Cryptographer.decryptfile(inFile,outFile,drot);
			break;
		case 3:
			Algorithm cpol = new Polibiusz();
			Cryptographer.cryptfile(inFile,outFile,cpol);
			break;
		case 4:
			Algorithm dpol = new Polibiusz();
			Cryptographer.decryptfile(inFile,outFile,dpol);
			break;
		default:
			System.out.println("B³êdne polecenie");
			break;
		}
		scanner.close();
	}
}
