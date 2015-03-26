package nl.saxion.ADT.opracht3;

import java.io.IOException;

public class Apl {

	public static void main (String[] args) {
		try {
			HbaseConnection con =  HbaseConnection.getInstance();
			
			new InsertApl(con);
			new FindApl(con);
			con.endProcess();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
