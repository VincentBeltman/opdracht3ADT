package nl.saxion.ADT.opracht3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

import com.google.common.io.Files;


public class Mail {

	private String sender;
	private Map<String, String> receivers;
	private long timestamp;
	private String body;
	private String subject;
	private File attachment;
	private boolean statusRead;
	private List<String> labels;
	private Map<String, String> headers;

	public Mail(String sender, Map<String, String> receivers, long timestamp,
			String body, String subject, File attachment,
			List<String> labels, Map<String, String> headers) {
		this.sender = sender;
		this.receivers = receivers;
		this.timestamp = timestamp;
		this.body = body;
		this.subject = subject;
		this.attachment = attachment;
		this.labels = labels;
		this.headers = headers;
		this.statusRead = false;
	}
	

	public byte[] getSender() {
		if (sender != null) {
			return toBytes(sender);
		}
		return null;
	}
	public Map<String, String> getReceivers() {
		return receivers;
	}
	public byte[] getTimestamp()
	{
		if(timestamp != 0)
		{
			return Bytes.toBytes(timestamp);
		}
		return null;
	}
	public long getTimeStamp()
	{
		return timestamp;
	}

	public byte[] getBody() {
		if (body != null) {
			return toBytes(body);
		}

		return null;
	}

	public byte[] getSubject() {
		if (subject != null) {
			return toBytes(subject);
		}
		return null;
	}

	public byte[] getAttachment() {
		if (attachment != null) {
			try {
				return Files.toByteArray(attachment);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}
	public byte[] getStatusRead()
	{
		return objectToBytes(statusRead);
	}
	public byte[] getLabels()
	{
		if(labels != null)
		{
			return objectToBytes(labels);
		}
		return null;
	}
	
	public Map<String, String> getHeaders()
	{
		return headers;
	}

	

	public static byte[] toBytes(String string) {
		return Bytes.toBytes(string);
	}

	public static byte[] objectToBytes(Object obj) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

		try {
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(obj);
			return byteOut.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Object objectFromBytes(byte[] data) {
		
		if(data == null)
		{
			return null;
		}
		ByteArrayInputStream byteIn = new ByteArrayInputStream(data);

		try {
			ObjectInputStream in = new ObjectInputStream(byteIn);
			return in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
