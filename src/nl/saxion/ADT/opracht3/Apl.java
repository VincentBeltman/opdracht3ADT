package nl.saxion.ADT.opracht3;

import java.io.IOException;

public class Apl {

	public static void main (String[] args) {
		try {
			HbaseConnection con =  HbaseConnection.getInstance();
			InsertApl insertApl = new InsertApl(con);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
