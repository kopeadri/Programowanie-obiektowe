import java.util.Scanner;

public class Prostokat extends Kwadrat {
	
	double b;
	
	Prostokat(double a_, double b_){
		super(a_);
		b = b_;
	}
	
	double getB() {
		return b;
	}
	
	public void setB() {
		System.out.println("Podaj nowa wartosc b:");
		Scanner scanner = new Scanner(System.in); 
		b = scanner.nextDouble();
		//scanner.close();
	}
	
	double area() {
		return a * b;
	}
	
	boolean isBigger(Prostokat x) {
		if(this.area() < x.area())
			return true;
		return false;
	}
}
