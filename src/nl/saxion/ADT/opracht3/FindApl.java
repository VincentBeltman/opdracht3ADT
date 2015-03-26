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
		//contactedBefore("willem@mail.com");
		//GetNumberOfSendMessages();
		//getMailByBodyOrSubject("Vincent" , "mike@mail.com");
		getMailWithLargestBody();

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
		
		ArrayList<Mail> mails = connection.getAllMailsBySender();
		for(Mail m : mails)
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
	
	private void getMailByBodyOrSubject(String searchterm ,String email) throws IOException
	{
		System.out.println("Mailtjes van " + email + " met de zoekterm " + searchterm);
		for (Mail mail : connection.getMailsBySubjectAndBody(searchterm , email)) {
			//TODO USE TO STRING
			System.out.println(mail.getSubjectString());
		}
		System.out.println("----------------------------------------");
	}
	private void getMailWithLargestBody() throws IOException
	{
		ArrayList<Mail> mails = connection.getAllMailsBySender();
		System.out.println(mails.size());
		Mail largestBody = null;
		for(Mail m : mails)
		{
			if(largestBody == null)
			{
				largestBody = m;
			}
			else
			{
				if(largestBody.getBodyString().length() < m.getBodyString().length())
				{
					largestBody = m;
				}
			}
		}
		System.out.println(largestBody.getBodyString());
	}
	
}
