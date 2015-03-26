package nl.saxion.ADT.opracht3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class insertApl {
	private HbaseConnection connection;
	
	public insertApl(HbaseConnection connection){
		this.connection = connection;
		
		sendEmail1();
	}
	
	public void sendEmail1(){
		Map<String, String> receivers = new HashMap<String, String>();
		receivers.put("mike@mail.com", "To");
		String afzender = "vincent@mail.com";
		List<String> labels = new ArrayList<String>();
		labels.add("emails");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Language", "nl");
		Mail mail = new Mail(afzender, receivers, System.currentTimeMillis(), "Firts email", "Email", null, labels, headers);
		connection.sendMail(mail);
	}
}
