package nl.saxion.ADT.opracht3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FindApl {
	private HbaseConnection connection;

	public FindApl(HbaseConnection connection) throws IOException {
		this.connection = connection;
		//findEmail();
		contactedBefore("vincent@mail.com");

	}

	private void findEmail() throws IOException {
		connection.scanEmail();
	}
	
	private void contactedBefore(String email) throws IOException
	{
		System.out.println(email + " heeft contact gehad met de volgede emails");
		Set<String> contacted = new HashSet<String>();
		ArrayList<Mail> mails = connection.findMailsBySender(email);
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
}
