

public class Main {
	public static void main(String[] args) {
		
		int fps = 24;
		int delay = 500;
		MicroDVD dvd = new MicroDVD();
		try{
			dvd.delay("src/napisy.txt","src/napisy_new.txt", delay, fps);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
