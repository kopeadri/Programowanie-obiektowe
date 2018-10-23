import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class Main {
	public static void main(String []args) throws AddressException, MessagingException{
        
		EmailMessage wiadomosc = EmailMessage.builder()
		.addFrom("k.adriannawz@gmail.com")
		.addTo("adkope@cstudent.agh.edu.pl")
		.addSubject("Test")
		.addContent("Udalo sie")
		.build();
		
        wiadomosc.send();
		//System.out.println(wiadomosc.from);
		//System.out.println(wiadomosc.to);
		//System.out.println(wiadomosc.content);
		//System.out.println(wiadomosc.subject);
	}	
}
