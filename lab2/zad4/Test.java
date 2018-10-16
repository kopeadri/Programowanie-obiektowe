import java.util.LinkedList;
import java.util.Scanner;

public class Test {
	
	public static void main (String[] args) {
		LinkedList<Prostokat>figury = new LinkedList<Prostokat>();
		
		int n=0;
		double sumaPol;
		
		while(n != 4) {
			System.out.println("\nMenu:\n"
							+"1 - Wczytaj prostokat\n"
							+"2 - Wyswietl wszystkie prostokaty\n"
							+"3 - Oblicz sume pol wszystkich prostokatow\n"
							+"4 - Zakoncz");
			
			Scanner scanner = new Scanner(System.in); 
			n = scanner.nextInt();
			//scanner.close(); 
			
			switch(n) {
				case 1:
					Prostokat p = new Prostokat(1,1);
					p.setA();
					p.setB();
					figury.add(p);
					n=0;
					break;
				case 2:
					System.out.println("Wszystkie prostokaty:");
					for( Prostokat x : figury)
						System.out.println("Prostokat("+x.getA()+","+x.getB()+"), ");
					break;
				case 3:
					sumaPol = 0;
					for (Prostokat x : figury) {
						sumaPol += x.area();
					}
					System.out.println("Suma pol wszystkich prostokatow wynosi:" +sumaPol);
					break;
				case 4:
					break;
				default:
					System.out.println("Bledne polecenie.");
					break;
	
			}
		}
		
	}
}
