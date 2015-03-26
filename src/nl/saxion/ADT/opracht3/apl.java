package nl.saxion.ADT.opracht3;

import java.io.IOException;

public class apl {

	public static void main (String[] args) {
		try {
			HbaseConnection con =  HbaseConnection.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
