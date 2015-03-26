package nl.saxion.ADT.opracht3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FindApl {
	private HbaseConnection connection;

	public FindApl(HbaseConnection connection) throws IOException {
		this.connection = connection;
		//findEmail();
		contactedBefore("willem@mail.com");
		GetNumberOfSendMessages();

	}

	private void findEmail() throws IOException {
		connection.scanEmail();
	}
	
	private void contactedBefore(String email) throws IOException
	{
		System.out.println(email + " heeft contact gehad met de volgede emails");
		Set<String> contacted = new HashSet<String>();
		ArrayList<Mail> mails = connection.findReceiversBySender(email);
		for(Mail m : mails)
		{
			contacted.addAll(m.getReceivers().keySet());
		}
		
		for(String emailAdress : contacted)
		{
			System.out.println(emailAdress);
		}
		System.out.println("----------------------------------------");
	}
	private void GetNumberOfSendMessages() throws IOException
	{
		System.out.println("Aantal mails per gebruiker");
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		System.out.println(connection.getAllMailsBySender().size());
		for(Mail m : connection.getAllMailsBySender())
		{
			int oldValue = result.get(m.getSenderString()) != null ? result.get(m.getSenderString()) : 0 ;
			oldValue += 1;
			result.put(m.getSenderString(), oldValue );
			
			
		}
		for(String k :  result.keySet())
		{
			System.out.println(k + " " + result.get(k));
		}
		System.out.println("----------------------------------------");
		
		
	}
	
}
