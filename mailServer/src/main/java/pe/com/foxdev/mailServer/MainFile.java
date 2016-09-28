package pe.com.foxdev.mailServer;

public class MainFile {
	public static void main(String[] args) {
			System.out.println("inicio");
		
			EmailSending sending=new EmailSending();
			Thread thread=new Thread(sending);
			thread.start();
			System.out.println("fin");
	}
}
