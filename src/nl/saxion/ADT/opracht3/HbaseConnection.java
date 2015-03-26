package nl.saxion.ADT.opracht3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import com.sun.imageio.plugins.png.RowFilter;




public class HbaseConnection {
	
	private static HbaseConnection instance;
	private static HTable tableSenderFirst;
	private static HTable tableReceiverFirst;
	private static HBaseAdmin admin;

	private final static byte[] content = toBytes("content");
	private final static byte[] receivers = toBytes("receivers");
	private final static byte[] headers = toBytes("headers");
	private final static byte[] attachments = toBytes("attachments");
	private final static byte[] other = toBytes("other");

	private final static byte[] subject = toBytes("subject");
	private final static byte[] body = toBytes("body");
	private final static byte[] file = toBytes("file");
	private final static byte[] labels = toBytes("labels");
	private final static byte[] from =  toBytes("From");
	
	private final static byte[] seperator = toBytes("|");
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

		admin = new HBaseAdmin(config);
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

		
	}
	
	public static HTableDescriptor createTable(String tableName){
		HTableDescriptor table = new HTableDescriptor(toBytes(tableName));

		table.addFamily(new HColumnDescriptor(content));
		table.addFamily(new HColumnDescriptor(receivers));
		table.addFamily(new HColumnDescriptor(headers));
		table.addFamily(new HColumnDescriptor(attachments));
		table.addFamily(new HColumnDescriptor(other));
		
	    System.out.println(" Table created ");
		return table;
	}
	
	public static byte[] toBytes(String string){
		return Bytes.toBytes(string);
	}
	
	public void scanEmail() throws IOException
	{
		Scan scan = new Scan();

		
		ResultScanner scanner =  tableSenderFirst.getScanner(scan);
		for(Result result : scanner)
		{
			MailParser.createEmailFromResult(result);
		}
	}

	public void sendMail(Mail mail) {
		byte[] rowkey = Bytes.add(mail.getSender(), seperator, mail.getTimestamp());
		try {
			Put p = mailToPut(rowkey, mail);
			tableSenderFirst.put(p);
			for (Entry<String, String> entry : mail.getReceivers().entrySet()){
				rowkey = Bytes.add(toBytes(entry.getKey()), seperator, mail.getTimestamp());
				p = mailToPut(rowkey, mail);
				p.add(other, from, mail.getSender());
				tableReceiverFirst.put(p);
			}
			System.out.println("Added email");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Put mailToPut(byte[] rowkey, Mail mail){
		Put p = new Put(rowkey);
		p.add(content, subject, mail.getSubject());
		p.add(content, body, mail.getBody());
		for (Entry<String, String> entry : mail.getReceivers().entrySet()){
			p.add(receivers, toBytes(entry.getKey()), toBytes(entry.getValue()));	
		}
		for (Entry<String, String> entry : mail.getHeaders().entrySet()){
			p.add(headers, toBytes(entry.getKey()), toBytes(entry.getValue()));	
		}
		p.add(attachments, file, mail.getAttachment());
		p.add(other, labels, mail.getLabels());
		return p;
	}
	public ArrayList<Mail> findMailsBySender(String email) throws IOException
	{
		ArrayList<Mail> result = new ArrayList<Mail>();
		Scan s = new Scan();
		s.addFamily(receivers);
		PrefixFilter filter = new PrefixFilter(toBytes(email));
		s.setFilter(filter);
		
		ResultScanner scanner = tableSenderFirst.getScanner(s);
		for(Result r : scanner)
		{
			result.add(MailParser.createEmailFromResult(r));
		}
		return result;
		
	}
	
	private static class MailParser{
	
		public static Mail createEmailFromResult(Result result)
		{
			if(!result.isEmpty())
			{
				String subjectString = Bytes.toString(getByteValueFromColum(result, content, subject));
				String bodyString = Bytes.toString(getByteValueFromColum(result, content, body));
				String senderString = Bytes.toString(getByteValueFromColum(result, other, from));
				
				ArrayList<String> labelList = new ArrayList<String>();
				Object unparsedObject = Mail.objectFromBytes(getByteValueFromColum(result, other, labels));
				if(unparsedObject != null && unparsedObject instanceof ArrayList<?>)
				{
					for(String label : (ArrayList<String>)  unparsedObject)
					{
						labelList.add(label);
						//System.out.println(label);
					}
				}
				byte[] rowKey = result.getRow();
				int offset = rowKey.length - Bytes.toBytes(System.currentTimeMillis()).length;
				
				if(senderString == null)
				{
					senderString = (Bytes.toString(rowKey , 0 , offset));
					
				}
				
				
				Long timestamp = Bytes.toLong(rowKey, offset);
				
				//System.out.println(timestamp);
				Map<String, String> receiverMap = new HashMap<String, String>();
				for(String email : getColumnsInColumnFamily(result ,receivers ))
				{
					
					receiverMap.put(email, Bytes.toString(getByteValueFromColum(result, receivers, toBytes(email))));
				}
				
				Map<String, String> headerMap = new HashMap<String, String>();
				for(String header : getColumnsInColumnFamily(result ,headers ))
				{
					//System.out.println(header);
					headerMap.put(header, Bytes.toString(getByteValueFromColum(result, receivers, toBytes(header))));
				}
				return new Mail(senderString, receiverMap, timestamp, bodyString, subjectString, null, labelList, headerMap);
				
			}
			return null;
			
		}
		private static byte[] getByteValueFromColum(Result result , byte[] family , byte[] column)
		{
			if(result.containsColumn(family, column))
			{
				byte[] value = result.getValue(family, column);
				return value;
			}
			return null;
		}
		private static String[] getColumnsInColumnFamily(Result r, byte[] ColumnFamily)
		{

		      NavigableMap<byte[], byte[]> familyMap = r.getFamilyMap(ColumnFamily);
		      String[] Quantifers = new String[familyMap.size()];

		      int counter = 0;
		      for(byte[] bQunitifer : familyMap.keySet())
		      {
		          Quantifers[counter++] = Bytes.toString(bQunitifer);

		      }

		      return Quantifers;
		}
	}
	
	public void endProcess() throws IOException{
		admin.close();
	}
}