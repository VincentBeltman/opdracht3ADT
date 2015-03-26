package nl.saxion.ADT.opracht3;

import java.io.IOException;

public class FindApl {
	private HbaseConnection connection;

	public FindApl(HbaseConnection connection) throws IOException {
		this.connection = connection;
		findEmail();

	}

	private void findEmail() throws IOException {
		connection.scanEmail();
	}
}
