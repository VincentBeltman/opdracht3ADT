package nl.saxion.ADT.opracht3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertApl {
	private HbaseConnection connection;
	
	public InsertApl(HbaseConnection connection){
		this.connection = connection;

		sendEmail1();
		sendEmail2();
		sendEmail3();
		sendEmail4();
		sendEmail5();
		sendEmail6();
		sendEmail7();
		sendEmail7();
		sendEmail8();
	}
	
	public void sendEmail1(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("emails");
		labels.add("teteee");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Firts email";
		String subject = "Email";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail2(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("vincent@mail.com", "To");
		receivers.put("mike@mail.com", "To");
		receivers.put("evert@mail.com", "Cc");
		receivers.put("prakken@mail.com", "Cc");
		receivers.put("stroet@mail.com", "Bcc");
		String afzender = "willem@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("emails");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Dag mede studenten. Hierbij verklaar ik, dat ik een geweldige zeer enorm gigantisch lange mail kan schrijven die nergens op slaat. Dit doe ik doormiddel van: Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui.";
		String subject = "Email";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail3(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "willem@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("stampot");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Hoi Mike! Ik wilde even mededelen dat ik heel veel van stampot wortelen houd. M.v.g. Willem";
		String subject = "Stampot wortelen";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail4(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		receivers.put("vincent@mail.com", "To");
		String afzender = "evert@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("opdrachten");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Dag Mike en Vincent, Voor deze geweldige implementatie krijgen jullie een dikke 10";
		String subject = "Opdracht 3";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail5(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("evert@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("opdrachten");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Hoi Evert, Ik snap niks van die Hbase. Zou jij mij een keer wat kunnen uitleggen? M.v.g., Vincent Beltman";
		String subject = "Ik snap er geen donder van";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail6(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("willem@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("stampot");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Hoi Willem, Zou jij mij dat recept van jou stampot op willen sturen? M.v.g., Vincent Beltman";
		String subject = "Stampot wortelen";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail7(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("stroet@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("bol");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Beste Stroet, Ik wil graag mededelen dat 1 + 1 gelijk is aan 11. Ik had begrepen dat u dat nog wat last mee had. Daarom ben ik samen met de 700 experts van bol.com bezig geweest om dit probleem uit te zoeken. Ik hoop dat u tevreden bent met dit antwoord. M.v.g., Vincent Beltman";
		String subject = "bol.com";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail8(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("opdrachten");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "Hey Mike, Heb jij de opdracht al af? M.v.g., Vincent Beltman";
		String subject = "opdracht af?";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail9(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "You've got mail";
		String subject = "mail";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
	
	public void sendEmail10(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("hoi");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		String body = "hoi";
		String subject = "hoi";
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), body, subject, null, labels, headers);
		connection.sendMail(mail);
	}
}
