package pe.com.foxdev.mailServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EmailSending implements Runnable{
	
	private static final String htmlResource  = "/email/format.jsp"; 
	
	private static final String htmlCompanyMASK = "{company}";
	private static final String htmlTitleMASK   = "{titulo}";
	private static final String htmlYearMASK    = "{year}";
	private static final String htmlFieldMASK   = "{field}";
	private static final String htmlValueMASK   = "{value}";
	private static final String htmlDataMASK    = "{html_data}";
	
	private static final String htmlContentType = "text/html";
	
	private  String mailCompany;
	private  String mailTitulo;
	
	public void run() {
		System.out.println("mail start");
		sendEmail();
		System.out.println("mail end");
	}

	public void sendEmail(){
			
		try{
			String message="Este es un correo de prueba 123";
			final String fromAddress="edymartinh@gmail.com";
		 	final String password="Cristina311275";
		 	String subject="Mensaje enviado por perdida de Contrasenia";
		 	String toAddress="edy.huamani@mdp.com.pe";
		 	this.mailCompany="sistemas@mdp.com.pe";
		 	this.mailTitulo="Correo de prueba ";
		 	
			Properties properties = new Properties();
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        System.out.println("bloque 1");
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromAddress, password);
	            }
	        };
	        
	        System.out.println("bloque 2");
	        Session session = Session.getInstance(properties, auth);
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(fromAddress));
	        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        
	        System.out.println("bloque 3");
			Multipart multipart = new MimeMultipart(mailCompany);

			BodyPart htmlPart = new MimeBodyPart();

			String content = createContentHTML("contenido del mensaje");

			htmlPart.setContent(content, htmlContentType);

			multipart.addBodyPart(htmlPart);
			System.out.println("bloque 4");
		//	multipart.addBodyPart(getImage(mailImgHeader, imgIdHeader));
		//	multipart.addBodyPart(getImage(mailImgFooter, imgIdFooter));
	        
	        
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	        msg.setText(message);
	    	System.out.println("bloque 5");
	        msg.setContent(multipart);
	        System.out.println("bloque 6");
	        // sends the e-mail
	        Transport.send(msg);
		}catch(Exception e){
			System.out.println(e.getMessage()+e);
		}	
	}
	
	private String createContentHTML( Object content ) throws IOException{
		
		String HTML = getHTML();
		
		HTML = HTML.replace( htmlYearMASK , ""+ Calendar.getInstance().get( Calendar.YEAR ) );
		HTML = HTML.replace( htmlCompanyMASK , mailCompany );
		HTML = HTML.replace( htmlTitleMASK , mailTitulo );
		HTML = HTML.replace( htmlDataMASK , content.toString() );
		
		return HTML;
	}

	
	private String getHTML() throws IOException{
		Resource resource = new ClassPathResource( htmlResource );
		InputStreamReader isReader = new InputStreamReader( resource.getInputStream() );
		BufferedReader buffered = new BufferedReader( isReader );
		StringBuffer buffer = new StringBuffer();
		String linea = null;
		while (  ( linea = buffered.readLine() ) != null  ) {
			buffer.append( linea );
		}
		buffered.close();
		return buffer.toString();
	}
	
}
