import java.util.*;

public class Main {
	public static void main(String [] args) {
		String inFile = args[0]; 
		String outFile = args[1];
		
		int x,y;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Co chcesz zrobiæ?\n1 - Szyfrowanie\n2 - Deszyfrowanie");
		x = scanner.nextInt();
		System.out.println("Wybierz algorytm:\n1 - ROT11\n2 - Polibiusz");
		y = scanner.nextInt();
		
		//Cryptographer cryptographer = new Cryptographer();
		if(y==1) {
			Algorithm rot = new ROT11();
			if(x==1)
				Cryptographer.cryptfile(inFile,outFile,rot);
			if(x==2)
				Cryptographer.decryptfile(inFile,outFile,rot);
			else
				System.out.println("B³êdne polecenie1");
		}else if(y==2) {
			Algorithm rot = new Polibiusz();
			if(x==1)
				Cryptographer.cryptfile(inFile,outFile,rot);
			if(x==2)
				Cryptographer.decryptfile(inFile,outFile,rot);
			else
				System.out.println("B³êdne polecenie2");
		}else
			System.out.println("B³êdne polecenie3");
		scanner.close();
	}
}
