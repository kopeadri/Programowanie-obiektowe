import java.util.Scanner;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailMessage {
	private String from; //required (must be e-mail)
	private String oneTo;
	private LinkedList<String> to; //required at least one (must be e-mail)
	private String subject; //optional
	private String content ; //optional
	private String mimeType; // optional
	private LinkedList<String> cc; //optional
	private LinkedList<String> bcc; // optional
	
		
	protected EmailMessage(Builder builder){
		from = builder.from;
		to = builder.to; //required at least one (must be e-mail)
		subject = builder.subject; //optional
		content = builder.content ; //optional
		mimeType = builder.mimeType; // optional
		cc = builder.cc; //optional
		bcc = builder.bcc; // optional
	}
	
	static public class Builder{
		private String from;
		private String oneTo;
		private LinkedList<String> to; 
		private String subject; 
		private String content ; 
		private String mimeType; 
		private LinkedList<String> cc; 
		private LinkedList<String> bcc;

		
		Builder addFrom(String name){
			this.from = name;
			return this;
		}
		
		Builder addTo(String name, String...names){
			LinkedList<String> list = new LinkedList<String>();
			list.add(name);
			for (String x : names)
				list.add(x);
			this.to = list;
			return this;
		}
	
		Builder addSubject(String subject){
			this.subject = subject;
			return this;
		}
		
		Builder addContent(String content){
			this.content = content;
			return this;
		}
		
		Builder addMimeType(String mime){
			this.mimeType = mime;
			return this;
		}
		
		Builder addCc(String name, String...names){
			LinkedList<String> list = new LinkedList<String>();
			list.add(name);
			for (String x : names)
				list.add(x);
			this.cc = list;
			return this;
		}

		Builder addBcc(String name, String...names){
			LinkedList<String> list = new LinkedList<String>();
			list.add(name);
			for (String x : names)
				list.add(x);
			this.bcc = list;
			return this;
		}
		
		EmailMessage build(){
			return new EmailMessage(this);
		}
	}

	public static Builder builder(){
		return new EmailMessage.Builder();
	}
	
	public void send() throws MessagingException  {
		Properties properties = new Properties();
	 
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.debug", "true");
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.socketFactory.port","465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(properties);
		 
		MimeMessage mm = new MimeMessage(session);
		
		try {
			mm.setFrom(new InternetAddress(this.from));
		
			InternetAddress[] addressTo = new InternetAddress[this.to.size()];
			int i = 0;
			for(String x: this.to) {
				InternetAddress adress = new InternetAddress(x);
				addressTo[i] = adress;
				i += 1;
			}
			

			mm.setRecipients(Message.RecipientType.TO, addressTo);
		
			mm.setSubject(this.subject); 
			mm.setText(this.content);
		
			Transport t = session.getTransport("smtp");
			t.connect(this.from,this.getProperty("Haslo:"));
			t.sendMessage(mm, mm.getAllRecipients());
		} catch (MessagingException mex) {
			System.out.println("\nFailed, exception: " + mex);
		}
	}
	private String getProperty(String string) {
		String name;
		System.out.println(string);
		Scanner scanner = new Scanner(System.in);
		name = scanner.nextLine();
		return name;
	}
	
	
	
	
	
	
	
	
}


