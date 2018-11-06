

public class Main {
	public static void main(String[] args) throws Exception{

		MicroDVD dvd = new MicroDVD();
		dvd.delay("src/napisy.txt","src/napisy_new.txt", 500, 24);
	
	}
}
