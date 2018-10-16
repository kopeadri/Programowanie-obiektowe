import java.util.Scanner;

public class Kwadrat {

		 double a;
		
		Kwadrat(double a_){
			a = a_;
		}
		
		public double getA() {
			return a;
		}
		
		public void setA() {
			System.out.println("Podaj nowa wartosc a:");
			Scanner scanner = new Scanner(System.in); 
			a = scanner.nextDouble();
			//scanner.close(); 
		}
		
		double area() {
			return a*a;
		}
		
		boolean isBigger(Kwadrat x) {
			if(this.area() < x.area())
				return true;
			return false;
		}
}
