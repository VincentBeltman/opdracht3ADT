package nl.saxion.ADT.opracht3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
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
}