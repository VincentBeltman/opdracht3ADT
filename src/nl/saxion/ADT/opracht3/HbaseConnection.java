package nl.saxion.ADT.opracht3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseConnection {
	public static void main(String[] args) throws IOException {
		Configuration config = HBaseConfiguration.create();

		HBaseAdmin admin = new HBaseAdmin(config);
		// Creates sender_first if not exists
		if(!admin.tableExists(toBytes("sender_first"))){
			admin.createTable(createTable("sender_first"));
		} else {
			System.out.println("Sender_first already exists");
		}

		// Creates receiver_first if not exists
		if(!admin.tableExists(toBytes("receiver_first"))){
			admin.createTable(createTable("receiver_first"));
		} else {
			System.out.println("Receiver_first already exists");
		}
		
		//TestMike
		List<String> labels = new ArrayList<String>();
		labels.add("Hoi");
		labels.add("doei");
		Mail m = new Mail(null, null, System.currentTimeMillis(), null, null, null, labels, null);
		Long time = (Long) Mail.objectFromBytes(m.getTimestamp() );
		boolean statusRead = (Boolean) Mail.objectFromBytes(m.getStatusRead());
		System.out.println("Mike " + time +  " " + statusRead);
		List<String > labelsFromObject = (List<String>) Mail.objectFromBytes(m.getLabels());
		for(String s : labelsFromObject)
		{
			System.out.println("Mike " + s);
		}
		HTable tableSender_first = new HTable(config, "sender_first");
		HTable tableReceiverFirst = new HTable(config, "receiver_first");
		boolean result = tableReceiverFirst != null && tableSender_first != null;
		System.out.println("Do we have the tables into varbles " + result );
		admin.close();
	}
	
	public static HTableDescriptor createTable(String tableName){
		HTableDescriptor table = new HTableDescriptor(toBytes(tableName));

		table.addFamily(new HColumnDescriptor(toBytes("content")));
		table.addFamily(new HColumnDescriptor(toBytes("receivers")));
		table.addFamily(new HColumnDescriptor(toBytes("headers")));
		table.addFamily(new HColumnDescriptor(toBytes("attachments")));
		table.addFamily(new HColumnDescriptor(toBytes("other")));
		
	    System.out.println(" Table created ");
		return table;
	}
	
	public static byte[] toBytes(String string){
		return Bytes.toBytes(string);
	}

	public void sendMail(Mail mail) {
		System.out.println(mail.getBody());
	}
}