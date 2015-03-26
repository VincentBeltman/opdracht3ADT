package nl.saxion.ADT.opracht3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseConnection {
	
	private static HbaseConnection instance;
	private static HTable tableSenderFirst;
	private static HTable tableReceiverFirst;
	public static HbaseConnection getInstance() throws IOException
	{
		if(instance == null)
		{
			instance = new HbaseConnection();
		}
		return instance;
	}
	
	private HbaseConnection() throws IOException
	{
		Configuration config = HBaseConfiguration.create();

		HBaseAdmin admin = new HBaseAdmin(config);
		// Creates sender_first if not exists
		if(!admin.tableExists(toBytes("sender_first"))){
			admin.createTable(createTable("sender_first"));
		} else {
			System.out.println("Sender_first already exists");
		}
		tableSenderFirst = new HTable(config, "sender_first");
		tableReceiverFirst = new HTable(config, "receiver_first");

		// Creates receiver_first if not exists
		if(!admin.tableExists(toBytes("receiver_first"))){
			admin.createTable(createTable("receiver_first"));
		} else {
			System.out.println("Receiver_first already exists");
		}

		//admin.close();
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
	
	public void scanEmail() throws IOException
	{
		Scan scan = new Scan();
		scan.addColumn(toBytes("content"), toBytes("subject"));
		
		ResultScanner scanner =  tableSenderFirst.getScanner(scan);
		for(Result result : scanner)
		{
			System.out.println(Bytes.toString(result.getValue(toBytes("content"), toBytes("subject"))));
		}
	}

	public void sendMail(Mail mail) {
		byte[] rowkey = Bytes.add(mail.getSender(), mail.getTimestamp());
		Put p = new Put(rowkey);
		p.add(toBytes("content"), toBytes("subject"), mail.getSubject());
		try {
			tableSenderFirst.put(p);
			System.out.println("Added email");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}