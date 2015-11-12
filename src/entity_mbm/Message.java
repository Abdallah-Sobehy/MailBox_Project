package entity_mbm;

import javax.persistence.*;


@Entity
@Table(name="Mail Box")
public class Message {
	private int msgID;	
	private String status = "unread"; // read/unread
	private String sender;
	private String receiver;
	private String subject;
	private String content;
	private String date;
	
	public Message() {}
	
	public void setMsgID(int id)
	{
		this.msgID = id;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	public void setSubject(String Subject)
	{
		this.subject = Subject;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getMsgID()
	{
		return msgID;
	}

	public String getStatus()
	{
		return this.status;
	}

	public String getSender()
	{
		return this.sender;
	}

	public String getReceiver()
	{
		return this.receiver;
	}

	public String getSubject()
	{
		return this.subject;
	}

	public String getContent()
	{
		return this.content;
	}

	public String getDate()
	{
		return this.date;
	}

	public String toString(){
		return msgID + "\t"+ sender + "\t" + subject + "\t" + date;
	}

	public String readMessage(){
		return "From: " + sender + "\nDate: " + date +"\nSubject: "+ subject + "\n\n" + content;
	}
	

}
